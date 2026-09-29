import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentProductsComponent } from './recent-products.component';

describe('RecentProductsComponent', () => {
  let component: RecentProductsComponent;
  let fixture: ComponentFixture<RecentProductsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecentProductsComponent]
    });
    fixture = TestBed.createComponent(RecentProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
