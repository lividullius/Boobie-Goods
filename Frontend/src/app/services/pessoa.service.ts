import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, firstValueFrom } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

export interface PessoaDTO {
  id: number;
  nome: string;
  perfis?: string[];
}

export interface PessoaBackendDTO {
  id: number;
  nome: string;
}

@Injectable({
  providedIn: 'root'
})
export class PessoaService {
  private apiUrl = 'http://localhost:8080/api/pessoas';

  constructor(private http: HttpClient) {}

  // Buscar todas as pessoas (dados básicos do backend)
  getAllPessoas(): Observable<PessoaBackendDTO[]> {
    return this.http.get<PessoaBackendDTO[]>(this.apiUrl);
  }

  // Buscar pessoa por ID
  getPessoaById(id: number): Observable<PessoaBackendDTO> {
    return this.http.get<PessoaBackendDTO>(`${this.apiUrl}/${id}`);
  }

  // Criar nova pessoa
  createPessoa(pessoa: PessoaBackendDTO): Observable<PessoaBackendDTO> {
    return this.http.post<PessoaBackendDTO>(this.apiUrl, pessoa);
  }

  // Atualizar pessoa
  updatePessoa(id: number, pessoa: PessoaBackendDTO): Observable<PessoaBackendDTO> {
    return this.http.put<PessoaBackendDTO>(`${this.apiUrl}/${id}`, pessoa);
  }

  // Deletar pessoa
  deletePessoa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Buscar perfis de uma pessoa
  getPerfisFromPessoa(pessoaId: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/${pessoaId}/perfis`);
  }

  // Adicionar perfil a uma pessoa
  addPerfilToPessoa(pessoaId: number, perfil: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/${pessoaId}/perfis`, { perfil });
  }
}