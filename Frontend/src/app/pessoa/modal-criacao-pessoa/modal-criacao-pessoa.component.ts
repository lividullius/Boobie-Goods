import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { PerfilService } from '../../services/perfil.service'
import { Pessoa } from '../../models/pessoa';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal-criacao-pessoa',
  imports: [ CommonModule, FormsModule ],
  templateUrl: './modal-criacao-pessoa.component.html',
  styleUrl: './modal-criacao-pessoa.component.scss'
})
export class ModalCriacaoPessoaComponent implements OnInit{
  pessoa = {
    id: null,
    nome: ''
  }

  perfisDisponiveis: any[] =[];

  constructor(private perfilService: PerfilService){

  }

  ngOnInit(): void {
    this.perfilService.getPerfis().subscribe({
      next: (perfis) => {
        this.perfisDisponiveis = perfis;
      },
      error: (err) => {
        console.error('Erro ao carregar perfis:', err);
      }
    });
  }

  onSubmit(form: any){
    console.log('nome da pessoa:', this.pessoa);
  }
}
