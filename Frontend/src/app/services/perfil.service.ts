import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Perfil } from '../models/perfil';

@Injectable({
  providedIn: 'root'
})
export class PerfilService {
  private apiUrl = 'http://localhost:8080/api/perfis'; 

  constructor(private http: HttpClient) {}

  getPerfis(): Observable<Perfil[]> {
    return this.http.get<Perfil[]>(this.apiUrl);
  }

  getPerfilById(id: number): Observable<Perfil> {
    return this.http.get<Perfil>(`${this.apiUrl}/${id}`);
  }
}

