import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { SearchBarComponent } from './search-bar.component';
import { ProductService } from 'src/app/service/product.service';

describe('SearchBarComponent', () => {
  
  
  let component: SearchBarComponent;
  let fixture: ComponentFixture<SearchBarComponent>;
  let productService: ProductService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchBarComponent ],
      providers: [
        { provide: ProductService, useValue: jasmine.createSpyObj('ProductService', ['searchProduct']) }
      ]
    })
    .compileComponents();

    productService = TestBed.inject(ProductService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit products when search is successful', () => {
    const products = [{ proName: 'Product 1' }, { proName: 'Product 2' }];
    (productService.searchProduct as jasmine.Spy).and.returnValue(of(products));
    spyOn(component.productsFound, 'emit');

    component.searchProducts('test');

    expect(component.productsFound.emit).toHaveBeenCalledWith(products);
  });

  it('should emit error message when search returns 400 error', () => {
    const errorResponse = new HttpErrorResponse({
      error: 'Bad Request',
      status: 400,
      statusText: 'Bad Request'
    });
    (productService.searchProduct as jasmine.Spy).and.returnValue(throwError(errorResponse));
    spyOn(component.searchError, 'emit');

    component.searchProducts('test');

    expect(component.searchError.emit).toHaveBeenCalledWith('No hay coincidencias');
  });

  it('should emit error message when search text is invalid', () => {
    spyOn(component.searchError, 'emit');
  
    component.searchProducts('1test');
  
    expect(component.searchError.emit).toHaveBeenCalledWith('Texto de búsqueda no válido');
  });


});
