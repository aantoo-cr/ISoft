import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface HackathonDetail {
  hackatonId: number;
  hackatonName: string;
  hackatonStartDate: string;
  hackatonEndDate: string;
}

export interface ParticipantDetailsResponse {
  actorType: string;
  actorName: string;
  hackatonesDetails: HackathonDetail[];
}

@Injectable({
  providedIn: 'root'
})
export class CU6Service {

  private apiUrl = 'http://localhost:8080/api/reports/participante/lista';

  constructor(private http: HttpClient) { }

    getParticipantDetails(actorId: string | null): Observable<ParticipantDetailsResponse> {
    return this.http.get<ParticipantDetailsResponse>(`${this.apiUrl}/${actorId}`).pipe(
      catchError((error) => {
        console.error('Error al obtener los detalles del participante', error);
        // Retorna un valor por defecto en caso de error
        return of({
          actorType: '',
          actorName: '',
          hackatonesDetails: []
        });
      })
    );
  }
}
