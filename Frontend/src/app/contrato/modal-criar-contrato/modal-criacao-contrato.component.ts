import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; 
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-modal-criacao-contrato',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './modal-criacao-contrato.component.html',
  styleUrl: './modal-criacao-contrato.component.scss'
})

export class ModalContratoComponent implements OnInit{
  modalContratoForm: FormGroup;
  mensagemFeedbackSucesso: string =  ' ';

  constructor(private fb: FormBuilder) {
    this.modalContratoForm = this.fb.group ({
      pessoa: [' ', Validators.required],
      perfil: [' ', Validators.required],
      dataInicio: [' ', Validators.required],
      dataFinal: [' ', Validators.required],
      horasSemana: [' ', [Validators.required, Validators.min(1), Validators.max(40)]],
      salarioHora: [' ', Validators.required], },
      { validators: this.dataValidator});
  }
  ngOnInit(): void {
    
  }

  dataValidator(group: AbstractControl) {
    const inicio = group.get('dataInicio')?.value;
    const final = group.get('dataFinal')?.value;

    const inicioContrato = new Date(inicio);
    const finalContrato = new Date(final);

    if(inicioContrato < finalContrato) {
      return {dataInvalida: true}
    }
    return null;
  }

  onSubmit() {
    if(this.modalContratoForm.invalid) {
      this.modalContratoForm.markAllAsTouched();

      this.mensagemFeedbackSucesso = 'Contrato salvo com sucesso';
      return;
    }
  }

}
