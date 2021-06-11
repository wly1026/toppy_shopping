import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityPurchaseResultComponent } from './activity-purchase-result.component';

describe('ActivityPurchaseResultComponent', () => {
  let component: ActivityPurchaseResultComponent;
  let fixture: ComponentFixture<ActivityPurchaseResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ActivityPurchaseResultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityPurchaseResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
