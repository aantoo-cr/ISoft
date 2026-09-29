import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginExampleComponent } from './login-example.component';

describe('LoginExampleComponent', () => {
  let component: LoginExampleComponent;
  let fixture: ComponentFixture<LoginExampleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginExampleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginExampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
