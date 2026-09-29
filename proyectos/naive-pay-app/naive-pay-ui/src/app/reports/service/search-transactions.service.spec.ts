import { TestBed } from '@angular/core/testing';

import { SearchTransactionsService } from './search-transactions.service';

describe('SearchTransactionsService', () => {
  let service: SearchTransactionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SearchTransactionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
