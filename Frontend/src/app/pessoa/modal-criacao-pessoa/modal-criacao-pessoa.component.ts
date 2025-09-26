import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { PerfilService } from '../../services/perfil.service'
import { PessoaService } from '../../services/pessoa.service';
import { Pessoa } from '../../models/pessoa';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-modal-criacao-pessoa',
  imports: [ CommonModule, FormsModule ],
  templateUrl: './modal-criacao-pessoa.component.html',
  styleUrl: './modal-criacao-pessoa.component.scss'
})
export class ModalCriacaoPessoaComponent implements OnInit{

  @Output() pessoaCriada = new EventEmitter<void>();

  pessoa = {
    id: null,
    nome: ''
  }

  perfisDisponiveis: any[] =[];

  constructor(private perfilService: PerfilService, private pessoaService: PessoaService){

  }

  ngOnInit(): void {
    this.perfilService.getPerfis().subscribe({
      next: (perfis) => {
        this.perfisDisponiveis = perfis;
      },
      error: (err) => {
        console.error('Erro ao carregar perfis:', err);
      }
    });
  }

  onSubmit(form: any){
    this.pessoaService.createPessoa({ id:0, nome: this.pessoa.nome }).subscribe({
      next: (novaPessoa) => {
        console.log('Pessoa criada:', novaPessoa);

        const idsPerfisSelecionados = this.perfisDisponiveis
          .filter(p => p.selecionado) // supondo que você marcou os selecionados no front
          .map(p => p.id);

        if (idsPerfisSelecionados.length > 0) {
          this.pessoaService.addPerfisToPessoa(novaPessoa.id, idsPerfisSelecionados).subscribe({
            next: () => {
              console.log('Perfis associados com sucesso!');
              this.pessoaCriada.emit();
            },
            error: (err) => {
              console.error('Erro ao associar perfis:', err);
            }
          });
        }
      },
      error: (err) => {
        console.error('Erro ao criar pessoa:', err);
      }
    });
  }


}
