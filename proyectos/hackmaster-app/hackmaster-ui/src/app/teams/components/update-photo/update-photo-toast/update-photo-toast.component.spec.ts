import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePhotoToastComponent } from './update-photo-toast.component';

describe('UpdatePhotoToastComponent', () => {
  let component: UpdatePhotoToastComponent;
  let fixture: ComponentFixture<UpdatePhotoToastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdatePhotoToastComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdatePhotoToastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
