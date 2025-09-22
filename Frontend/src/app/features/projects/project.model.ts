export interface Project {
  id?: number;
  nome: string;
  dataInicio: Date;
  dataFim?: Date | null;
  descricao?: string;
}
