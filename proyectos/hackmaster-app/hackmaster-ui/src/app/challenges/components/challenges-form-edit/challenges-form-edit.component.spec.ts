import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengesFormEditComponent } from './challenges-form-edit.component';

describe('ChallengesFormEditComponent', () => {
  let component: ChallengesFormEditComponent;
  let fixture: ComponentFixture<ChallengesFormEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChallengesFormEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChallengesFormEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
