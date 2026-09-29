import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu6Component } from './cu6.component';

describe('Cu6Component', () => {
  let component: Cu6Component;
  let fixture: ComponentFixture<Cu6Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu6Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cu6Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
