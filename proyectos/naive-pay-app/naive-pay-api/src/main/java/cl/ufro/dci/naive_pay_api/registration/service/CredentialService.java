package cl.ufro.dci.naive_pay_api.registration.service;

import cl.ufro.dci.naive_pay_api.registration.domain.Credential;
import cl.ufro.dci.naive_pay_api.registration.domain.AccountHolder;
import cl.ufro.dci.naive_pay_api.registration.domain.KeyState;
import cl.ufro.dci.naive_pay_api.registration.dto.CredentialDTO;
import cl.ufro.dci.naive_pay_api.registration.repository.AccountHolderRepository;
import cl.ufro.dci.naive_pay_api.registration.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.DHParameterSpec;
import java.util.Optional;

/**
 * Servicio para gestionar las credenciales de los usuarios, permitiendo operaciones de almacenamiento y consulta.
 */
@Service
public class CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private DHParameterSpec dhSpec;

    /**
     * Metodo para guardar una credencial en la base de datos.
     *
     * @param credentialDTO el DTO con los datos de la credencial.
     * @return la credencial guardada.
     */
    public Credential saveCredential(CredentialDTO credentialDTO) {
        Optional<AccountHolder> accountHolderOptional = accountHolderRepository.findByRut(credentialDTO.getRutAccountHolder());

        AccountHolder accountHolder = accountHolderOptional
                .orElseThrow(() -> new IllegalArgumentException("AccountHolder con el rut " + credentialDTO.getRutAccountHolder() + " no encontrado."));

        Credential credential = new Credential();

        String publicKey = credentialDTO.getPublicKey();
        credential.setPublicKey(publicKey);
        credential.setKeyState(KeyState.valueOf(credentialDTO.getKeyState()));
        credential.setAccountHolder(accountHolder);

        return credentialRepository.save(credential);
    }

    public Optional<Credential> getCredentials(String rut) {
        return credentialRepository.findByAccountHolder_Rut(rut);
    }

}
