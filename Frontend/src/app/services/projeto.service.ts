import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Projeto } from '../models/projeto';

@Injectable({
  providedIn: 'root'
})
export class ProjetoService {
  private apiUrl = 'http://localhost:8080/api/projetos';

  constructor(private http: HttpClient) {}

  // Buscar todos os projetos
  getAllProjetos(): Observable<Projeto[]> {
    return this.http.get<Projeto[]>(this.apiUrl);
  }

  // Buscar projeto por ID
  getProjetoById(id: number): Observable<Projeto> {
    return this.http.get<Projeto>(`${this.apiUrl}/${id}`);
  }

  // Buscar projetos onde uma pessoa não está alocada
  getProjetosNaoAlocados(pessoaId: number): Observable<Projeto[]> {
    return this.http.get<Projeto[]>(`${this.apiUrl}/nao-alocados/${pessoaId}`);
  }

  // Criar novo projeto
  createProjeto(projeto: Projeto): Observable<Projeto> {
    return this.http.post<Projeto>(this.apiUrl, projeto);
  }

  // Atualizar projeto
  updateProjeto(id: number, projeto: Projeto): Observable<Projeto> {
    return this.http.put<Projeto>(`${this.apiUrl}/${id}`, projeto);
  }

  // Remover projeto
  deleteProjeto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}