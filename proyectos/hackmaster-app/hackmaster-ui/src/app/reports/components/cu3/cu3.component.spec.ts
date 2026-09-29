import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu3Component } from './cu3.component';

describe('Cu3Component', () => {
  let component: Cu3Component;
  let fixture: ComponentFixture<Cu3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu3Component]
    })
      .compileComponents();

    fixture = TestBed.createComponent(Cu3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

