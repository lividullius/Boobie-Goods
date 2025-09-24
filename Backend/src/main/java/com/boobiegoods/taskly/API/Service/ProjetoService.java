package com.boobiegoods.taskly.API.Service;

import com.boobiegoods.taskly.Data.Repository.ProjetoRepository;
import com.boobiegoods.taskly.Data.Repository.AlocacaoRepository;
import com.boobiegoods.taskly.Domain.Alocacao;
import com.boobiegoods.taskly.Domain.Contrato;
import com.boobiegoods.taskly.Domain.Projeto;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
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

     /*CÁLCULO DE CUSTO */

    /** Custo geral usando o próprio período do projeto. */
    public BigDecimal calcularCustoGeralProjeto(Integer projetoId) {
        Projeto p = projetoRepository.findById(projetoId)
            .orElseThrow(() -> new IllegalArgumentException("Projeto não encontrado"));

        LocalDate ini = p.getDataInicioProjeto();
        LocalDate fim = p.getDataTerminoProjeto();
        if (ini == null || fim == null || fim.isBefore(ini)) {
            throw new IllegalArgumentException("Período do projeto inválido");
        }
        return calcularCustoProjetoNoPeriodo(projetoId, ini, fim);
    }

    /** 
     * Custo por período: para cada alocação que intersecta o período,
     * somar salárioHora × (horasSemanal/7) × nºDias (interseção de Período ∩ Alocação ∩ Contrato).
     */
    public BigDecimal calcularCustoProjetoNoPeriodo(Integer projetoId, LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || fim.isBefore(inicio)) {
            throw new IllegalArgumentException("Período inválido");
        }

        List<Alocacao> alocacoes = alocacaoRepository
            .findByProjetoAndPeriodoOverlapFetchContrato(projetoId, inicio, fim);

        BigDecimal total = BigDecimal.ZERO;

        for (Alocacao a : alocacoes) {
            Contrato c = a.getContrato();
            if (c == null || c.getValorporHora() == null) continue;

            // (Período pedido) ∩ (Alocação) ∩ (Contrato)
            LocalDate aIni = a.getDataInicio();
            LocalDate aFim = (a.getDataFim() == null) ? fim : a.getDataFim();

            LocalDate effIni = max3(inicio, aIni, c.getDataInicio());
            LocalDate effFim = min3(fim, aFim, c.getDataFim());

            if (effIni == null || effFim == null || effIni.isAfter(effFim)) continue;

            Integer horasSemanal = a.getHorasSemanal();
            if (horasSemanal == null || horasSemanal <= 0) continue;

            long dias = ChronoUnit.DAYS.between(effIni, effFim) + 1; 
            BigDecimal horasDia = BigDecimal.valueOf(horasSemanal)
                    .divide(BigDecimal.valueOf(7), 6, RoundingMode.HALF_UP);

            BigDecimal custo = c.getValorporHora()
                    .multiply(horasDia)
                    .multiply(BigDecimal.valueOf(dias));

            total = total.add(custo);
        }
        return total;
    }

    /* Helpers de datas */
    private static LocalDate max(LocalDate a, LocalDate b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.isAfter(b) ? a : b;
    }

    private static LocalDate min(LocalDate a, LocalDate b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.isBefore(b) ? a : b;
    }

    private static LocalDate max3(LocalDate a, LocalDate b, LocalDate c) { return max(max(a,b), c); }
    private static LocalDate min3(LocalDate a, LocalDate b, LocalDate c) { return min(min(a,b), c); }
}
