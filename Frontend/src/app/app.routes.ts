// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { MainpageComponent } from './mainpage/mainpage.component';
import { ContratoComponent } from './contrato/contrato.component';
import { ProjetoComponent } from './projeto/projeto.component';
import { ModalCriacaoPessoaComponent } from './modal-criacao-pessoa/modal-criacao-pessoa.component';

export const routes: Routes = [

    { path: 'home', component: MainpageComponent, title: 'Home'},
    { path: 'projeto', component: ProjetoComponent},
    { path: 'contrato', component: ContratoComponent},
    { path: 'alocacoes', component: ContratoComponent},
    { path: 'pessoa', component: ModalCriacaoPessoaComponent}
];
