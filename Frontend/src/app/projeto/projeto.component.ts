import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe, NgForOf } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { FormsModule } from '@angular/forms';             // << novo
import { ProjetoService } from '../services/projeto.service';
import { Projeto } from '../models/projeto';

declare const bootstrap: any;

@Component({
  standalone: true,
  selector: 'app-projeto',
  templateUrl: './projeto.component.html',
  styleUrls: ['./projeto.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, FormsModule, NgForOf, DatePipe], // << FormsModule aqui
})
export class ProjetoComponent implements OnInit {

  // lista de projetos vindos da API
  projetos: Projeto[] = [];

  // estados de tela
  loading = false;
  error = '';

  // campos para o "teste rápido" de custo
  dataInicio = '';
  dataFim = '';
  custo: number | null = null;
  custoPeriodo: number | null = null;

  // form para criar/editar projeto (você já tinha)
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private projetoService: ProjetoService
  ) {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.maxLength(100)]],
      dataInicio: ['', Validators.required],
      dataFim: [''],
      descricao: ['', [Validators.maxLength(255)]],
    });
  }

  // carrega projetos ao abrir a página
  ngOnInit(): void {
    this.loadProjetos();
  }

  private loadProjetos(): void {
    this.loading = true;
    this.error = '';
    this.projetoService.getAllProjetos().subscribe({
      next: (projetos) => {
        this.projetos = projetos;
        this.loading = false;
      },
      error: () => {
        this.error = 'Falha ao carregar projetos';
        this.loading = false;
      }
    });
  }

  // --- Cálculos de custo ---
  verCusto(id: number): void {
    this.error = '';
    this.projetoService.getCustoGeral(id).subscribe({
      next: (valor) => this.custo = valor,
      error: () => this.error = 'Falha ao calcular custo geral'
    });
  }

  verCustoPeriodo(id: number): void {
    this.error = '';
    if (!this.dataInicio || !this.dataFim) {
      this.error = 'Informe início e fim do período';
      return;
    }
    this.projetoService.getCustoPeriodo(id, this.dataInicio, this.dataFim).subscribe({
      next: (valor) => this.custoPeriodo = valor,
      error: () => this.error = 'Falha ao calcular custo no período'
    });
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
