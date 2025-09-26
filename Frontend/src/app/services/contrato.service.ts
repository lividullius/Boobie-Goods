import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { forkJoin, map, Observable, switchMap } from 'rxjs';
import { PessoaService } from './pessoa.service';
import { PerfilService } from './perfil.service';
import { ContratoDTO } from '../contrato/contrato.component';

@Injectable ({
    providedIn: 'root'
})

export class ContratoService {
    private apiUrl = 'http://localhost:8080/api/contratos';

    constructor(private http: HttpClient, 
                private pessoaService: PessoaService, 
                private perfilService: PerfilService
    ) {}
    
    criarContrato(contrato:any): Observable<any> {
        return this.http.post(this.apiUrl, contrato);
    }

    getContratos(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }

    getContratosComPessoaEPerfilNome(): Observable<ContratoDTO[]> {
    return this.http.get<ContratoDTO[]>(this.apiUrl).pipe(
      switchMap(contratos => {
        const contratosComNomes$ = contratos.map(contrato =>
          forkJoin({
            pessoa: this.pessoaService.getPessoaById(contrato.fkPessoa),
            perfil: this.perfilService.getPerfilById(contrato.fkPerfil)
          }).pipe(
            map(res => ({
              ...contrato,
              pessoaNome: res.pessoa.nome,
              perfilNome: res.perfil.tipo.toString() // enum para string
            }))
          )
        );
        return forkJoin(contratosComNomes$);
      })
    );
  }

}