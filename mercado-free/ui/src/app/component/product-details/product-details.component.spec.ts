import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { ProductDetailsComponent } from './product-details.component';

describe('ProductDetailsComponent', () => {
  let component: ProductDetailsComponent;
  let fixture: ComponentFixture<ProductDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit popupClosed event when closePopup is called', () => {
    spyOn(component.popupClosed, 'emit');
    component.closePopup();
    expect(component.popupClosed.emit).toHaveBeenCalled();
  });

  it('should open popup when openPopup is called', () => {
    component.openPopup();
    expect(component.showPopup).toBeTrue();
  });

  it('should close popup when closePopup is called', () => {
    component.showPopup = true;
    component.closePopup();
    expect(component.showPopup).toBeFalse();
  });
});
