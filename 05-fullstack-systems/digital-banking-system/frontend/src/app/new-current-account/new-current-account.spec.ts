import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCurrentAccount } from './new-current-account';

describe('NewCurrentAccount', () => {
  let component: NewCurrentAccount;
  let fixture: ComponentFixture<NewCurrentAccount>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewCurrentAccount]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewCurrentAccount);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
