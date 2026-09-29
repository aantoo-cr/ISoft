import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupExampleComponent } from './signup-example.component';

describe('SignupExampleComponent', () => {
  let component: SignupExampleComponent;
  let fixture: ComponentFixture<SignupExampleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SignupExampleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SignupExampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
