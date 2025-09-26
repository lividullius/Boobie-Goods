import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Perfil {
  id: number;
  nome: string;
}

@Injectable({
  providedIn: 'root'
})
export class PerfisService {
  private baseUrl = 'http://localhost:8080/api/perfis';

  constructor(private http: HttpClient) {}

  getPerfis(): Observable<Perfil[]> {
    return this.http.get<Perfil[]>(this.baseUrl);
  }
}
