import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChallengesFormInicialComponent } from './challenges-form-inicial.component';

describe('ChallengesFormInicialComponent', () => {
  let component: ChallengesFormInicialComponent;
  let fixture: ComponentFixture<ChallengesFormInicialComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChallengesFormInicialComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChallengesFormInicialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
