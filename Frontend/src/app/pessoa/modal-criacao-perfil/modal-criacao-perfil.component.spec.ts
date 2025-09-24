import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCriacaoPerfilComponent } from './modal-criacao-perfil.component';

describe('ModalCriacaoPerfilComponent', () => {
  let component: ModalCriacaoPerfilComponent;
  let fixture: ComponentFixture<ModalCriacaoPerfilComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalCriacaoPerfilComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalCriacaoPerfilComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
