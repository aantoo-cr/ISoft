import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { urlTokenGuard } from './url-token.guard';

describe('urlTokenGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => urlTokenGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
