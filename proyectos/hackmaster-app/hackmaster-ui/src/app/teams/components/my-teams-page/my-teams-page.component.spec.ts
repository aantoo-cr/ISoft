import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyTeamsPageComponent } from './my-teams-page.component';

describe('MyTeamsPageComponent', () => {
  let component: MyTeamsPageComponent;
  let fixture: ComponentFixture<MyTeamsPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyTeamsPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyTeamsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
