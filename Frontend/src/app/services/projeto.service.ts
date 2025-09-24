import { HttpClient } from "@angular/common/http";
import { Projeto } from '../models/projeto'
import { Observable } from "rxjs";
import { Injectable } from "@angular/core";


@Injectable({
  providedIn: 'root'
})
export class ProjetoService{
    private apiUrl = 'http://localhost:8080/api/projetos';

    constructor(private http: HttpClient) {}

    listarProjetos(): Observable<Projeto[]> {
        return this.http.get<Projeto[]>(this.apiUrl);
    }
}