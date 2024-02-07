import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDishesByRestaurantComponent } from './view-dishes-by-restaurant.component';

describe('ViewDishesByRestaurantComponent', () => {
  let component: ViewDishesByRestaurantComponent;
  let fixture: ComponentFixture<ViewDishesByRestaurantComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewDishesByRestaurantComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewDishesByRestaurantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
