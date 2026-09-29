import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Participant } from '../domain/Participant';

@Injectable({
  providedIn: 'root',
})
export class ParticipantService {
  private API_AUTH_URL = 'http://localhost:8080/auth';
  private API_PARTICIPANT_URL = 'http://localhost:8080/api/participants';

  constructor(private http: HttpClient) {}

  addParticipant(participant: Participant): Observable<Participant> {
    const body = participant;
    return this.http.post<Participant>(
      this.API_AUTH_URL + '/register/participant',
      body,
    );
  }

  getUserByEmail(email: string): Observable<Participant> {
    return this.http.get<Participant>(
      this.API_PARTICIPANT_URL + '/search/' + encodeURIComponent(email),
    );
  }

  updateUser(userData: any): Observable<any> {
    return this.http.put(this.API_PARTICIPANT_URL + '/update', userData);
  }
}
