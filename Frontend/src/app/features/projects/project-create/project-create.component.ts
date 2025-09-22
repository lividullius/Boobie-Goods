import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder, ReactiveFormsModule, Validators,
  AbstractControl, ValidationErrors
} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { ProjectService } from '../project.service';
import { Project } from '../project.model';

function dateRangeValidator(group: AbstractControl): ValidationErrors | null {
  const ini = group.get('dataInicio')?.value as Date | null;
  const fim = group.get('dataFim')?.value as Date | null;
  if (ini && fim && ini > fim) return { range: 'Data início deve ser <= data fim' };
  return null;
}

@Component({
  selector: 'app-project-create',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule,
    MatCardModule, MatFormFieldModule, MatInputModule,
    MatDatepickerModule, MatNativeDateModule,
    MatButtonModule, MatSnackBarModule
  ],
  templateUrl: './project-create.component.html',
  styleUrls: ['./project-create.scss']
})
export class ProjectCreateComponent {
  form: any;

  constructor(
    private fb: FormBuilder,
    private service: ProjectService,
    private snack: MatSnackBar
  ) {
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.maxLength(120)]],
      dataInicio: [null, Validators.required],
      dataFim: [null],
      descricao: ['', [Validators.maxLength(500)]]
    }, { validators: dateRangeValidator });
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.snack.open('Corrija os campos.', 'OK', { duration: 2500 });
      return;
    }
    const payload: Project = this.form.value as Project;
    this.service.create(payload).subscribe(novo => {
      this.snack.open(`Projeto "${novo.nome}" criado!`, 'OK', { duration: 2500 });
      this.form.reset();
    });
  }

  get f() { return this.form.controls; }
}
