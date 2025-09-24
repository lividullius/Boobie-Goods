import { Component } from '@angular/core';
import { CommonModule, DatePipe, NgForOf } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { ProjetoService } from '../services/projeto.service';
import { Projeto } from '../models/projeto';

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

  projetos: Projeto[] = [
    { id: 1, nomeProjeto: 'Exemplo de Projeto', dataInicioProjeto: new Date(2025, 2, 23), dataTerminoProjeto: new Date(2025, 2, 26), descricaoProjeto: 'Descrição 1' },
  ];


  form: FormGroup;

  constructor(private fb: FormBuilder, 
    private projetoService: ProjetoService
  ) 
  {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.maxLength(100)]],
      dataInicio: ['', Validators.required],
      dataFim: [''],
      descricao: ['', [Validators.maxLength(255)]],
    });
  }

  ngOnInit() {
      /* this.projetoService.listarProjetos().subscribe(projetos => {
      this.projetos = projetos; // aqui sim você atribui os dados reais
    }); */
  }

  salvar() {
    /* if (this.form.invalid) {
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

    this.form.reset(); */
  }
}
