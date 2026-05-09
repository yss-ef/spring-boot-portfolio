import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCustomer } from './new-customer';

describe('NewCustomer', () => {
  let component: NewCustomer;
  let fixture: ComponentFixture<NewCustomer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewCustomer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewCustomer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
