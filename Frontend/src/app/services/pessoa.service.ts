import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, firstValueFrom } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';
import { forkJoin } from 'rxjs';

export interface PessoaDTO {
  id?: number
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
  private apiPessoasUrl = 'http://localhost:8080/api/pessoas';
  private apiPessoaPerfilUrl = 'http://localhost:8080/api/pessoas-perfis';

  constructor(private http: HttpClient) {}

  // Buscar todas as pessoas (dados básicos do backend)
  getAllPessoas(): Observable<PessoaBackendDTO[]> {
    return this.http.get<PessoaBackendDTO[]>(this.apiPessoasUrl);
  }

  // Buscar pessoa por ID
  getPessoaById(id: number): Observable<PessoaBackendDTO> {
    return this.http.get<PessoaBackendDTO>(`${this.apiPessoasUrl}/${id}`);
  }

  // Criar nova pessoa
  createPessoa(pessoa: PessoaBackendDTO): Observable<PessoaBackendDTO> {
    return this.http.post<PessoaBackendDTO>(this.apiPessoasUrl, pessoa);
  }

  // Atualizar pessoa
  updatePessoa(id: number, pessoa: PessoaBackendDTO): Observable<PessoaBackendDTO> {
    return this.http.put<PessoaBackendDTO>(`${this.apiPessoasUrl}/${id}`, pessoa);
  }

  // Deletar pessoa
  deletePessoa(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiPessoasUrl}/${id}`);
  }

  // Buscar perfis de uma pessoa
  getPerfisFromPessoa(pessoaId: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiPessoasUrl}/${pessoaId}/perfis`);
  }

  // Adicionar perfis a uma pessoa (vários IDs)
  addPerfisToPessoa(pessoaId: number, idsPerfis: number[]): Observable<void> {
    return this.http.post<void>(`${this.apiPessoasUrl}/${pessoaId}/perfis`, idsPerfis);
  }

  getAllPessoasComPerfis(): Observable<PessoaDTO[]> {
  return this.getAllPessoas().pipe(

    map(pessoas => pessoas.map(pessoa => {
      return firstValueFrom(this.getPerfisFromPessoa(pessoa.id)).then(perfis => ({
        id: pessoa.id,
        nome: pessoa.nome,
        perfis
      }));
    })),

    switchMap(promessas => forkJoin(promessas))
  );
}

}