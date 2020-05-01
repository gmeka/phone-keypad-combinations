import { TestBed } from '@angular/core/testing';

import { PhonenumToWordConverterService } from './phonenum-to-word-converter.service';

describe('PhonenumToWordConverterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PhonenumToWordConverterService = TestBed.get(PhonenumToWordConverterService);
    expect(service).toBeTruthy();
  });
});
