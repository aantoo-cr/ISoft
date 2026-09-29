import { HttpErrorResponse } from '@angular/common/http';
import { Component, Output, EventEmitter } from '@angular/core';
import { ProductService } from 'src/app/service/product.service';
import { StringValidator } from 'src/app/util/string-validator';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  @Output() productsFound = new EventEmitter<any>();
  @Output() searchError = new EventEmitter<string>();

  constructor(private productService: ProductService) { }

  searchProducts(searchText: string) {
    if (!this.isValidSearchText(searchText)) {
      this.searchError.emit('Texto de búsqueda no válido');
      return;
    }

    this.productService.searchProduct(searchText).subscribe(
      products => {
        this.productsFound.emit(products);
      },
      (error: HttpErrorResponse) => {
        if (error.status === 400) {
          this.searchError.emit("No hay coincidencias");
        }
      }
    );
  }

  private isValidSearchText(searchText: string): boolean {
    return !StringValidator.startsWithNumber(searchText) &&
    StringValidator.hasOnlyLettersNumbersSpaces(searchText) &&
    StringValidator.validateStringLength(searchText, 1, 64) &&
    StringValidator.isNotEmpty(searchText);
  }
}
