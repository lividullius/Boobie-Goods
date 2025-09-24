import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common'; 

@Component({
  selector: 'app-modal-criar-projeto',
  standalone: true,
  imports: [
    CommonModule,   
    FormsModule,
    DatePipe        
  ],
  templateUrl: './modal-criar-projeto.component.html',
  styleUrls: ['./modal-criar-projeto.component.scss']
})
export class ModalCriarProjetoComponent {
  projeto = {
    id: null as number | null,
    nome: '',
    dataInicio: '',
    dataFim: '',
    descricao: ''
  };

  onSalvarProjeto(form: any) {
    if (form.invalid) return;
    console.log('Projeto a criar:', this.projeto);
    form.resetForm();
  }
}


