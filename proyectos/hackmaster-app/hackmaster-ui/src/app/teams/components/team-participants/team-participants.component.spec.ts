import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamParticipantsComponent } from './team-participants.component';

describe('TeamParticipantsComponent', () => {
  let component: TeamParticipantsComponent;
  let fixture: ComponentFixture<TeamParticipantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamParticipantsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamParticipantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
