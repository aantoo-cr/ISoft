import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WebKeySetupComponent } from './web-key-setup.component';

describe('WebKeySetupComponent', () => {
  let component: WebKeySetupComponent;
  let fixture: ComponentFixture<WebKeySetupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WebKeySetupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WebKeySetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
