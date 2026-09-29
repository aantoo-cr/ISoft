import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DesvincularDispositivoComponent } from './desvincular-dispositivo.component';

describe('DesvincularDispositivoComponent', () => {
  let component: DesvincularDispositivoComponent;
  let fixture: ComponentFixture<DesvincularDispositivoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DesvincularDispositivoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DesvincularDispositivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
