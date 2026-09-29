import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu4Component } from './cu4.component';

describe('Cu4Component', () => {
  let component: Cu4Component;
  let fixture: ComponentFixture<Cu4Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu4Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cu4Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
