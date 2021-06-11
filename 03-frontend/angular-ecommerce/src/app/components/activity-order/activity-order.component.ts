import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ActivityOrder } from 'src/app/common/activity-order';
import { SeckillActivity } from 'src/app/common/seckill-activity';
import { SeckillInfoService } from 'src/app/services/seckill-info.service';

@Component({
  selector: 'app-activity-order',
  templateUrl: './activity-order.component.html',
  styleUrls: ['./activity-order.component.css']
})
export class ActivityOrderComponent implements OnInit {

  order: ActivityOrder = new ActivityOrder();
  activity: SeckillActivity = new SeckillActivity();

  constructor(private seckillInfo: SeckillInfoService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    const orderId: String= this.route.snapshot.paramMap.get('id');
    console.log(orderId);

    this.seckillInfo.getOrder(orderId).subscribe(
      data => {
        console.log(data);
        this.order = data;
        this.updateActivity();
      }
    )
  
  }

  updateActivity() {
    console.log(this.order.seckillActivityId);
    this.seckillInfo.getActivity(this.order.seckillActivityId).subscribe(
      data => {
        this.activity = data;
      }
    )
  }

  onSubmit() {
    this.seckillInfo.processPay(this.order.orderNo).subscribe(
      data => {
        this.order = data;
      }
    );
  }
}
