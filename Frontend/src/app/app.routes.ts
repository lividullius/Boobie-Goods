import { Routes, RouterModule} from '@angular/router';
import { ContratoComponent } from './contrato/contrato.component';
import { MainpageComponent } from './mainpage/mainpage.component';

export const routes: Routes = [

    { path: '', component: MainpageComponent, title: 'Home'},
    { path: 'projeto', component: ContratoComponent},
    { path: 'contrato', component: ContratoComponent},
    { path: 'alocacoes', component: ContratoComponent},
    { path: 'pessoa', component: ContratoComponent}
];
