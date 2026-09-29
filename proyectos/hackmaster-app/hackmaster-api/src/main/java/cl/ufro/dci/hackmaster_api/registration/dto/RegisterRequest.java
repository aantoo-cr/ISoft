package cl.ufro.dci.hackmaster_api.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private boolean isParticipant;

    // Atributos específicos para Participant
    private String name;
    private String lastname;
    private String city;
    private String country;
    private String rut;
    private String phoneNumber;

    // Atributos específicos para Organization
    private String socialNameOrganization;
    private String rutOrganization;
    private String rutRepresentative;
    private String representativeName;
}
