package cl.ufro.dci.naive_pay_api.devices.controller;

import cl.ufro.dci.naive_pay_api.devices.domain.Devices;
import cl.ufro.dci.naive_pay_api.devices.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controlador para gestionar el dispositivo vinculado.
 * Este controlador maneja las solicitudes relacionadas al verificar si al iniciar sesion hay un dispositivo vinculado,
 * en este caso, busca mediante el rut del accountHolder si es dispositivo esta vinculado, si no esta hay que agregar un dispositivo.
 *
 */

@RestController
@RequestMapping("/devices")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceController {

    /**
     * Servicio que contiene la lógica para obtener el dispositivo del titular.
     */
    private DeviceService deviceService;


    @Autowired
    public void DispositivosController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * Busca el dispositivo mediante el rut del titular.
     *
     * @param rut El rut del titular.
     * @return ResponseEntity con un mensaje de éxito si encuentra el dispositivo o error si no lo encuentra.
     */
    @GetMapping("/by-rut")
    public ResponseEntity<List<Devices>> getDevicesByRut(@RequestParam String rut) {
        List<Devices> devices = deviceService.getDevicesByRut(rut);

        if (!devices.isEmpty()) {
            return ResponseEntity.ok(devices);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
}