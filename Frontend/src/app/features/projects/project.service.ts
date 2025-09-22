import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { Project } from './project.model';

@Injectable({ providedIn: 'root' })
export class ProjectService {
  private seq = 1;
  private store = new BehaviorSubject<Project[]>([]);
  projects$ = this.store.asObservable();

  create(p: Project): Observable<Project> {
    const novo = { ...p, id: this.seq++ };
    this.store.next([...this.store.value, novo]);
    return of(novo);
  }
}
