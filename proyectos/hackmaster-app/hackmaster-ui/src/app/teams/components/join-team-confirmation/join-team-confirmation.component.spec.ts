import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JoinTeamConfirmationComponent } from './join-team-confirmation.component';

describe('JoinTeamConfirmationComponent', () => {
  let component: JoinTeamConfirmationComponent;
  let fixture: ComponentFixture<JoinTeamConfirmationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JoinTeamConfirmationComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JoinTeamConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
