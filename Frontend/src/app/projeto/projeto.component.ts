import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe, NgForOf } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { ProjetoService } from '../services/projeto.service';
import { Projeto } from '../models/projeto';

declare const bootstrap: any;

@Component({
  standalone: true,
  selector: 'app-projeto',
  templateUrl: './projeto.component.html',
  styleUrls: ['./projeto.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, FormsModule, NgForOf, DatePipe],
})
export class ProjetoComponent implements OnInit {

  // lista vinda da API
  projetos: Projeto[] = [];

  // estados de tela
  loading = false;
  salvando = false;
  error = '';

  // "teste rápido" de custo (inputs no topo da página)
  dataInicio = '';
  dataFim = '';
  custo: number | null = null;
  custoPeriodo: number | null = null;

  // form do modal
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private projetoService: ProjetoService
  ) {
    this.form = this.fb.group(
      {
        nome: ['', [Validators.required, Validators.maxLength(100)]],
        dataInicio: ['', Validators.required],
        dataFim: [''],
        descricao: ['', [Validators.maxLength(255)]],
      },
      {
        // validação: fim >= início (se ambos existirem)
        validators: (g: FormGroup) => {
          const ini = g.get('dataInicio')?.value;
          const fim = g.get('dataFim')?.value;
          if (ini && fim && new Date(fim) < new Date(ini)) {
            g.get('dataFim')?.setErrors({ menorQueInicio: true });
          }
          return null;
        }
      }
    );
  }

  ngOnInit(): void {
    this.loadProjetos();
  }

  private loadProjetos(): void {
    this.loading = true;
    this.error = '';
    this.projetoService.getAllProjetos().subscribe({
      next: (projetos) => { this.projetos = projetos; this.loading = false; },
      error: () => { this.error = 'Falha ao carregar projetos'; this.loading = false; }
    });
  }

  // ===================== CÁLCULOS DE CUSTO =====================
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
  // =============================================================

  /** garante 'yyyy-MM-dd' (input[type=date] já envia assim; aqui é só por segurança) */
  private toYMD(v: any): string | null {
    if (!v) return null;
    if (/^\d{4}-\d{2}-\d{2}$/.test(v)) return v;
    const d = new Date(v);
    if (isNaN(d.getTime())) return null;
    const mm = String(d.getMonth() + 1).padStart(2, '0');
    const dd = String(d.getDate()).padStart(2, '0');
    return `${d.getFullYear()}-${mm}-${dd}`;
  }

  /** fecha o modal e limpa backdrop se necessário */
  private fecharModalCriarProjeto(): void {
    const el = document.getElementById('modalCriarProjeto');
    if (!el) return;
    const modal = bootstrap.Modal.getOrCreateInstance(el);
    modal.hide();

    // fallback: se o backdrop ficar preso, remove
    el.addEventListener('hidden.bs.modal', () => {
      document.body.classList.remove('modal-open');
      document.querySelectorAll('.modal-backdrop').forEach(b => b.remove());
    }, { once: true });
  }

  // salvar projeto (POST)
  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.salvando = true;
    this.error = '';

    // mapeia nomes do form -> nomes esperados pelo backend
    const desc = (this.form.value.descricao ?? '').toString().trim();

    const payload: Partial<Projeto> = {
      nomeProjeto: (this.form.value.nome as string).trim(),
      dataInicioProjeto: this.toYMD(this.form.value.dataInicio) as any,
      dataTerminoProjeto: this.form.value.dataFim
        ? (this.toYMD(this.form.value.dataFim) as any)
        : undefined,                               // não envia se vazio
      descricaoProjeto: desc.length ? desc : undefined, // undefined em vez de null
    };

    this.projetoService.createProjeto(payload as Projeto).subscribe({
      next: (criado) => {
        this.projetos = [criado, ...this.projetos]; // adiciona no topo
        this.fecharModalCriarProjeto();
        this.form.reset();
      },
      error: () => this.error = 'Não foi possível salvar o projeto.',
      complete: () => this.salvando = false
    });
  }

  // mostra/esconde o card de cálculo
    showCalculo = false;

    abrirCalculo(): void {
      this.showCalculo = true;
      // rola suave até o card quando abrir
      setTimeout(() => {
        document.getElementById('cardCalculo')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 0);
    }

    fecharCalculo(): void {
      this.showCalculo = false;
      // limpa resultados/inputs do cálculo ao fechar (opcional)
    this.custo = null;
    this.custoPeriodo = null;
    this.dataInicio = '';
    this.dataFim = '';
  }

}
