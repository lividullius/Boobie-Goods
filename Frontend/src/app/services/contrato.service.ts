import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable ({
    providedIn: 'root'
})

export class ContratoService {
    private apiUrl = 'http://localhost:8080/api/contratos';

    constructor(private http: HttpClient) {}
    
    criarContrato(contrato:any): Observable<any> {
        return this.http.post(this.apiUrl, contrato);
    }

    getContratos(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }
}