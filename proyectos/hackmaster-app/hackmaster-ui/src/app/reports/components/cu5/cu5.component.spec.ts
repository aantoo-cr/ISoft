import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Cu5Component } from './cu5.component';

describe('Cu5Component', () => {
  let component: Cu5Component;
  let fixture: ComponentFixture<Cu5Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Cu5Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Cu5Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
