import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './../navbar/navbar.component';
import { MainpageComponent } from './../mainpage/mainpage.component';
import { ModalCriacaoPessoaComponent } from "./modal-criacao-pessoa/modal-criacao-pessoa.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent, MainpageComponent, ModalCriacaoPessoaComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {}
