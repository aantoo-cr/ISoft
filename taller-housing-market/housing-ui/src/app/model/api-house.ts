export interface ApiHouse {
  houId?: number;                     // dejo el id de la casa como opcional para que no genere problemas al presionar CREAR una casa.
  houTitle: string;
  houAddress: string| undefined;
  houDescription: string| undefined;
  houPhoto: string| undefined;
  houAvailable:  boolean| undefined;
}
