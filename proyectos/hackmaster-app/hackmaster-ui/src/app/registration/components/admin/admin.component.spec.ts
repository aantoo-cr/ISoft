import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu1Component } from './cu1.component';

describe('Cu1Component', () => {
  let component: Cu1Component;
  let fixture: ComponentFixture<Cu1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu1Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cu1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
