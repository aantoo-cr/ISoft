import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Organization } from '../domain/Organization';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private API_URL = 'http://localhost:8080/api/admin';

  private pendingRequestsSubject = new BehaviorSubject<Organization[]>([]);
  public pendingRequests$ = this.pendingRequestsSubject.asObservable();

  constructor(private http: HttpClient) {}

  getPendingRequests(): Observable<Organization[]> {
    return this.http.get<Organization[]>(`${this.API_URL}/requests`).pipe(
      tap((requests) => {
        this.pendingRequestsSubject.next(requests);
      }),
    );
  }

  approveRequest(id: number): Observable<Organization> {
    return this.http
      .post<Organization>(`${this.API_URL}/approve/${id}`, {})
      .pipe(
        tap(() => {
          this.getPendingRequests().subscribe();
        }),
      );
  }

  rejectRequest(id: number): Observable<Organization> {
    return this.http
      .post<Organization>(`${this.API_URL}/reject/${id}`, {})
      .pipe(
        tap(() => {
          this.getPendingRequests().subscribe();
        }),
      );
  }
}
