import { Transaction } from "../domain/transaction";
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map } from "rxjs/operators";
import { Observable, throwError } from "rxjs";
import {BankAccount} from "../domain/bank-account";

const httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'my-auth-token'
    })
};

@Injectable({
    providedIn: 'root'
})
export class GetServiceService {
    private pendingTransactionsEndPoint = "http://localhost:8080/api/transaction/getPending";
    private updateTransactionEndPoint = "http://localhost:8080/api/transaction/update";
    private getBankAccountByRut = "http://localhost:8080/bank-account/getByRut";
    private getCredentialsByRut = "http://localhost:8080/credential/getByRut";

    constructor(private http: HttpClient) { }

    // Obtener transacciones pendientes
    public getPendingTransactions(accountNumber: string | undefined): Observable<Transaction[]> {
        return this.http.get<Transaction[]>(`${this.pendingTransactionsEndPoint}/${accountNumber}`, httpOptions).pipe(
            map((transactions: Transaction[]) => transactions),
            catchError(this.handleError)
        );
    }

    // Actualizar el estado de una transacción (PUT)
    public updateTransactionState(token: string, state: string): Observable<string> {
        const payload = { state };
        return this.http.put(`${this.updateTransactionEndPoint}/${token}`, payload, {
            ...httpOptions,
            responseType: 'text'  // Indicamos que esperamos una respuesta de tipo texto
        }).pipe(
            catchError(this.handleError)
        );
    }

    public getBankAccount(rut: string | null): Observable<BankAccount> {
        return this.http.get<BankAccount>(`${this.getBankAccountByRut}/${rut}`, httpOptions).pipe(
            map((bankAccount: BankAccount) => bankAccount),
            catchError(this.handleError)
        );
    }

    public getCredentials(rut: string | undefined): Observable<any> {
        return this.http.get<any>(`${this.getCredentialsByRut}/${rut}`, httpOptions).pipe(
            map((response: any) => response),
            catchError(this.handleError)
        );
    }

    private handleError(error: any): Observable<never> {
        console.error('An error occurred:', error);
        return throwError(() => new Error('Something went wrong; please try again later.'));
    }
}
