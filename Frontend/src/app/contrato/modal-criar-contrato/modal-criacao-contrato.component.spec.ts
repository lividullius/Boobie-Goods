import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalContratoComponent } from './modal-criacao-contrato.component';

describe('ModalContratoComponent', () => {
  let component: ModalContratoComponent;
  let fixture: ComponentFixture<ModalContratoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalContratoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalContratoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
