import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface Hackaton {
  hackatonId: number;
  hackatonName: string;
  fechaInicio: string;
  fechaTermino: string;
  ingresosGenerados: number;
  totalInscripciones: number;
  totalPremios: number;
}

export interface ActorResponse {
  actorType: string;
  actorName: string;
  hackatones: Hackaton[];
}


@Injectable({
  providedIn: 'root'
})


export class Cu2Service {

  private cu2 = 'http://localhost:8080/api/reports/organizador/lista';


  constructor(private http: HttpClient) { }


  getHackatones(actorId: string | null): Observable<ActorResponse> {
    return this.http.get<ActorResponse>(`${this.cu2}/${actorId}`).pipe(
      catchError((error) => {
        console.error('Error al obtener los datos', error);
        return of({ actorType: '', actorName: '', hackatones: [] }); // Retorna un valor por defecto
      }))

  }


}
