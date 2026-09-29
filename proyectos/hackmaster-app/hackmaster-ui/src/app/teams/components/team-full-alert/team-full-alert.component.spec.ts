import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamFullAlertComponent } from './team-full-alert.component';

describe('TeamFullAlertComponent', () => {
  let component: TeamFullAlertComponent;
  let fixture: ComponentFixture<TeamFullAlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamFullAlertComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamFullAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
