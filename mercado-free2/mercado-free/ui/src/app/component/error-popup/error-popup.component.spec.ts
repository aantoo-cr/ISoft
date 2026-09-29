import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { ErrorPopupComponent } from './error-popup.component';

describe('ErrorPopupComponent', () => {
  let component: ErrorPopupComponent;
  let fixture: ComponentFixture<ErrorPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ErrorPopupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ErrorPopupComponent);
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

  it('should set showPopup to false when closePopup is called', () => {
    component.showPopup = true;
    component.closePopup();
    expect(component.showPopup).toBeFalse();
  });
});
