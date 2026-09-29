import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionDispositivosComponent } from './gestion-dispositivos.component';

describe('GestionDispositivosComponent', () => {
  let component: GestionDispositivosComponent;
  let fixture: ComponentFixture<GestionDispositivosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionDispositivosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionDispositivosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
