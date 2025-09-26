import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ContratoDTO {
  id: number;
  fkPessoa: number;
  fkPerfil: number;
  dataInicioContrato: string;
  dataFimContrato: string;
  numeroHorasSemana: number;
  salarioHora: number;
}

@Injectable({
  providedIn: 'root'
})
export class ContratoService {
  private apiUrl = 'http://localhost:8080/api/contratos';

  constructor(private http: HttpClient) {}

  // Buscar todos os contratos
  getAllContratos(): Observable<ContratoDTO[]> {
    return this.http.get<ContratoDTO[]>(this.apiUrl);
  }

  // Buscar contrato por ID
  getContratoById(id: number): Observable<ContratoDTO> {
    return this.http.get<ContratoDTO>(`${this.apiUrl}/${id}`);
  }

  // Criar novo contrato
  criarContrato(contrato: ContratoDTO): Observable<ContratoDTO> {
    return this.http.post<ContratoDTO>(this.apiUrl, contrato);
  }

  // Atualizar contrato
  atualizarContrato(id: number, contrato: ContratoDTO): Observable<ContratoDTO> {
    return this.http.put<ContratoDTO>(`${this.apiUrl}/${id}`, contrato);
  }

  // Remover contrato
  removerContrato(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
