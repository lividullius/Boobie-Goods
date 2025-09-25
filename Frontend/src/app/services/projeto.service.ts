import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Projeto } from '../models/projeto';
import { environment } from '../../environments/environments';

@Injectable({ providedIn: 'root' })
export class ProjetoService {
  private base = `${environment.apiUrl}/projetos`;

  constructor(private http: HttpClient) {}

  // CRUD
  getAllProjetos(): Observable<Projeto[]> {
    return this.http.get<Projeto[]>(this.base);
  }
  getProjetoById(id: number): Observable<Projeto> {
    return this.http.get<Projeto>(`${this.base}/${id}`);
  }
  getProjetosNaoAlocados(pessoaId: number): Observable<Projeto[]> {
    return this.http.get<Projeto[]>(`${this.base}/nao-alocados/${pessoaId}`);
  }
  createProjeto(projeto: Projeto): Observable<Projeto> {
    return this.http.post<Projeto>(this.base, projeto);
  }
  updateProjeto(id: number, projeto: Projeto): Observable<Projeto> {
    return this.http.put<Projeto>(`${this.base}/${id}`, projeto);
  }
  deleteProjeto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

  // Custo 
  getCustoGeral(id: number): Observable<number> {
    return this.http.get<number>(`${this.base}/${id}/custo`);
  }
  getCustoPeriodo(id: number, dataInicio: string, dataFim: string): Observable<number> {
    return this.http.get<number>(`${this.base}/${id}/custo-periodo`, {
      params: { dataInicio, dataFim }
    });
  }
}
