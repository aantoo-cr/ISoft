import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsProjectCompleteComponent } from './details-project-complete.component';

describe('DetailsProjectCompleteComponent', () => {
  let component: DetailsProjectCompleteComponent;
  let fixture: ComponentFixture<DetailsProjectCompleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailsProjectCompleteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsProjectCompleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
