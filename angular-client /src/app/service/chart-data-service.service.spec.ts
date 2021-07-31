import { TestBed } from '@angular/core/testing';

import { ChartDataServiceService } from './chart-data-service.service';

describe('ChartDataServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ChartDataServiceService = TestBed.get(ChartDataServiceService);
    expect(service).toBeTruthy();
  });
});
