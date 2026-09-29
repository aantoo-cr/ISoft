export interface Organization {
  id?: number;
  username: string;
  password: string;
  email: string;
  socialNameOrganization: string;
  rutOrganization: string;
  rutRepresentative: string;
  representativeName: string;
  status?: string;
  resetPasswordToken?: string;
  resetPasswordExpires?: string;
}
