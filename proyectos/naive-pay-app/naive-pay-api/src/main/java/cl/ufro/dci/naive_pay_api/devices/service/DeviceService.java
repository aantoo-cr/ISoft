package cl.ufro.dci.naive_pay_api.devices.service;

import cl.ufro.dci.naive_pay_api.devices.domain.Devices;
import cl.ufro.dci.naive_pay_api.devices.repository.DeviceRepository;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que contiene la lógica para obtener el dispositivo del titular.
 * Este servicio maneja las operaciones de obtener el dispositivo el titular y buscar el dispositivo especifico del titular.
 */
@Service
public class DeviceService {

    /**
     * Repositorio para manejar las operaciones relacionadas con los dispositivos.
     */
    private final DeviceRepository deviceRepository;

    /**
     * Servicio para obtener los dispositivos del titular.
     */
    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Obtiene los dispositivos del titular.
     * @return true si el dispositivo fue obtenido, false en caso contrario.
     */
    public List<Devices> obtenerDispositivosPorTitular(AccountHolder accountHolder) {
        return deviceRepository.findByAccountHolder(accountHolder);
    }

    /**
     * Busca por el metodo getDevicesByRut, el dispositivo segun el rut del titular.
     *
     * @return true si el dispositivo fue encontrado, falso si es el caso contrario
     */
    public List<Devices> getDevicesByRut(String rut) {
        return deviceRepository.findByAccountHolderRut(rut);
    }


    /**
     * Busca el dispositivo especifico del titular.
     *
     * @param deviceName El nombre del dispositivo.
     * @param deviceModel El modelo del dispositivo.
     * @return true si el dispositivo especifico fue encontrado fue encontrado, falso si es el caso contrario
     */
    public Optional<Devices> buscarDispositivoEspecifico(AccountHolder accountHolder, String deviceName, String deviceModel) {
        return deviceRepository.findByAccountHolderAndNameAndModelo(accountHolder, deviceName, deviceModel);
    }
}