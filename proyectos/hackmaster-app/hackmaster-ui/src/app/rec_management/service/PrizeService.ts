import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prize } from '../domain/prize';

@Injectable({
  providedIn: 'root',
})
export class PrizeService {
  private apiUrl = 'http://localhost:8080/challenges';

  constructor(private http: HttpClient) {}

  getAllPrizes(challengeId: number): Observable<Prize[]> {
    return this.http.get<Prize[]>(`${this.apiUrl}/${challengeId}/prizes`);
  }

  deletePrize(challengeId: number, prizeId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${challengeId}/prizes/${prizeId}`);
  }

  assignPrize(challengeId: number, prize: Prize): Observable<Prize> {
    return this.http.post<Prize>(`${this.apiUrl}/${challengeId}/prizes/assign`, prize);
  }
}

