import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminComponentsComponent } from './admin-components.component';

describe('AdminComponentsComponent', () => {
  let component: AdminComponentsComponent;
  let fixture: ComponentFixture<AdminComponentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminComponentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminComponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
