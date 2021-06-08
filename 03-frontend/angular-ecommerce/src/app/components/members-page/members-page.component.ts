import { Component, OnInit } from '@angular/core';
import { SeckillActivity } from 'src/app/common/seckill-activity';
import { SeckillInfoService } from 'src/app/services/seckill-info.service';

@Component({
  selector: 'app-members-page',
  templateUrl: './members-page.component.html',
  styleUrls: ['./members-page.component.css']
})
export class MembersPageComponent implements OnInit {

  activities : SeckillActivity[] = [];

  constructor(private seckillInfoService: SeckillInfoService) { }

  ngOnInit(): void {
    this.hanle();
  }

  hanle() {
    this.seckillInfoService.getActivities().subscribe(
      data => {
        console.log(data);
        this.activities = data;
      }
    )
  }
}


