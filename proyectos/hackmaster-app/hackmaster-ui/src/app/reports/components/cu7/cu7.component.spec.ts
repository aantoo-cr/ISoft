import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu7Component } from './cu7.component';

describe('Cu7Component', () => {
  let component: Cu7Component;
  let fixture: ComponentFixture<Cu7Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu7Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cu7Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
