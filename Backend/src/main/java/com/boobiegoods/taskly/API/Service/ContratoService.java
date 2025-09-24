package com.boobiegoods.taskly.API.Service;

import com.boobiegoods.taskly.Data.Repository.ContratoRepository;
import com.boobiegoods.taskly.Domain.Contrato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {
    
    @Autowired
    private ContratoRepository contratoRepository;
    
    // Buscar todos os contratos
    public List<Contrato> findAll() {
        return contratoRepository.findAll();
    }
    
    // Buscar contrato por ID
    public Optional<Contrato> findById(Integer id) {
        return contratoRepository.findById(id);
    }
    
    // Salvar contrato (criar ou atualizar)
    public Contrato save(Contrato contrato) {
        return contratoRepository.save(contrato);
    }
    
    // Deletar contrato por ID
    public void deleteById(Integer id) {
        contratoRepository.deleteById(id);
    }

    // Verificar se contrato existe
    public boolean existsById(Integer id) {
        return contratoRepository.existsById(id);
    }

    // Buscar contratos por pessoa
    public List<Contrato> findByPessoaId(Integer pessoaId) {
        return contratoRepository.findByPessoaId(pessoaId);
    }

    // Buscar contratos por perfil
    public List<Contrato> findByPerfilId(Integer perfilId) {
        return contratoRepository.findByPerfilId(perfilId);
    }
    
    // Buscar contratos ativos
    public List<Contrato> findContratosAtivos() {
        return contratoRepository.findContratosAtivos(LocalDate.now());
    }

    // Buscar contratos finalizados
    public List<Contrato> findContratosFinalizados() {
        return contratoRepository.findContratosFinalizados(LocalDate.now());
    }
    
    // Buscar contratos por período
    public List<Contrato> findByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return contratoRepository.findByDataInicioContratoBetween(dataInicio, dataFim);
    }

    // Buscar contratos por valor hora (intervalo)
    public List<Contrato> findByValorHora(Double valorMin, Double valorMax) {
        return contratoRepository.findByValorHoraBetween(valorMin, valorMax);
    }

    // Buscar contratos por horas semanais
    public List<Contrato> findByHorasSemana(Integer horas) {
        return contratoRepository.findByNumeroHorasSemana(horas);
    }

    // Buscar contratos por intervalo de horas semanais
    public List<Contrato> findByHorasSemana(Integer horasMin, Integer horasMax) {
        return contratoRepository.findByNumeroHorasSemanaBetween(horasMin, horasMax);
    }

    // Buscar contratos com alocações
    public List<Contrato> findContratosComAlocacoes() {
        return contratoRepository.findContratosComAlocacoes();
    }

    // Buscar contratos sem alocações
    public List<Contrato> findContratosSemAlocacoes() {
        return contratoRepository.findContratosSemAlocacoes();
    }

    // Contar contratos ativos
    public long countContratosAtivos() {
        return contratoRepository.countContratosAtivos(LocalDate.now());
    }
    
    // Buscar contratos ordenados por data de início
    public List<Contrato> findAllOrderByDataInicio() {
        return contratoRepository.findAllByOrderByDataInicioContratoAsc();
    }

    // Buscar contratos ordenados por valor hora (crescente)
    public List<Contrato> findAllOrderByValorHoraAsc() {
        return contratoRepository.findAllByOrderByValorHoraAsc();
    }

    // Buscar contratos ordenados por valor hora (decrescente)
    public List<Contrato> findAllOrderByValorHoraDesc() {
        return contratoRepository.findAllByOrderByValorHoraDesc();
    }

    // Buscar contratos de uma pessoa por período
    public List<Contrato> findByPessoaIdAndPeriodo(Integer pessoaId, LocalDate inicio, LocalDate fim) {
        return contratoRepository.findByPessoaIdAndPeriodo(pessoaId, inicio, fim);
    }

    // Salvar com validação de regras de negócio
    public Contrato saveWithValidation(Contrato contrato) {

    // Validar campos obrigatórios de data
        if (contrato.getDataInicioContrato() == null || contrato.getDataFimContrato() == null) {
            throw new IllegalArgumentException("Datas de início e fim são obrigatórias");
        }
        if (contrato.getDataInicioContrato().isAfter(contrato.getDataFimContrato())) {
            throw new IllegalArgumentException("Data de início não pode ser posterior à data de fim");
        }

        // Validar valor/hora (BigDecimal)
        
        BigDecimal valor = contrato.getValorporHora();
            if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Valor por hora deve ser maior que zero");
            }
            contrato.setValorporHora(valor.setScale(2, RoundingMode.HALF_UP));

            // Validar horas semanais
            if (contrato.getNumeroHorasSemana() <= 0 || contrato.getNumeroHorasSemana() > 40) {
                throw new IllegalArgumentException("Número de horas semanais deve estar entre 1 e 40");
            }

            // Validar pessoa e perfil
            if (contrato.getPessoa() == null) {
                throw new IllegalArgumentException("Pessoa é obrigatória");
            }
            if (contrato.getPerfil() == null) {
                throw new IllegalArgumentException("Perfil é obrigatório");
            }

    return save(contrato); 
    }
}