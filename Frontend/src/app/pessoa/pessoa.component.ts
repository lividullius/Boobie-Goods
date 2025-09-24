import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ModalCriacaoPessoaComponent } from './modal-criacao-pessoa/modal-criacao-pessoa.component';
import { ModalCriacaoPerfilComponent } from './modal-criacao-perfil/modal-criacao-perfil.component';
import { ModalCriacaoAlocacaoComponent } from '../modal-criacao-alocacao/modal-criacao-alocacao.component';
import { PessoaService, PessoaDTO } from '../services/pessoa.service';

@Component({
  selector: 'app-pessoa',
  standalone: true,
  imports: [CommonModule, HttpClientModule, ModalCriacaoPessoaComponent, ModalCriacaoPerfilComponent, ModalCriacaoAlocacaoComponent],
  templateUrl: './pessoa.component.html',
  styleUrl: './pessoa.component.scss',
  providers: [PessoaService]
})
export class PessoaComponent implements OnInit {
  pessoas: PessoaDTO[] = [];
  loading = false;
  error: string | null = null;

  constructor(private pessoaService: PessoaService) {}

  ngOnInit(): void {
    this.loadPessoas();
  }

  loadPessoas(): void {
    this.loading = true;
    this.error = null;
    
    this.pessoaService.getAllPessoasComPerfis().subscribe({
      next: (pessoas) => {
        this.pessoas = pessoas;
        this.loading = false;
        console.log('Pessoas carregadas:', pessoas);
      },
      error: (error) => {
        console.error('Erro ao carregar pessoas:', error);
        this.error = 'Erro ao carregar lista de pessoas. Verifique se o backend está rodando.';
        this.loading = false;
        
        // Fallback para dados mockados em caso de erro
        this.pessoas = [
          { id: 1, nome: 'João Silva', perfis: ['Gerente', 'Developer'] },
          { id: 2, nome: 'Maria Santos', perfis: ['Developer', 'QualityAnalyst'] },
          { id: 3, nome: 'Pedro Oliveira', perfis: ['Security'] },
          { id: 4, nome: 'Ana Costa', perfis: ['Gerente'] },
          { id: 5, nome: 'Carlos Ferreira', perfis: ['Developer', 'Security'] }
        ];
      }
    });
  }

  refreshPessoas(): void {
    this.loadPessoas();
  }
}
