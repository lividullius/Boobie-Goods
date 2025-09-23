import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MainpageComponent } from './../mainpage/mainpage.component';
import { ModalCriarProjetoComponent } from './projeto/modal-criar-projeto/modal-criar-projeto.component';
import { ModalCriacaoPessoaComponent } from "./modal-criacao-pessoa/modal-criacao-pessoa.component";


@Component({
  selector: 'app-root',
  standalone: true,

  imports: [RouterOutlet, NavbarComponent,  MatDialogModule, MainpageComponent, ModalCriacaoPessoaComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(private dialog: MatDialog) {}

  abrirModalProjeto() {
    this.dialog.open(ModalCriarProjetoComponent, {
      width: '720px',
      autoFocus: false,
    }).afterClosed().subscribe(result => {
      if (result === 'saved') {
        console.log('Projeto criado!');
      }
    });
  }
}
