import { Injectable } from '@angular/core';
import CryptoJS from 'crypto-js';

/**
 * Servicio que implementa una versión simulada del algoritmo Diffie-Hellman.
 * Utiliza criptografía simétrica (SHA256 y HMAC) para generar claves y calcular secretos compartidos.
 */
@Injectable({
    providedIn: 'root'
})
export class DiffieHellmanService {

    /**
     * Crea una instancia del servicio DiffieHellmanService.
     */
    constructor() { }

    /**
     * Genera un par de claves simulado (no Diffie-Hellman puro).
     * Se genera una clave privada aleatoria y una clave pública derivada usando SHA256.
     *
     * @returns Un objeto con la clave privada y la clave pública generadas.
     * @example
     * const { privateKey, publicKey } = diffieHellmanService.generateKeyPair();
     */
    generateKeyPair(): { privateKey: string, publicKey: string } {
        const privateKey = CryptoJS.lib.WordArray.random(256 / 8).toString(CryptoJS.enc.Hex);
        const publicKey = CryptoJS.SHA256(privateKey).toString(CryptoJS.enc.Hex);

        return { privateKey, publicKey };
    }

    /**
     * Calcula el "secreto compartido" de manera simulada.
     * Utiliza HMAC con la clave privada y la clave pública para derivar un secreto compartido.
     *
     * @param privateKey - La clave privada utilizada para calcular el secreto compartido.
     * @param publicKey - La clave pública con la que se calcula el secreto compartido.
     * @returns El "secreto compartido" derivado.
     * @example
     * const sharedSecret = diffieHellmanService.generateSharedSecret(privateKey, publicKey);
     */
    generateSharedSecret(privateKey: string, publicKey: string): string {
        const sharedSecret = CryptoJS.HmacSHA256(publicKey, privateKey).toString(CryptoJS.enc.Hex);
        return sharedSecret;
    }

    /**
     * Verifica si una clave pública corresponde a una clave privada.
     * Se genera una clave pública esperada a partir de la clave privada y se compara con la clave pública proporcionada.
     *
     * @param publicKey - La clave pública que se desea verificar.
     * @param privateKey - La clave privada asociada a la clave pública.
     * @returns `true` si la clave pública coincide con la clave privada, `false` en caso contrario.
     * @example
     * const isValid = diffieHellmanService.verifyKeyPair(publicKey, privateKey);
     */
    verifyKeyPair(publicKey: string, privateKey: string): boolean {
        try {
            const expectedPublicKey = CryptoJS.SHA256(privateKey).toString(CryptoJS.enc.Hex);
            return expectedPublicKey === publicKey;
        } catch (error) {
            console.error('Error verifying key pair:', error);
            return false;
        }
    }
}
