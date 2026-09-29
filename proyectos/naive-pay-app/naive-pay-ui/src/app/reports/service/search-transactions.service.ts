import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, Observable} from "rxjs";
import {ReportsMetrics} from "../domain/reports-metrics";
import {ChartsDataDto} from "../domain/report-chart-data";
import {Transaction} from "../domain/transaction";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'my-auth-token'
  })
};

@Injectable({
  providedIn: 'root'
})
export class SearchTransactionsService {

  private searchMetricsEndPoint = "http://localhost:8080/report/metrics";
  private searchChartsDataEndPoint = "http://localhost:8080/report/charts";
  private searchTransactionsEndPoint = "http://localhost:8080/report/history";

  constructor(private http: HttpClient) {
  }

  public getMetrics(userId: string | null): Observable<ReportsMetrics> {
    return this.http.get<ReportsMetrics>(`${this.searchMetricsEndPoint}/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  public getChartsData(userId: string | null): Observable<ChartsDataDto> {
    return this.http.get<ChartsDataDto>(`${this.searchChartsDataEndPoint}/${userId}`).pipe(
      catchError(this.handleError)
    );
  }
    private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    return new Observable<never>((observer) => {
      observer.error(new Error('Something went wrong; please try again later.'));
    });
  }
}

