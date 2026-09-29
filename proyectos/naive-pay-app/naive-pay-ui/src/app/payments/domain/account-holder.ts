export interface AccountHolder {
    rut: string;
    verificationDigit: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    documentNumber: string;
    address: string;
    ocupation: string;
    salt: number;
    webKey: string;
    dateOfBirth: Date;
}
