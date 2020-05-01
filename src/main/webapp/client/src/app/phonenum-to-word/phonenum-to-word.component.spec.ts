import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhonenumToWordComponent } from './phonenum-to-word.component';

describe('PhonenumToWordComponent', () => {
  let component: PhonenumToWordComponent;
  let fixture: ComponentFixture<PhonenumToWordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhonenumToWordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhonenumToWordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
