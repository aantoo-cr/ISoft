import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UpdatePhotoService {
  private errorSubject = new Subject<void>();
  fileErrorSubject = this.errorSubject.asObservable();

  emitFailWhenUpdatePhoto(): void {
    console.log('Emitiendo error en la foto...');
    this.errorSubject.next();
  }
}
