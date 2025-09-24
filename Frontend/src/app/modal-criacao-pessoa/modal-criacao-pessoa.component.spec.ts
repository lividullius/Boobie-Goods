import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCriacaoPessoaComponent } from './modal-criacao-pessoa.component';

describe('ModalCriacaoPessoaComponent', () => {
  let component: ModalCriacaoPessoaComponent;
  let fixture: ComponentFixture<ModalCriacaoPessoaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalCriacaoPessoaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalCriacaoPessoaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
