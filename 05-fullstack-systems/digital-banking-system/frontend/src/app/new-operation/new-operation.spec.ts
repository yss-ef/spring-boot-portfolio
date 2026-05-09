import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewOperation } from './new-operation';

describe('NewOperation', () => {
  let component: NewOperation;
  let fixture: ComponentFixture<NewOperation>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewOperation]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewOperation);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
