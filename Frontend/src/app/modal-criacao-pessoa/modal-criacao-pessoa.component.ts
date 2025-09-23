import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { Pessoa } from '../models/pessoa';

@Component({
  selector: 'app-modal-criacao-pessoa',
  imports: [ FormsModule ],
  templateUrl: './modal-criacao-pessoa.component.html',
  styleUrl: './modal-criacao-pessoa.component.scss'
})
export class ModalCriacaoPessoaComponent {
  pessoa = {
    id: null,
    nome: ''
  }

  onSubmit(form: any){
    console.log('nome da pessoa:', this.pessoa);
  }
}
