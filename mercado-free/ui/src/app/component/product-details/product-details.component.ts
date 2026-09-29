import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Product } from '../../domain/product'

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent {
  @Input() showPopup: boolean = false;
  @Input() product: Product | null = null;
  @Output() popupClosed = new EventEmitter<void>();

  closePopup() {
    this.showPopup = false;
    this.popupClosed.emit();
  }

  openPopup() {
    this.showPopup = true;
  }

}
