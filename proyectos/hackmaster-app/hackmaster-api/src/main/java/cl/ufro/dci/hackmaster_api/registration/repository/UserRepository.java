package cl.ufro.dci.hackmaster_api.registration.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends UserReg> extends CrudRepository<T, Long> {
     Optional<T> findByEmail(String email);
}
