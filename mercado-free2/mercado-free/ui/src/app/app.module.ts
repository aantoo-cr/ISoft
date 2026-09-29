import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { SearchBarComponent } from './component/search-bar/search-bar.component';
import { ProductDetailsComponent } from './component/product-details/product-details.component';
import { ProductListComponent } from './component/product-list/product-list.component';
import { ProductService } from './service/product.service';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { ErrorPopupComponent } from './component/error-popup/error-popup.component';
import { RecentProductsComponent } from './component/recent-products/recent-products.component';


@NgModule({
  declarations: [
    AppComponent,
    SearchBarComponent,
    ProductDetailsComponent,
    ProductListComponent,
    ErrorPopupComponent,
    RecentProductsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [ProductService],
  bootstrap: [AppComponent]
})
export class AppModule { }
