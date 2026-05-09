import { ComponentFixture, TestBed } from '@angular/core/testing';

// @ts-ignore
import { Operations } from './operations';

describe('Operations', () => {
  let component: Operations;
  let fixture: ComponentFixture<Operations>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Operations]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Operations);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
