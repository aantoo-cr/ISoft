package cl.ufro.dci.naive_pay_api.payments.service;

import cl.ufro.dci.naive_pay_api.registration.domain.BankAccount;
import cl.ufro.dci.naive_pay_api.registration.domain.Credential;
import cl.ufro.dci.naive_pay_api.payments.util.PasswordHashUtil;
import cl.ufro.dci.naive_pay_api.registration.repository.BankAccountRepository;
import cl.ufro.dci.naive_pay_api.registration.repository.CredentialRepository;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

@Service
public class PublicKeyService {
    private BankAccountRepository bankAccountRepository;
    private CredentialRepository credentialRepository;

    public PublicKeyService(BankAccountRepository bankAccountRepository, CredentialRepository credentialRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.credentialRepository = credentialRepository;
    }
    // Dado un RUT, busca al usuario y compara los HASHES de las claves públicas.
    public boolean authenticatePublicKey(String accountNumber, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Optional<BankAccount> BankAccount = bankAccountRepository.findById(accountNumber);
        Optional<Credential> Credentials = credentialRepository.findByAccountHolder_Rut(BankAccount.get().getRutAccountHolder().getRut());
        PasswordHashUtil hashGenerator = new PasswordHashUtil();
        String hashedPublicKey = hashGenerator.createPasswordHashWithGivenSalt(publicKey, BankAccount.get().getRutAccountHolder().getSalt());
        System.out.print("Hashed public key: " + hashedPublicKey);
        return Credentials.map(value -> value.getPublicKey().equals(hashedPublicKey)).orElse(false);
    }
}
