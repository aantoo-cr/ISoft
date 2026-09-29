import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddParticipantButtonComponent } from './add-participant-button.component';

describe('AddParticipantButtonComponent', () => {
  let component: AddParticipantButtonComponent;
  let fixture: ComponentFixture<AddParticipantButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddParticipantButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddParticipantButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
