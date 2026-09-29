package cl.ufro.dci.naive_pay_api.authentication.dto;

/**
 * Clase de solicitud para el restablecimiento de contraseña.
 */
public class PasswordResetRequestController {

    private String rut;
    private String newPassword;
    private String confirmPassword;

    // Getters y Setters
    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}