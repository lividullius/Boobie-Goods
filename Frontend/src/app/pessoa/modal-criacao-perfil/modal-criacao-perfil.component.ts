import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PessoaDTO, PessoaService } from '../../services/pessoa.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-modal-criacao-perfil',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule],
  templateUrl: './modal-criacao-perfil.component.html',
  styleUrl: './modal-criacao-perfil.component.scss',
  providers: [PessoaService]
})
export class ModalCriacaoPerfilComponent implements OnInit {
  pessoaSelecionada: PessoaDTO | null = null;
  pessoaSelecionadaId: number | null = null;
  
  // Lista de pessoas
  pessoas: PessoaDTO[] = [];
  
  constructor(private pessoaService: PessoaService) {}
  
  ngOnInit(): void {
    this.carregarPessoas();
  }
  
  carregarPessoas(): void {
  this.pessoaService.getAllPessoasComPerfis().subscribe({
    next: (data: PessoaDTO[]) => {
      this.pessoas = data;
      console.log('Pessoas com perfis carregadas:', this.pessoas);
    },
    error: (error) => {
      console.error('Erro ao carregar pessoas com perfis:', error);
    }
  });
}


  // Todos os perfis disponíveis
  perfisDisponiveis = [
    { id: 'Gerente', nome: 'Gerente' },
    { id: 'Developer', nome: 'Developer' },
    { id: 'QualityAnalyst', nome: 'Quality Analyst' },
    { id: 'Security', nome: 'Security' }
  ];

  // Estado dos checkboxes dos perfis
  perfisCheckbox: { [key: string]: boolean } = {
    'Gerente': false,
    'Developer': false,
    'QualityAnalyst': false,
    'Security': false
  };

  onPessoaChange() {
    if (this.pessoaSelecionadaId) {
      this.pessoaSelecionada = this.pessoas.find(p => p.id === Number(this.pessoaSelecionadaId)) || null;
      
      // Reset checkboxes
      this.perfisCheckbox = {
        'Gerente': false,
        'Developer': false,
        'QualityAnalyst': false,
        'Security': false
      };

      // Marcar perfis existentes da pessoa
      if (this.pessoaSelecionada && this.pessoaSelecionada.perfis) {
        this.pessoaSelecionada.perfis.forEach(perfil => {
          this.perfisCheckbox[perfil] = true;
        });
      }
    } else {
      this.pessoaSelecionada = null;
    }
  }

  isPessoaTemPerfil(perfil: string): boolean {
    return this.pessoaSelecionada && this.pessoaSelecionada.perfis 
      ? this.pessoaSelecionada.perfis.includes(perfil)
      : false;
  }

  onSubmit(form: any) {
    if (!this.pessoaSelecionada) {
      alert('Por favor, selecione uma pessoa.');
      return;
    }

    // Garantir que o array de perfis exista
    if (!this.pessoaSelecionada.perfis) {
      this.pessoaSelecionada.perfis = [];
    }

    // Coletar novos perfis selecionados
    const novosPerfis = Object.keys(this.perfisCheckbox)
      .filter(perfil => this.perfisCheckbox[perfil] && !this.isPessoaTemPerfil(perfil));

    console.log('Pessoa selecionada:', this.pessoaSelecionada.nome);
    console.log('Perfis existentes:', this.pessoaSelecionada.perfis);
    console.log('Novos perfis adicionados:', novosPerfis);

    // Atualizar localmente para demonstração
    if (novosPerfis.length > 0) {
      this.pessoaSelecionada.perfis.push(...novosPerfis);
      alert(`Perfis adicionados com sucesso para ${this.pessoaSelecionada.nome}!`);
    } else {
      alert('Nenhum perfil novo foi selecionado.');
    }
  }
}
