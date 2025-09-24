import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCriacaoAlocacaoComponent } from './modal-criacao-alocacao.component';

describe('ModalCriacaoAlocacaoComponent', () => {
  let component: ModalCriacaoAlocacaoComponent;
  let fixture: ComponentFixture<ModalCriacaoAlocacaoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalCriacaoAlocacaoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalCriacaoAlocacaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
