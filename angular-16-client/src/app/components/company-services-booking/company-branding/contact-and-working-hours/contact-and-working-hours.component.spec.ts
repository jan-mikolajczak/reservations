import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactAndWorkingHoursComponent } from './contact-and-working-hours.component';

describe('ContactAndWorkingHoursComponent', () => {
  let component: ContactAndWorkingHoursComponent;
  let fixture: ComponentFixture<ContactAndWorkingHoursComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactAndWorkingHoursComponent]
    });
    fixture = TestBed.createComponent(ContactAndWorkingHoursComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
