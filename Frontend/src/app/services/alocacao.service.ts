import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AlocacaoDTO {
  id?: number;
  pessoaId: number;
  projetoId: number;
  perfil?: string; // opcional, dependendo de como a alocação é armazenada
}

@Injectable({
  providedIn: 'root'
})
export class AlocacaoService {
  private apiUrl = 'http://localhost:8080/api/alocacoes';

  constructor(private http: HttpClient) {}

  // Buscar todas as alocações
  getAllAlocacoes(): Observable<AlocacaoDTO[]> {
    return this.http.get<AlocacaoDTO[]>(this.apiUrl);
  }

  // Buscar alocações por pessoa
  getAlocacoesByPessoa(pessoaId: number): Observable<AlocacaoDTO[]> {
    return this.http.get<AlocacaoDTO[]>(`${this.apiUrl}/pessoa/${pessoaId}`);
  }

  // Buscar alocações por projeto
  getAlocacoesByProjeto(projetoId: number): Observable<AlocacaoDTO[]> {
    return this.http.get<AlocacaoDTO[]>(`${this.apiUrl}/projeto/${projetoId}`);
  }

  // Verificar se pessoa já está alocada no projeto
  verificarAlocacao(pessoaId: number, projetoId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/verificar/${pessoaId}/${projetoId}`);
  }

  // Criar nova alocação
  criarAlocacao(alocacao: AlocacaoDTO): Observable<AlocacaoDTO> {
    return this.http.post<AlocacaoDTO>(this.apiUrl, alocacao);
  }

  // Remover alocação
  removerAlocacao(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}