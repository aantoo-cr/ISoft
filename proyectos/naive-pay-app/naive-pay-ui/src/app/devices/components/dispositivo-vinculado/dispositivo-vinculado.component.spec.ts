import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DispositivoVinculadoComponent } from './dispositivo-vinculado.component';

describe('DispositivoVinculadoComponent', () => {
  let component: DispositivoVinculadoComponent;
  let fixture: ComponentFixture<DispositivoVinculadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DispositivoVinculadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DispositivoVinculadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
