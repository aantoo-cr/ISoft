import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DescriptionTeamComponent } from './description-team.component';

describe('DescriptionTeamComponent', () => {
  let component: DescriptionTeamComponent;
  let fixture: ComponentFixture<DescriptionTeamComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DescriptionTeamComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DescriptionTeamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
