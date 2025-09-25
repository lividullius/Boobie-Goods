import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { PessoaDTO, PessoaService } from '../services/pessoa.service';
import { Projeto } from '../models/projeto';
import { ProjetoService } from '../services/projeto.service';
import { AlocacaoService, AlocacaoDTO } from '../services/alocacao.service';
import { ModalCriacaoAlocacaoComponent } from './modal-criacao-alocacao/modal-criacao-alocacao.component';

interface AlocacaoView {
  id: number;
  pessoa: string;
  perfil: string;
  projeto: string;
  horasSemanal: number;
}

@Component({
  selector: 'app-alocacao',
  standalone: true,
  imports: [CommonModule, HttpClientModule, ModalCriacaoAlocacaoComponent],
  templateUrl: './alocacao.component.html',
  styleUrl: './alocacao.component.scss',
  providers: [AlocacaoService, PessoaService, ProjetoService]
})
export class AlocacaoComponent implements OnInit {
  alocacoes: AlocacaoView[] = [];
  loading = false;
  error: string | null = null;
  
  pessoas: { [id: number]: PessoaDTO } = {};
  projetos: { [id: number]: Projeto } = {};
  
  constructor(
    private alocacaoService: AlocacaoService,
    private pessoaService: PessoaService,
    private projetoService: ProjetoService
  ) {}

  ngOnInit(): void {
    this.carregarDados();
  }
  
  carregarDados(): void {
    this.loading = true;
    this.error = null;
    
    // Primeiro, carregamos as pessoas e projetos para mapeamento
    Promise.all([
      this.carregarPessoas(),
      this.carregarProjetos()
    ]).then(() => {
      // Depois carregamos as alocações
      this.carregarAlocacoes();
    }).catch(error => {
      console.error("Erro ao carregar dados:", error);
      this.error = "Erro ao carregar dados necessários.";
      this.loading = false;
    });
  }
  
  carregarPessoas(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.pessoaService.getAllPessoasComPerfis().subscribe({
        next: (data) => {
          // Criar mapeamento de ID para objeto pessoa
          data.forEach(pessoa => {
            this.pessoas[pessoa.id!] = pessoa;
          });
          resolve();
        },
        error: (error) => {
          console.error('Erro ao carregar pessoas:', error);
          reject(error);
        }
      });
    });
  }
  
  carregarProjetos(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.projetoService.getAllProjetos().subscribe({
        next: (data) => {
          // Criar mapeamento de ID para objeto projeto
          data.forEach(projeto => {
            this.projetos[projeto.id] = projeto;
          });
          resolve();
        },
        error: (error) => {
          console.error('Erro ao carregar projetos:', error);
          reject(error);
        }
      });
    });
  }
  
  carregarAlocacoes(): void {
    this.alocacaoService.getAllAlocacoes().subscribe({
      next: (data) => {
        this.alocacoes = data.map(alocacao => this.converterParaView(alocacao));
        this.loading = false;
        console.log('Alocações carregadas:', this.alocacoes);
      },
      error: (error) => {
        console.error('Erro ao carregar alocações:', error);
        this.error = 'Erro ao carregar alocações. Verifique se o backend está rodando.';
        this.loading = false;
        
        // Fallback para dados mockados em caso de erro
        this.alocacoes = [
          { id: 1, pessoa: 'João Silva', perfil: 'Developer', projeto: 'Projeto A', horasSemanal: 20 },
          { id: 2, pessoa: 'Maria Santos', perfil: 'QualityAnalyst', projeto: 'Projeto B', horasSemanal: 30 },
          { id: 3, pessoa: 'Pedro Oliveira', perfil: 'Gerente', projeto: 'Projeto C', horasSemanal: 40 },
          { id: 4, pessoa: 'Ana Costa', perfil: 'Security', projeto: 'Projeto A', horasSemanal: 15 }
        ];
      }
    });
  }
  
  converterParaView(alocacao: AlocacaoDTO): AlocacaoView {
    // Usar fkPessoa ou pessoaId, dependendo de qual estiver disponível
    const pessoaId = alocacao.fkPessoa !== undefined ? alocacao.fkPessoa : alocacao.pessoaId;
    const projetoId = alocacao.fkProjeto !== undefined ? alocacao.fkProjeto : alocacao.projetoId;
    
    const pessoa = this.pessoas[pessoaId] || { nome: 'Pessoa não encontrada' };
    const projeto = this.projetos[projetoId] || { nomeProjeto: 'Projeto não encontrado' };
    
    return {
      id: alocacao.id || 0,
      pessoa: pessoa.nome,
      // Aqui estamos usando um perfil fixo para simplificar.
      // Em uma implementação real, seria necessário obter o perfil específico da alocação
      perfil: alocacao.perfil || (pessoa.perfis && pessoa.perfis.length > 0 ? pessoa.perfis[0] : 'Sem perfil'),
      projeto: projeto.nomeProjeto,
      horasSemanal: alocacao.horasSemanal || 0
    };
  }
  
  refreshAlocacoes(): void {
    this.carregarDados();
  }
}