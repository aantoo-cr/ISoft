import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngresarDispositivoComponent } from './ingresar-dispositivo.component';

describe('IngresarDispositivoComponent', () => {
  let component: IngresarDispositivoComponent;
  let fixture: ComponentFixture<IngresarDispositivoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IngresarDispositivoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IngresarDispositivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
