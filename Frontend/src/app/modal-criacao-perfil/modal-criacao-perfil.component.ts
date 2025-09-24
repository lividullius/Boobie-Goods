import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

type PessoaDTO = {
  id?: number;
  nome: string;
  perfis: string[];
};

@Component({
  selector: 'app-modal-criacao-perfil',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './modal-criacao-perfil.component.html',
  styleUrl: './modal-criacao-perfil.component.scss'
})
export class ModalCriacaoPerfilComponent {
  pessoaSelecionada: PessoaDTO | null = null;
  pessoaSelecionadaId: number | null = null;
  
  // Lista de pessoas (deveria vir do serviço)
  pessoas: PessoaDTO[] = [
    { id: 1, nome: 'João Silva', perfis: ['Gerente', 'Developer'] },
    { id: 2, nome: 'Maria Santos', perfis: ['Developer', 'QualityAnalyst'] },
    { id: 3, nome: 'Pedro Oliveira', perfis: ['Security'] },
    { id: 4, nome: 'Ana Costa', perfis: ['Gerente'] },
    { id: 5, nome: 'Carlos Ferreira', perfis: ['Developer', 'Security'] }
  ];

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
      if (this.pessoaSelecionada) {
        this.pessoaSelecionada.perfis.forEach(perfil => {
          this.perfisCheckbox[perfil] = true;
        });
      }
    } else {
      this.pessoaSelecionada = null;
    }
  }

  isPessoaTemPerfil(perfil: string): boolean {
    return this.pessoaSelecionada?.perfis.includes(perfil) || false;
  }

  onSubmit(form: any) {
    if (!this.pessoaSelecionada) {
      alert('Por favor, selecione uma pessoa.');
      return;
    }

    // Coletar novos perfis selecionados
    const novosPerfis = Object.keys(this.perfisCheckbox)
      .filter(perfil => this.perfisCheckbox[perfil] && !this.isPessoaTemPerfil(perfil));

    console.log('Pessoa selecionada:', this.pessoaSelecionada.nome);
    console.log('Perfis existentes:', this.pessoaSelecionada.perfis);
    console.log('Novos perfis adicionados:', novosPerfis);

    // Aqui você enviaria para o backend
    // Atualizar localmente para demonstração
    if (novosPerfis.length > 0) {
      this.pessoaSelecionada.perfis.push(...novosPerfis);
      alert(`Perfis adicionados com sucesso para ${this.pessoaSelecionada.nome}!`);
    } else {
      alert('Nenhum perfil novo foi selecionado.');
    }
  }
}
