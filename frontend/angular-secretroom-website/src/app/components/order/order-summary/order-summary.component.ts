import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PurchaseService} from "../../../services/purchase.service";
import {OrderReview} from "../../../model/order-review";

@Component({
  selector: 'app-order-summary',
  templateUrl: './order-summary.component.html',
  styleUrls: ['./order-summary.component.css']
})
export class OrderSummaryComponent implements OnInit {


  orderReview: OrderReview | null = null;


  constructor(private route: ActivatedRoute, private orderService: PurchaseService) {
  }

  ngOnInit(): void {
    let uuid = this.route.snapshot.paramMap.get('id');
    if (uuid != null) {
      this.orderService.getOrderDetails(uuid).subscribe({
        next: (orderReview) => {
          this.orderReview = orderReview;
          console.log(orderReview);
        },
        error: (err) => {
          console.log(err.error.statusCode);
        }
      });
    }
  }

}
