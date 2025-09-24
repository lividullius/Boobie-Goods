import { Component } from "@angular/core";
import { ModalContratoComponent } from '../contrato/modal-criar-contrato/modal-criacao-contrato.component'
import { RouterLink } from "@angular/router";

@Component({
    selector: 'app-navbar',
    standalone: true,
    imports: [ModalContratoComponent, RouterLink],
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})

export class NavbarComponent {}