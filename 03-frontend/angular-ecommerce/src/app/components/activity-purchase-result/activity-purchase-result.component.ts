import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ActivityOrder } from 'src/app/common/activity-order';
import { SeckillActivity } from 'src/app/common/seckill-activity';
import { SeckillInfoService } from 'src/app/services/seckill-info.service';

@Component({
  selector: 'app-activity-purchase-result',
  templateUrl: './activity-purchase-result.component.html',
  styleUrls: ['./activity-purchase-result.component.css']
})
export class ActivityPurchaseResultComponent implements OnInit {

  createOrderSuccess: Boolean = false;
  info: String = "";
  orderId: number;

  constructor(private seckillInfo: SeckillInfoService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const activityId: number = +this.route.snapshot.paramMap.get('id');
    const userId: number = +this.route.snapshot.paramMap.get('user');
    this.seckillInfo.getPurchaseResponse(activityId, userId).subscribe(
      data => {
        this.info = data.info;
        this.createOrderSuccess = data.createOrderSuccess;
        this.orderId = data.orderId;
      }
    )
  }

}
