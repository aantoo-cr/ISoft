import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SideBarButton4Component } from './side-bar-button4.component';

describe('SideBarButton4Component', () => {
  let component: SideBarButton4Component;
  let fixture: ComponentFixture<SideBarButton4Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SideBarButton4Component]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SideBarButton4Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
