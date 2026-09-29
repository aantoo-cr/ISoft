package cl.ufro.dci.naive_pay_api.payments.domain;

/**
 * Enumeración que representa el estado de una trtransaccion.
 * Esta enumeración contiene los estados posibles de una transaccion.
 * @see Transaction
 * @author joaquin
 * @version 1.0
 */
public enum State {

	PENDING(),
	APPROVED(),
	REJECTED();
}
