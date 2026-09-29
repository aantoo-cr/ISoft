import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InviteParticipantComponent } from './invite-participant.component';

describe('InviteParticipantComponent', () => {
  let component: InviteParticipantComponent;
  let fixture: ComponentFixture<InviteParticipantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InviteParticipantComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InviteParticipantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
