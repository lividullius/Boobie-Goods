import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ReactiveFormsModule } from '@angular/forms';
import { ContratoService } from '../../services/contrato.service';
import { PessoaService, PessoaBackendDTO } from '../../services/pessoa.service';
import { PerfilService } from '../../services/perfil.service';
import { CommonModule } from '@angular/common';
import { Perfil } from '../../models/perfil';

declare const bootstrap: any;

@Component({
  standalone: true,
  selector: 'app-modal-criacao-contrato',
  templateUrl: './modal-criacao-contrato.component.html',
  styleUrls: ['./modal-criacao-contrato.component.scss'],
  imports: [ReactiveFormsModule, CommonModule]
})
export class ModalContratoComponent implements OnInit {

  @Output() contratoCriado = new EventEmitter<void>();
  
  modalContratoForm: FormGroup;
  pessoas: PessoaBackendDTO[] = [];
  perfis: Perfil[] = [];
  mensagemFeedbackSucesso: string = '';

  constructor(
    private fb: FormBuilder,
    private contratoService: ContratoService,
    private pessoaService: PessoaService,
    private perfilService: PerfilService
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

    // Buscar perfis do backend usando o PerfilService fornecido
    this.perfilService.getPerfis().subscribe(res => this.perfis = res);
  }

  dataValidator(group: AbstractControl) {
    const inicio = group.get('dataInicioContrato')?.value;
    const fim = group.get('dataFimContrato')?.value;
    if (!inicio || !fim) return null;

    return new Date(inicio) > new Date(fim) ? { dataInvalida: true } : null;
  }

  onSubmit() {
  if (this.modalContratoForm.invalid) {
    this.modalContratoForm.markAllAsTouched();
    return;
  }

  const contrato = { ...this.modalContratoForm.value };
  contrato.fkPessoa = Number(contrato.fkPessoa);
  contrato.fkPerfil = Number(contrato.fkPerfil);
  contrato.numeroHorasSemana = Number(contrato.numeroHorasSemana);
  contrato.salarioHora = Number(contrato.salarioHora);

  this.contratoService.criarContrato(contrato).subscribe({
    next: () => {
      this.mensagemFeedbackSucesso = 'Contrato salvo com sucesso';
      this.modalContratoForm.reset();

      // Fechar modal
      const el = document.getElementById('modalCriarContrato');
      if (el) {
        const modalInstance = bootstrap.Modal.getInstance(el);
        if (modalInstance) modalInstance.hide();
      }

      // Avisar o componente pai
      this.contratoCriado.emit();
    },
    error: err => console.error('Erro ao salvar contrato', err)
  });
}
}
