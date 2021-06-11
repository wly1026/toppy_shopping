import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SeckillActivity } from 'src/app/common/seckill-activity';
import { SeckillInfoService } from 'src/app/services/seckill-info.service';

@Component({
  selector: 'app-activity-details',
  templateUrl: './activity-details.component.html',
  styleUrls: ['./activity-details.component.css']
})
export class ActivityDetailsComponent implements OnInit {

  activity: SeckillActivity = new SeckillActivity();

  constructor(private seckillInfoService: SeckillInfoService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {

    const theActivityId: number = +this.route.snapshot.paramMap.get('id')!;

    this.seckillInfoService.getActivity(theActivityId).subscribe(
      data => {
        this.activity = data;
      }
    )
  }

  purchase() {
    
  }

}
