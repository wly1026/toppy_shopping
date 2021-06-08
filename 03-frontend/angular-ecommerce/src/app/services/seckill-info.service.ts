import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SeckillActivity } from '../common/seckill-activity';

@Injectable({
  providedIn: 'root'
})

export class SeckillInfoService {

  private seckillUrl = 'http://localhost:8082/api';

  constructor(private httpClient: HttpClient) { }

  getActivities(): Observable<SeckillActivity[]> {

    const activitiesUrl = `${this.seckillUrl}/seckills`;

    return this.httpClient.get<SeckillActivity[]>(activitiesUrl).pipe(
      map(response => response)
    );
  }
}
