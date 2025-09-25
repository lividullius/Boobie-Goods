package com.boobiegoods.taskly.API.Service;

import com.boobiegoods.taskly.Data.Repository.ProjetoRepository;
import com.boobiegoods.taskly.Data.Repository.AlocacaoRepository;
import com.boobiegoods.taskly.Domain.Alocacao;
// com.boobiegoods.taskly.Domain.Contrato;
import com.boobiegoods.taskly.Domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

@Service
public class ProjetoService {
    
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private AlocacaoRepository alocacaoRepository;
    
    /* Buscar todos os projetos */
    public List<Projeto> findAll() {
        return projetoRepository.findAll();
    }
    /* Buscar projeto por ID */
    public Optional<Projeto> findById(Integer id) {
        return projetoRepository.findById(id);
    }

    /* Salvar projeto (criar ou atualizar) */
    public Projeto save(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    /* Deletar projeto por ID */
    public void deleteById(Integer id) {
        projetoRepository.deleteById(id);
    }
    
    /* Verificar se projeto existe */
    public boolean existsById(Integer id) {
        return projetoRepository.existsById(id);
    }

    /* Buscar projeto por nome */
    public Optional<Projeto> findByNome(String nome) {
        return projetoRepository.findByNomeProjeto(nome);
    }

    /* Buscar projetos por nome contendo texto */
    public List<Projeto> findByNomeContaining(String nome) {
        return projetoRepository.findByNomeProjetoContainingIgnoreCase(nome);
    }

    /* Buscar projetos por descrição contendo texto */
    public List<Projeto> findByDescricaoContaining(String descricao) {
        return projetoRepository.findByDescricaoProjetoContainingIgnoreCase(descricao);
    }

    /* Buscar projetos ativos */
    public List<Projeto> findProjetosAtivos() {
        return projetoRepository.findProjetosAtivos(LocalDate.now());
    }

    /* Buscar projetos finalizados */
    public List<Projeto> findProjetosFinalizados() {
        return projetoRepository.findProjetosFinalizados(LocalDate.now());
    }
    
    /* Buscar projetos por período */
    public List<Projeto> findByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return projetoRepository.findByDataInicioProjetoBetween(dataInicio, dataFim);
    }

    /* Contar projetos ativos */
    public long countProjetosAtivos() {
        return projetoRepository.countProjetosAtivos(LocalDate.now());
    }

    /* Buscar projetos com alocações */
    public List<Projeto> findProjetosComAlocacoes() {
        return projetoRepository.findProjetosComAlocacoes();
    }

    /* Buscar projetos sem alocações */
    public List<Projeto> findProjetosSemAlocacoes() {
        return projetoRepository.findProjetosSemAlocacoes();
    }

    /* Verificar se nome do projeto já existe */
    public boolean existsByNome(String nome) {
        return projetoRepository.existsByNomeProjetoIgnoreCase(nome);
    }

    /* Buscar projetos ordenados por data de início */
    public List<Projeto> findAllOrderByDataInicio() {
        return projetoRepository.findAllByOrderByDataInicioProjetoAsc();
    }

    /* Validar regras de negócio antes de salvar */
    public Projeto saveWithValidation(Projeto projeto) {
        // Validar se nome já existe (para novos projetos)
        if (projeto.getId() == 0 && existsByNome(projeto.getNomeProjeto())) {
            throw new IllegalArgumentException("Já existe um projeto com este nome");
        }
        
        // Validar datas
        if (projeto.getDataTerminoProjeto() != null && 
            projeto.getDataInicioProjeto().isAfter(projeto.getDataTerminoProjeto())) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de término");
        }
        
        return save(projeto);
    }

    /* CÁLCULO DE CUSTO (GERAL) = do início do projeto até o fim (ou hoje) */
    @Transactional(readOnly = true)
    public BigDecimal calcularCustoGeralProjeto(Integer projetoId) {
        Projeto p = projetoRepository.findById(projetoId)
            .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));

        LocalDate ini = p.getDataInicioProjeto();
        LocalDate fim = (p.getDataTerminoProjeto() != null) ? p.getDataTerminoProjeto() : LocalDate.now();

        return calcularCustoProjetoNoPeriodo(projetoId, ini, fim);
    }

    /* CÁLCULO DE CUSTO (POR PERÍODO) — apenas dias úteis (seg–sex) */
    @Transactional(readOnly = true)
    public BigDecimal calcularCustoProjetoNoPeriodo(Integer projetoId, LocalDate inicio, LocalDate fim) {
        Projeto p = projetoRepository.findById(projetoId)
            .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));

        if (inicio == null) inicio = p.getDataInicioProjeto();
        if (fim == null)    fim    = (p.getDataTerminoProjeto() != null ? p.getDataTerminoProjeto() : LocalDate.now());
        if (inicio == null || fim == null || fim.isBefore(inicio))
            throw new IllegalArgumentException("Período inválido");

        // Limita o pedido ao período do projeto
        LocalDate baseIni = max(inicio, p.getDataInicioProjeto());
        LocalDate baseFim = min(fim, nn(p.getDataTerminoProjeto(), fim));
        if (baseIni.isAfter(baseFim)) return BigDecimal.ZERO;

        // Busca alocações do projeto onde o CONTRATO também intersecta o período
        var alocacoes = alocacaoRepository.findByProjetoAndPeriodoOverlapFetchContrato(projetoId, baseIni, baseFim);

        BigDecimal total = BigDecimal.ZERO;
        final BigDecimal FIVE = BigDecimal.valueOf(5);

        for (Alocacao a : alocacoes) {
            var c = a.getContrato();
            if (c == null || c.getSalarioHora() == null) continue;

            Integer horasSemanal = a.getHorasSemanal();
            if (horasSemanal == null || horasSemanal <= 0) continue;

            // Interseção final = (Projeto) ∩ (Contrato)
            LocalDate effIni = max(baseIni, c.getDataInicioContrato());
            LocalDate effFim = min(baseFim, nn(c.getDataFimContrato(), baseFim));
            if (effIni == null || effFim == null || effIni.isAfter(effFim)) continue;

            // >>> só dias úteis (inclusivo)
            long diasUteis = businessDaysBetween(effIni, effFim);
            if (diasUteis <= 0) continue;

            // horas por dia considerando 5 dias úteis por semana
            BigDecimal horasDia = BigDecimal.valueOf(horasSemanal)
                    .divide(FIVE, 6, RoundingMode.HALF_UP);

            BigDecimal custo = c.getSalarioHora()
                    .multiply(horasDia)
                    .multiply(BigDecimal.valueOf(diasUteis));

            total = total.add(custo);
        }

        // arredonda o total no final
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /** Conta dias úteis (seg–sex) entre start e end */
    private static long businessDaysBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null || start.isAfter(end)) return 0L;

        long days = ChronoUnit.DAYS.between(start, end) + 1; // inclusivo
        long fullWeeks = days / 7;
        long business = fullWeeks * 5;

        long remaining = days % 7;
        DayOfWeek dow = start.getDayOfWeek();
        for (int i = 0; i < remaining; i++) {
            DayOfWeek d = dow.plus(i);
            if (d != DayOfWeek.SATURDAY && d != DayOfWeek.SUNDAY) {
                business++;
            }
        }
        return business;
    }

    // helpers (aceitam null)
    private static LocalDate nn(LocalDate d, LocalDate fb) { return d != null ? d : fb; }
    private static LocalDate max(LocalDate a, LocalDate b) { if (a == null) return b; if (b == null) return a; return a.isAfter(b) ? a : b; }
    private static LocalDate min(LocalDate a, LocalDate b) { if (a == null) return b; if (b == null) return a; return a.isBefore(b) ? a : b; }
}