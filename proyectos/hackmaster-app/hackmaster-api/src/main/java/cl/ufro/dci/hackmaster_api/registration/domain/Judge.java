package cl.ufro.dci.hackmaster_api.registration.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Judge")
@EqualsAndHashCode(callSuper = true)
public class Judge extends Participant {
  public Judge(String username, String email, String password, String rut, String name, String lastname,
      String city, String country, String phoneNumber) {
    super(username, email, password, rut, name, lastname, city, country, phoneNumber);
  }
}
