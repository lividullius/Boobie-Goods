import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PessoaDTO, PessoaService } from '../services/pessoa.service';
import { ProjetoService } from '../services/projeto.service';
import { AlocacaoService, AlocacaoDTO } from '../services/alocacao.service';
import { Projeto } from '../models/projeto';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-modal-criacao-alocacao',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './modal-criacao-alocacao.component.html',
  styleUrl: './modal-criacao-alocacao.component.scss',
  providers: [PessoaService, ProjetoService, AlocacaoService]
})
export class ModalCriacaoAlocacaoComponent implements OnInit {
  pessoaSelecionada: PessoaDTO | null = null;
  pessoaSelecionadaId: number | null = null;
  projetoSelecionado: Projeto | null = null;
  projetoSelecionadoId: number | null = null;
  
  // Listas
  pessoas: PessoaDTO[] = [];
  projetos: Projeto[] = [];
  projetosDisponiveis: Projeto[] = [];
  
  constructor(
    private pessoaService: PessoaService,
    private projetoService: ProjetoService,
    private alocacaoService: AlocacaoService
  ) {}
  
  ngOnInit(): void {
    this.carregarPessoas();
    this.carregarProjetos();
  }
  
  carregarPessoas(): void {
    this.pessoaService.getAllPessoasComPerfis().subscribe({
      next: (data) => {
        this.pessoas = data;
      },
      error: (error) => {
        console.error('Erro ao carregar pessoas:', error);
      }
    });
  }
  
  carregarProjetos(): void {
    this.projetoService.getAllProjetos().subscribe({
      next: (data) => {
        this.projetos = data;
      },
      error: (error) => {
        console.error('Erro ao carregar projetos:', error);
      }
    });
  }

  onPessoaChange(): void {
    if (this.pessoaSelecionadaId) {
      this.pessoaSelecionada = this.pessoas.find(p => p.id === Number(this.pessoaSelecionadaId)) || null;
      this.projetoSelecionado = null;
      this.projetoSelecionadoId = null;
      
      // Carrega apenas os projetos onde a pessoa não está alocada
      this.carregarProjetosNaoAlocados();
    } else {
      this.pessoaSelecionada = null;
      this.projetosDisponiveis = [];
    }
  }
  
  onProjetoChange(): void {
    if (this.projetoSelecionadoId) {
      this.projetoSelecionado = this.projetosDisponiveis.find(p => p.id === Number(this.projetoSelecionadoId)) || null;
    } else {
      this.projetoSelecionado = null;
    }
  }
  
  carregarProjetosNaoAlocados(): void {
    if (this.pessoaSelecionadaId) {
      this.projetoService.getProjetosNaoAlocados(Number(this.pessoaSelecionadaId)).subscribe({
        next: (data) => {
          this.projetosDisponiveis = data;
        },
        error: (error) => {
          console.error('Erro ao carregar projetos não alocados:', error);
          // Fallback: usar todos os projetos em caso de erro
          this.projetosDisponiveis = this.projetos;
        }
      });
    }
  }

  onSubmit(form: any): void {
    if (!this.pessoaSelecionadaId || !this.projetoSelecionadoId) {
      alert('Por favor, selecione uma pessoa e um projeto.');
      return;
    }

    const novaAlocacao: AlocacaoDTO = {
      pessoaId: Number(this.pessoaSelecionadaId),
      projetoId: Number(this.projetoSelecionadoId)
    };

    this.alocacaoService.criarAlocacao(novaAlocacao).subscribe({
      next: (response) => {
        console.log('Alocação criada com sucesso:', response);
        alert('Alocação criada com sucesso!');
        
        // Resetar o formulário
        this.pessoaSelecionadaId = null;
        this.pessoaSelecionada = null;
        this.projetoSelecionadoId = null;
        this.projetoSelecionado = null;
        this.projetosDisponiveis = [];
        
        // Opcional: fechar o modal programaticamente
        const closeButton = document.querySelector('#modalAlocacao .btn-close') as HTMLElement;
        if (closeButton) {
          closeButton.click();
        }
      },
      error: (error) => {
        console.error('Erro ao criar alocação:', error);
        alert('Erro ao criar alocação. Por favor, tente novamente.');
      }
    });
  }
}
