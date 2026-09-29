import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction } from '../domain/transaction.model';  // Importa el modelo

@Injectable({
  providedIn: 'root'
})
export class FundsService {
  private apiUrl = 'http://localhost:8080/api/funds'; // Ajusta el puerto si es necesario

    /**
     * Constructor de FundsService que inyecta HttpClient para realizar peticiones HTTP.
     *
     * @param http - Cliente HTTP utilizado para hacer solicitudes HTTP al servidor.
     */
  constructor(private http: HttpClient) {}

    /**
     * Obtiene las transacciones realizadas por un usuario específico.
     *
     * Este método realiza una solicitud GET a la API para obtener las transacciones asociadas
     * con el número de cuenta proporcionado.
     *
     * @param numCuenta - Número de cuenta del usuario cuyas transacciones se desean obtener.
     * @returns Observable con un array de objetos Transaction. Cada objeto Transaction contiene detalles de una transacción.
     */
  obtenerTransacciones(numCuenta: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/transacciones/${numCuenta}`);
  }


    /**
     * Obtiene el saldo disponible de un usuario específico.
     *
     * Este método realiza una solicitud GET a la API para obtener el saldo de la cuenta
     * de un usuario basado en el número de cuenta proporcionado.
     *
     * @param numCuenta - Número de cuenta del usuario cuyo saldo se desea obtener.
     * @returns Observable con el saldo del usuario como un número. El saldo puede ser un valor decimal.
     */
  obtenerSaldo(numCuenta: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/saldo/${numCuenta}`);
  }

    /**
     * Agrega fondos a la cuenta de un usuario específico.
     *
     * Este método realiza una solicitud POST a la API para agregar un monto de dinero a la cuenta
     * de un usuario. La solicitud contiene el número de cuenta del usuario y el monto a añadir.
     *
     * @param numCuenta - Número de cuenta del usuario al que se desean agregar fondos.
     * @param monto - Monto de dinero que se desea añadir a la cuenta del usuario.
     * @returns Observable con la transacción creada después de añadir los fondos. El objeto Transaction incluye detalles de la transacción.
     */
  agregarFondos(numCuenta: string, monto: number): Observable<Transaction> {
      const body = '{ "numCuenta": "' + numCuenta + '", "monto": ' + monto + '}'
    return this.http.post<Transaction>(`${this.apiUrl}/agregar-fondos`, body, {headers : new HttpHeaders({ 'Content-Type': 'application/json' })});
  }
}

