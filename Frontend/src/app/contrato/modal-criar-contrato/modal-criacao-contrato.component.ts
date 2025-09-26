
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl,ReactiveFormsModule  } from '@angular/forms';
import { ContratoService } from '../../services/contrato.service';
import { PessoaService, PessoaBackendDTO } from '../../services/pessoa.service';
import { PerfisService, Perfil } from '../../services/perfil.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal-criacao-contrato',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './modal-criacao-contrato.component.html',
  styleUrls: ['./modal-criacao-contrato.component.scss']
})
export class ModalContratoComponent implements OnInit {
  modalContratoForm: FormGroup;
  pessoas: PessoaBackendDTO[] = [];
  perfis: Perfil[] = [];
  mensagemFeedbackSucesso: string = '';

  constructor(
    private fb: FormBuilder,
    private contratoService: ContratoService,
    private pessoaService: PessoaService,
    private perfisService: PerfisService
  ) {
    this.modalContratoForm = this.fb.group({
      fkPessoa: [null, Validators.required],
      fkPerfil: [null, Validators.required],
      dataInicioContrato: ['', Validators.required],
      dataFimContrato: ['', Validators.required],
      numeroHorasSemana: [null, [Validators.required, Validators.min(1), Validators.max(40)]],
      salarioHora: [null, Validators.required],
    }, { validators: this.dataValidator });
  }

  ngOnInit(): void {
    // Buscar pessoas do backend
    this.pessoaService.getAllPessoas().subscribe(res => this.pessoas = res);

    // Buscar perfis do backend
    this.perfisService.getPerfis().subscribe(res => this.perfis = res);
  }

  dataValidator(group: AbstractControl) {
    const inicio = group.get('dataInicioContrato')?.value;
    const fim = group.get('dataFimContrato')?.value;
    if (!inicio || !fim) return null;

    const inicioContrato = new Date(inicio);
    const fimContrato = new Date(fim);

    return inicioContrato > fimContrato ? { dataInvalida: true } : null;
  }

  onSubmit() {
    if (this.modalContratoForm.invalid) {
      this.modalContratoForm.markAllAsTouched();
      return;
    }

    // Transformar campos numéricos para garantir que backend receba ints/BigDecimal
    const contrato = { ...this.modalContratoForm.value };
    contrato.fkPessoa = Number(contrato.fkPessoa);
    contrato.fkPerfil = Number(contrato.fkPerfil);
    contrato.numeroHorasSemana = Number(contrato.numeroHorasSemana);
    contrato.salarioHora = Number(contrato.salarioHora);

    this.contratoService.criarContrato(contrato).subscribe({
      next: res => {
        this.mensagemFeedbackSucesso = 'Contrato salvo com sucesso';
        this.modalContratoForm.reset();
      },
      error: err => console.error('Erro ao salvar contrato', err)
    });
  }
}