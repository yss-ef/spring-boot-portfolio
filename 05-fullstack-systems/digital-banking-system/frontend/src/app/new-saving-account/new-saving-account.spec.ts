import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewSavingAccount } from './new-saving-account';

describe('NewSavingAccount', () => {
  let component: NewSavingAccount;
  let fixture: ComponentFixture<NewSavingAccount>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewSavingAccount]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewSavingAccount);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
