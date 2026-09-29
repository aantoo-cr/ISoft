export interface Participant {
  id?: string;
  username: string;
  password: string;
  email: string;
  rut: string;
  name: string;
  lastname: string;
  city: string;
  country: string;
  phoneNumber: string;
  resetPasswordToken?: string;
  resetPasswordExpires?: string;
}
