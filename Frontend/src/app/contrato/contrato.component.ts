import { Component } from '@angular/core';
import { ModalContratoComponent } from './modal-criar-contrato/modal-criacao-contrato.component'


@Component({
  selector: 'app-contrato',
  imports: [ModalContratoComponent],
  templateUrl: './contrato.component.html',
  styleUrl: './contrato.component.scss'
})
export class ContratoComponent {

}
