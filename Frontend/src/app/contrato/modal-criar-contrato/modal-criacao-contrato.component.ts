import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-modal-criacao-contrato',
  templateUrl: './modal-criacao-contrato.component.html',
  styleUrl: './modal-criacao-contrato.component.scss'
})
export class ModalContratoComponent implements OnInit{
  modalContratoForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.modalContratoForm = this.fb.group ({
      
    })
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
}
