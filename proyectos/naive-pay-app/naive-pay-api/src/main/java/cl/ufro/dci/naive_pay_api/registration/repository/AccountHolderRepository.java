package cl.ufro.dci.naive_pay_api.registration.repository;

import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de los titulares de cuenta.
 * Extiende de CrudRepository para proporcionar métodos CRUD básicos.
 * \@author Gerson-art
 * \@version 1.0
 */
public interface AccountHolderRepository extends CrudRepository<AccountHolder, String> {

    /**
     * Busca un titular de cuenta por su RUT.
     *
     * @param rutAccountHolder el RUT del titular de cuenta.
     * @return un Optional que contiene el titular de cuenta si se encuentra, o vacío si no.
     */
    Optional<AccountHolder> findByRut(String rutAccountHolder);
    Optional<AccountHolder> findByEmail(String email);
    List<AccountHolder> findAll();

    boolean existsByVerificationDigit(String verificationDigit);
}
