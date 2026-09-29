import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KeyDisplayComponent } from './key-display.component';

describe('KeyDisplayComponent', () => {
  let component: KeyDisplayComponent;
  let fixture: ComponentFixture<KeyDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KeyDisplayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KeyDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
