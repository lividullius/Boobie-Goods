import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalContratoComponent } from './modal-criar-contrato/modal-criacao-contrato.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-contrato',
  imports: [CommonModule, ModalContratoComponent],
  templateUrl: './contrato.component.html',
  styleUrl: './contrato.component.scss'
})
export class ContratoComponent {

}
