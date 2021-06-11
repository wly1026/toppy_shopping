import { HttpClient} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityOrder } from '../common/activity-order';
import { SeckillActivity } from '../common/seckill-activity';

@Injectable({
  providedIn: 'root'
})

export class SeckillInfoService {

  private seckillUrl = 'http://localhost:8082/api';

  constructor(private httpClient: HttpClient) { }

  getActivities(): Observable<SeckillActivity[]> {
    const activitiesUrl = `${this.seckillUrl}/seckills`;
    return this.httpClient.get<SeckillActivity[]>(activitiesUrl);
  }

  getActivity(activityId: number): Observable<SeckillActivity> {
    const activityUrl = `${this.seckillUrl}/seckill/item/${activityId}`;
    return this.httpClient.get<SeckillActivity>(activityUrl);
  }

  getPurchaseResponse(activityId: number, userId: number): Observable<PurchaseResponse> {
    const purchaseUrl = `${this.seckillUrl}/buy/${userId}/${activityId}`;
    return this.httpClient.get<PurchaseResponse>(purchaseUrl);
  }

  getOrder(orderId: String): Observable<ActivityOrder> {
    const orderUrl = `${this.seckillUrl}/orderQuery/${orderId}`;
    console.log("orderUrl: " + orderUrl);
    return this.httpClient.get<ActivityOrder>(orderUrl);
  }

  processPay(orderId: String): Observable<ActivityOrder> {
    const orderUrl = `${this.seckillUrl}/payOrder/${orderId}`;
    return this.httpClient.post<ActivityOrder>(orderUrl, orderId);
  }
}

interface PurchaseResponse {
    info: String
    orderId: number
    createOrderSuccess: boolean
}
