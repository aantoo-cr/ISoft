import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu8Component } from './cu8.component';

describe('Cu8Component', () => {
  let component: Cu8Component;
  let fixture: ComponentFixture<Cu8Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu8Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cu8Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
