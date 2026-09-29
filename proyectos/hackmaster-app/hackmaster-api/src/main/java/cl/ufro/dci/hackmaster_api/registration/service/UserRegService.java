package cl.ufro.dci.hackmaster_api.registration.service;

import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;

import cl.ufro.dci.hackmaster_api.registration.domain.UserReg;

@NoRepositoryBean
public interface UserRegService<T extends UserReg> {

  void register(T t);

  Optional<T> update(T t);

  Optional<T> findById(Long id);
}
