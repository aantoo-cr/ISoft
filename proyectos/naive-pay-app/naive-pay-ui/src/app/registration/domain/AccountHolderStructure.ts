

export class AccountHolderStructure {
  rut: string
  verificationDigit: string
  firstName: string
  lastName: string
  email: string
  phoneNumber: string
  documentNumber: string
  address : string
  occupation: string
  salt: string | undefined
  webKey: string | undefined
  dateOfBirth: Date
    
  constructor(
    rut: string,
    verificationDigit: string,
    firstName: string,
    lastName: string,
    email: string,
    phoneNumber: string,
    documentNumber: string,
    address: string,
    occupation: string,
    dateOfBirth: Date
  ) {
    this.rut = rut;
    this.verificationDigit= verificationDigit;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.documentNumber = documentNumber;
    this.address = address;
    this.occupation = occupation;
    this.dateOfBirth = dateOfBirth;
  }

  setSalt(salt: string) {
    this.salt = salt;
  }
  setWebKey(webKey: string) {this.webKey = webKey;}
 }

