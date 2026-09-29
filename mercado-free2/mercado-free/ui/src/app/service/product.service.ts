import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class ProductService {


  private HttpClient : HttpClient;
  private getAllProductUrl = "http://localhost:8080/product";
  private getProductUrl = "http://localhost:8080/product/";
  private searchProductUrl = "http://localhost:8080/search/";

  constructor(httpClient: HttpClient) {
    this.HttpClient = httpClient;
   }

   /**
    * Gets all products from the backend
    * @returns Observable<any>
    */

   public getAllProduct() {
      return this.HttpClient.get(this.getAllProductUrl, {responseType: 'json'});
   }

   /**
    * Get a specific product from the backend by id
    * @param id
    * @returns Observable<any>
    */

    public getProduct(id: number) {
      return this.HttpClient.get(this.getProductUrl + id, {responseType: 'json'});
    }

    /**
     * Given a text from the search bar, it returns all the products that match with the text
     * @param text
     * @returns Observable<any>
     */

    public searchProduct(text: string) {
      return this.HttpClient.get(this.searchProductUrl + text, {responseType: 'json'});
    }

}
