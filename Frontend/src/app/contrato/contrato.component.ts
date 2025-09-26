import { Component, OnInit } from '@angular/core';
import { ModalContratoComponent } from './modal-criar-contrato/modal-criacao-contrato.component';
import { ContratoService } from '../services/contrato.service';
import { CommonModule, DatePipe, NgForOf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

export interface ContratoDTO {
  pessoaNome: string;
  perfilNome: string;
  dataInicioContrato: string;
  dataFimContrato: string;
  numeroHorasSemana: number;
  salarioHora: number;
}

@Component({
  standalone: true,
  selector: 'app-contrato',
  templateUrl: './contrato.component.html',
  styleUrls: ['./contrato.component.scss'],
  imports: [ModalContratoComponent, CommonModule, ReactiveFormsModule, NgForOf, DatePipe]
})
export class ContratoComponent implements OnInit {
  contratos: ContratoDTO[] = [];
  loading = false;
  error = '';

  constructor(private contratoService: ContratoService) {}

  ngOnInit(): void {
    this.loadContratos();
  }

  private loadContratos(): void {
    this.loading = true;
    this.error = '';
    this.contratoService.getContratos().subscribe({
      next: (res) => {
        this.contratos = res;
        this.loading = false;
      },
      error: () => {
        this.error = 'Falha ao carregar contratos';
        this.loading = false;
      }
    });
  }
}
