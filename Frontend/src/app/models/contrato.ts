import {Pessoa} from './pessoa';

export interface contrato {
    /* private int idContrato;
    private Pessoa pessoa;
    private Perfil perfil;
    private LocalDate dataInicioContrato;
    private LocalDate dataFimContrato;
    private int numeroHorasSemana;
    private double valorHora; */

    id: number;
    pessoa: Pessoa; 
}