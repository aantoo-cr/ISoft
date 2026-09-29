import { Component, Input, ChangeDetectorRef } from '@angular/core';
import { Product } from 'src/app/domain/product';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent {
  @Input() set products(value: any[]) {
    this._products = value;
    this.changeDetectorRef.detectChanges();
  }

  get products(): any[] {
    return this._products;
  }

  private _products!: any[];
  selectedProduct: Product | null = null;
  showProductDetails: boolean = false;

  constructor(private changeDetectorRef: ChangeDetectorRef) { }

  openProductDetails(product: Product) {
    this.selectedProduct = product;
    this.showProductDetails = true;
  }

  handlePopupClosed() {
    this.showProductDetails = false;
  }
}
