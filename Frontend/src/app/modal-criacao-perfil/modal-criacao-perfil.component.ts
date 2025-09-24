import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TipoPerfil } from '../models/tipoPerfil';

@Component({
  selector: 'app-modal-criacao-perfil',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './modal-criacao-perfil.component.html',
  styleUrl: './modal-criacao-perfil.component.scss'
})
export class ModalCriacaoPerfilComponent {
  perfil = {
    id: null,
    descricao: '',
    tiposPerfilSelecionados: {
      gerente: false,
      developer: false,
      qualityAnalyst: false,
      security: false
    }
  };

  onSubmit(form: any) {
    console.log('Perfil criado:', this.perfil);
    
    // Aqui você pode adicionar a lógica para enviar para o backend
    const tiposPerfilArray = Object.keys(this.perfil.tiposPerfilSelecionados)
      .filter(key => this.perfil.tiposPerfilSelecionados[key as keyof typeof this.perfil.tiposPerfilSelecionados])
      .map(key => key.charAt(0).toUpperCase() + key.slice(1)); // Capitaliza a primeira letra
    
    console.log('Tipos de perfil selecionados:', tiposPerfilArray);
  }
}
