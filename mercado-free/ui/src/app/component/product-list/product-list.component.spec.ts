import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { ProductListComponent } from './product-list.component';
import { ChangeDetectorRef } from '@angular/core';
import { Product } from "../../domain/product";

describe('ProductListComponent', () => {
  let component: ProductListComponent;
  let fixture: ComponentFixture<ProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductListComponent ],
      providers: [ ChangeDetectorRef ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set products correctly', () => {
    const products = [{ proName: 'Product 1' }, { proName: 'Product 2' }];
    component.products = products;
    expect(component.products).toEqual(products);
  });

  it('should render products correctly', () => {
    const products = [{ proName: 'Product 1' }, { proName: 'Product 2' }];
    component.products = products;
    fixture.detectChanges();
    const productElements = fixture.debugElement.queryAll(By.css('.product'));
    expect(productElements.length).toBe(2);
  });

  it('should handle product click correctly', () => {
    const products = [{ proName: 'Product 1' }, { proName: 'Product 2' }];
    component.products = products;
    fixture.detectChanges();
    spyOn(component, 'openProductDetails');
    const productElements = fixture.debugElement.queryAll(By.css('.product'));
    const mockProduct: Product = {
      proId: 1,
      proName: 'Product 1',
      proDescription: 'Description',
      proPrice: 10.99,
      proBrand: 'Brand',
      proImage: 'Image'
    };
    productElements[0].triggerEventHandler('click', mockProduct);
    expect(component.openProductDetails).toHaveBeenCalledWith(mockProduct);

  });

  it('should handle popup closed correctly', () => {
    component.handlePopupClosed();
    expect(component.showProductDetails).toBeFalse();
  });
});
