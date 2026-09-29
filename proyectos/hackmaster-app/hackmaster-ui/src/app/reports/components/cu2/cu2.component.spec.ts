import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu2Component } from './cu2.component';

describe('Cu2Component', () => {
  let component: Cu2Component;
  let fixture: ComponentFixture<Cu2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu2Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cu2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
