import { Component } from '@angular/core';
import { CommonModule, DatePipe, NgForOf } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';

declare const bootstrap: any; 

type ProjetoDTO = {
  id?: number;
  nome: string;
  dataInicio: string | Date;
  dataFim?: string | Date | null;
  descricao?: string | null;
};

@Component({
  standalone: true,
  selector: 'app-projeto',
  templateUrl: './projeto.component.html',
  styleUrls: ['./projeto.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, NgForOf, DatePipe],
})
export class ProjetoComponent {
  projetos: ProjetoDTO[] = [
    { nome: 'Exemplo de Projeto', dataInicio: '2024-01-01', dataFim: '2024-12-31', descricao: 'Descrição 1' },
  ];

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.maxLength(100)]],
      dataInicio: ['', Validators.required],
      dataFim: [''],
      descricao: ['', [Validators.maxLength(255)]],
    });
  }

  salvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const novo: ProjetoDTO = this.form.value;
    this.projetos = [novo, ...this.projetos]; // mock: adiciona na lista

    // fechar o modal bootstrap programaticamente
    const el = document.getElementById('modalCriarProjeto');
    if (el) {
      const instance = bootstrap.Modal.getInstance(el) || new bootstrap.Modal(el);
      instance.hide();
    }

    this.form.reset();
  }
}
