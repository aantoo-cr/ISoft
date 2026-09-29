import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, map} from "rxjs/operators";
import {House} from "../model/house";
import {Observable} from "rxjs";
import {ApiHouse} from "../model/api-house";


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Authorization: 'my-auth-token'
  })
};


@Injectable({
  providedIn: 'root'
})
export class SearchServiceService {

  private allHousesEndPoint = "http://localhost:8080/houses";
  private searchHousesEndPoint = "http://localhost:8080/houses/search";

  constructor(private http: HttpClient) {
  }

  public getHouses(): Observable<House[]> {
    return this.http.get<ApiHouse[]>(this.allHousesEndPoint).pipe(
      map((apiHouses: ApiHouse[]) => apiHouses.map(this.mapApiHouseToHouse)),
      catchError(this.handleError)
    );
  }

  public searchHouses(name: string): Observable<House[]> {

    return this.http.get<ApiHouse[]>(`${this.searchHousesEndPoint}?query=${name}`).pipe(
      map((apiHouses: ApiHouse[]) => apiHouses.map(this.mapApiHouseToHouse)),
      catchError(this.handleError));
  }

  //nuevo metodo para crear una casa
  public createHouse(house: House): Observable<House> {
    const body: ApiHouse = this.mapHouseToApiHouse(house);
    return this.http.post<ApiHouse>(this.allHousesEndPoint, body).pipe(
      map((apiHouse: ApiHouse) => this.mapApiHouseToHouse(apiHouse)),       // me ayudó la IA
      catchError(this.handleError));
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    return new Observable<never>((observer) => {
      observer.error(new Error('Something went wrong; please try again later.'));
    });
  }

  private mapApiHouseToHouse(apiHouse: ApiHouse): House {
    return {
      id: apiHouse.houId,
      title: apiHouse.houTitle,
      address: apiHouse.houAddress,
      description: apiHouse.houDescription,
      photo: apiHouse.houPhoto,
      available: apiHouse.houAvailable
    };
  }

  //nuevo metodo para mapear de la ui a la api
  private mapHouseToApiHouse(house: House): ApiHouse {
    return {
      houId: house.id,
      houTitle: house.title,
      houAddress: house.address,
      houDescription: house.description,
      houPhoto: house.photo,
      houAvailable: house.available
    };
  }

}
