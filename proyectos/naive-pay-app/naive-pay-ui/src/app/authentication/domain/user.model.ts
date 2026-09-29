export class User {
    rut: string;
    verificationDigit: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    documentNumber: string;
    address: string;
    occupation: string;
    dateOfBirth: Date | null;
    role: string;

    constructor(data: Partial<User>) {
        this.rut = data.rut || '';
        this.verificationDigit = data.verificationDigit || '';
        this.firstName = data.firstName || '';
        this.lastName = data.lastName || '';
        this.email = data.email || '';
        this.phoneNumber = data.phoneNumber || '';
        this.documentNumber = data.documentNumber || '';
        this.address = data.address || '';
        this.occupation = data.occupation || '';
        this.dateOfBirth = data.dateOfBirth
            ? Array.isArray(data.dateOfBirth)
                ? new Date(data.dateOfBirth[0], data.dateOfBirth[1] - 1, data.dateOfBirth[2])
                : new Date(data.dateOfBirth)
            : null;
        this.role = data.role || '';
    }

    getFullName(): string {
        return `${this.firstName} ${this.lastName}`;
    }

    getFormattedRUT(): string {
        return `${this.rut}-${this.verificationDigit}`;
    }
}
