import { Transaction } from "../../domain/transaction";
import { State } from "../../domain/state"; 
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { catchError, map } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import { BankAccount } from "../../domain/bank-account";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'my-auth-token'
  })
};
@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  private apiUrl = 'http://localhost:8080/report'
  private getBankAccountByRut = "http://localhost:8080/bank-account/getByRut";
  private getCredentialsByRut = "http://localhost:8080/credential/getByRut";

  constructor(private http: HttpClient) { }

  public getBankAccount(rut: string | null): Observable<BankAccount> {
    return this.http.get<BankAccount>(`${this.getBankAccountByRut}/${rut}`, httpOptions).pipe(
      map((bankAccount: BankAccount) => bankAccount),
      catchError(this.handleError)
    );
  }
  getAllTransaction(accountNumber: string | undefined ): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/history/${accountNumber}`);
  }
  getFilteredTransactions(accountNumber: string | undefined, startDate: string, endDate: string, minAmount: number, maxAmount: number, estadoFiltro: string): Observable<any> {
    let params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate)
      .set('minAmount', minAmount.toString())
      .set('maxAmount', maxAmount.toString())
      .set('estadoFiltro', estadoFiltro);
    return this.http.get<Transaction[]>(`${this.apiUrl}/filtro/${accountNumber}/${startDate}/${endDate}/${minAmount}/${maxAmount}/${estadoFiltro}`);
  }
  getFilteredTransactionsquitState(accountNumber: string | undefined, startDate: string, endDate: string, minAmount: number, maxAmount: number): Observable<any> {
    let params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate)
      .set('minAmount', minAmount.toString())
      .set('maxAmount', maxAmount.toString())
    return this.http.get<Transaction[]>(`${this.apiUrl}/filtro/${accountNumber}/${startDate}/${endDate}/${minAmount}/${maxAmount}`);
  }
  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Something went wrong; please try again later.'));
  }

}
