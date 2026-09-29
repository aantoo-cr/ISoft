import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideBarButton3Component } from './side-bar-button3.component';

describe('SideBarButton3Component', () => {
  let component: SideBarButton3Component;
  let fixture: ComponentFixture<SideBarButton3Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SideBarButton3Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SideBarButton3Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
