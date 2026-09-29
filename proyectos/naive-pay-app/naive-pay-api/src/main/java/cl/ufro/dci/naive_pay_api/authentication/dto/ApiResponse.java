package cl.ufro.dci.naive_pay_api.authentication.dto;

/**
 * La clase se utiliza comúnmente para enviar respuestas consistentes en las APIs,
 * proporcionando tanto el estado de la operación (éxito o error) como un mensaje detallado
 * para que el cliente pueda entender el resultado de la solicitud.
 */
public class ApiResponse {

    /**
     * Indicamos si la operación fue exitosa.
     * true si la operación fue exitosa, false en caso contrario.
     */
    private boolean success;

    /**
     * Mensaje informativo que acompaña la respuesta.
     * Este mensaje puede ser utilizado para proporcionar detalles adicionales
     * sobre el estado de la operación.
     */
    private String message;

    /**
     * Creamos el constructor de la clase ApiResponse, inicializando los parámetros success y message.
     *
     * @param success El estado de la operación (éxito o fracaso).
     * @param message Un mensaje informativo que describe el resultado de la operación.
     */
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Con esta función obtenemos el estado de la operación.
     *
     * @return true si la operación fue exitosa, false si hubo un error.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Establecemos el estado de la operación.
     *
     * @param success El estado de la operación (éxito o fracaso).
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Obtenemos el mensaje informativo asociado a la respuesta.
     *
     * @return El mensaje informativo de la respuesta.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establecemos el mensaje informativo de la respuesta.
     *
     * @param message El mensaje informativo que describe el resultado de la operación.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}