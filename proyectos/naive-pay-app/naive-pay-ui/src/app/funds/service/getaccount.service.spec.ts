import { TestBed } from '@angular/core/testing';

import { GetaccountService } from './getaccount.service';

describe('GetaccountService', () => {
  let service: GetaccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetaccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
