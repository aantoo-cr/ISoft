import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodigoVincularComponent } from './codigo-vincular.component';

describe('CodigoVincularComponent', () => {
  let component: CodigoVincularComponent;
  let fixture: ComponentFixture<CodigoVincularComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodigoVincularComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodigoVincularComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
