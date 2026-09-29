import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

export interface TeamRanking {
  teamName: string;
  promedioNotaGrupo: number;
}

export interface JudgeGrade {
  teamName: string;
  promedioNotaGrupo: number;
  notaJuez: number;
}

export interface JudgeHackathonReport {
  actorType: string;
  actorName: string;
  hackatonName: string;
  ranking: TeamRanking[];
  grades: JudgeGrade[];
}

@Injectable({
  providedIn: 'root'
})
export class CU5Service {
  private apiUrl = 'http://localhost:8080/api/reports/juez/hackaton';

  constructor(private http: HttpClient) { }

    getHackathonReportByJudge(judgeId: string | null, hackathonId: string | null): Observable<JudgeHackathonReport> {
    return this.http.get<JudgeHackathonReport>(`${this.apiUrl}/${judgeId}/${hackathonId}`).pipe(
      catchError((error) => {
        console.error('Error al obtener el reporte del hackathon', error);
        // Valor por defecto en caso de error
        return of({
          actorType: '',
          actorName: '',
          hackatonName: '',
          ranking: [],
          grades: []
        });
      })
    );
  }
}
