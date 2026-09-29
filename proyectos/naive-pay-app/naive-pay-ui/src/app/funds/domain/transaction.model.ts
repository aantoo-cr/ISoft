import {BankAccount} from "../../payments/domain/bank-account";

/**
 * Representa una transacción financiera con detalles específicos.
 *
 * Una transacción incluye información sobre el monto, cuentas involucradas, estado de la transacción, y su token único.
 */
export interface Transaction {
    /**
     * Hora de la transacción en formato de cadena de texto.
     * Representa el momento exacto en que se realizó la transacción.
     *
     * @type {string}
     */
    time: string;

    /**
     * Cuenta de destino de la transacción.
     * Representa la cuenta a la que se envía el dinero.
     *
     * @type {BankAccount}
     */
    destinationAccount: BankAccount;

    /**
     * Fecha de la transacción en formato legible.
     * Representa el día en que se realizó la transacción.
     *
     * @type {string}
     */

    date: string;

    /**
     * Estado actual de la transacción.
     * Puede representar estados como "completada", "pendiente", "fallida", etc.
     *
     * @type {string}
     */

    state: string;

    /**
     * Token único de la transacción.
     * Un identificador único generado para cada transacción.
     *
     * @type {string}
     */
    token: string;




    /**
     * Monto de la transacción.
     * La cantidad de dinero que se transfirió en esta transacción.
     *
     * @type {number}
     */
  amount: number;

    /**
     * Cuenta de origen de la transacción.
     * Representa la cuenta desde la cual se envía el dinero.
     *
     * @type {BankAccount}
     */
  originAccount: BankAccount;


}

