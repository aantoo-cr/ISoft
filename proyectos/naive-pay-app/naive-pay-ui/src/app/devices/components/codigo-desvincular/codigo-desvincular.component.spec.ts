import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodigoDesvincularComponent } from './codigo-desvincular.component';

describe('CodigoDesvincularComponent', () => {
  let component: CodigoDesvincularComponent;
  let fixture: ComponentFixture<CodigoDesvincularComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodigoDesvincularComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodigoDesvincularComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
