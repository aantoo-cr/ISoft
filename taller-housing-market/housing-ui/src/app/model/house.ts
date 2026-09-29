export interface House {
  id?: number;                     //también opcional.
  title: string;
  address: string| undefined;
  description: string| undefined;
  photo: string| undefined;
  available:  boolean| undefined;
}
