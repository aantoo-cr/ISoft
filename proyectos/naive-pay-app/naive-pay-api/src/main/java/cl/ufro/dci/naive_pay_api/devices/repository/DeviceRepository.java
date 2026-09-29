package cl.ufro.dci.naive_pay_api.devices.repository;

import cl.ufro.dci.naive_pay_api.devices.domain.Devices;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Devices, Long> {
    List<Devices> findByAccountHolder(AccountHolder accountHolder);
    List<Devices> findByAccountHolderRut(String rut);
    Optional<Devices> findByAccountHolderAndNameAndModelo(AccountHolder accountHolder, String deviceName, String deviceModel);
}