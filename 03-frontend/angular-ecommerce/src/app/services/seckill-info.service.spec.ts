import { TestBed } from '@angular/core/testing';

import { SeckillInfoService } from './seckill-info.service';

describe('SeckillInfoService', () => {
  let service: SeckillInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SeckillInfoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
