import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VincularDispositivoComponent } from './vincular-dispositivo.component';

describe('VincularDispositivoComponent', () => {
  let component: VincularDispositivoComponent;
  let fixture: ComponentFixture<VincularDispositivoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VincularDispositivoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VincularDispositivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
