import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamSideBarComponent } from './my-team-side-bar.component';

describe('TeamSideBarComponent', () => {
  let component: TeamSideBarComponent;
  let fixture: ComponentFixture<TeamSideBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TeamSideBarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamSideBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
