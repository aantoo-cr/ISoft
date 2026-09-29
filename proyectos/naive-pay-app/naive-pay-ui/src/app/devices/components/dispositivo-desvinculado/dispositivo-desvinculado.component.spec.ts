import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DispositivoDesvinculadoComponent } from './dispositivo-desvinculado.component';

describe('DispositivoDesvinculadoComponent', () => {
  let component: DispositivoDesvinculadoComponent;
  let fixture: ComponentFixture<DispositivoDesvinculadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DispositivoDesvinculadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DispositivoDesvinculadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
