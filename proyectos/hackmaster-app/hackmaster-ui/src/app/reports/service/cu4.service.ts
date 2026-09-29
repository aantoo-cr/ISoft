import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface JudgeHackathon {
  hackatonId: number;
  hackatonName: string;
  fechaInicio: string;
  fechaTermino: string;
}

export interface JudgeResponse {
  actorType: string;
  actorName: string;
  hackatones: JudgeHackathon[];
}

@Injectable({
  providedIn: 'root'
})
export class CU4Service {

  private apiUrl = 'http://localhost:8080/api/reports/juez/lista';

  constructor(private http: HttpClient) { }

  getHackathonsByJudge(judgeId: number): Observable<JudgeResponse> {
    return this.http.get<JudgeResponse>(`${this.apiUrl}/${judgeId}`).pipe(
      catchError((error) => {
        console.error('Error al obtener los hackatones del juez', error);
        // Retorno por defecto en caso de error
        return of({
          actorType: '',
          actorName: '',
          hackatones: []
        });
      })
    );
  }

  // Método para obtener el actorId de manera dinámica
  getActorIdByEmailAndType(email: string, actorType: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/getId/${email}/${actorType}`);
  }
}
