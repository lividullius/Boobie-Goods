import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ModalCriacaoPessoaComponent } from './modal-criacao-pessoa/modal-criacao-pessoa.component';
import { ModalCriacaoPerfilComponent } from './modal-criacao-perfil/modal-criacao-perfil.component';
import { PessoaService, PessoaDTO } from '../services/pessoa.service';
import { forkJoin, map } from 'rxjs';

@Component({
  selector: 'app-pessoa',
  standalone: true,
  imports: [CommonModule, HttpClientModule, ModalCriacaoPessoaComponent, ModalCriacaoPerfilComponent],
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

  this.pessoaService.getAllPessoas().subscribe({
    next: (pessoas) => {
      // Cria um array de observables que pegam os perfis de cada pessoa
      const requests = pessoas.map(p =>
      this.pessoaService.getPerfisFromPessoa(p.id!).pipe(
        map(perfis => ({
          ...p,
          perfis: perfis.map((perfil: any) =>
            typeof perfil === 'string' ? perfil : perfil.tipo // força para string
          )
        }))
      )
    );

      // Executa todos os requests em paralelo
      forkJoin(requests).subscribe({
        next: (pessoasComPerfis) => {
          this.pessoas = pessoasComPerfis;
          this.loading = false;
          console.log('Pessoas com perfis:', this.pessoas);
        },
        error: (error) => {
          console.error('Erro ao carregar perfis:', error);
          this.error = 'Erro ao carregar perfis das pessoas.';
          this.loading = false;
        }
      });
    },
    error: (error) => {
      console.error('Erro ao carregar pessoas:', error);
      this.error = 'Erro ao carregar lista de pessoas. Verifique se o backend está rodando.';
      this.loading = false;
    }
  });
}

  refreshPessoas(): void {
    this.loadPessoas();
  }
}
