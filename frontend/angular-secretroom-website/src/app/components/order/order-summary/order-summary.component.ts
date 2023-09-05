import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PurchaseService} from "../../../services/purchase.service";
import {OrderReview} from "../../../model/order-review";
import {LanguageService} from "../../../services/language.service";

@Component({
  selector: 'app-order-summary',
  templateUrl: './order-summary.component.html',
  styleUrls: ['./order-summary.component.css']
})
export class OrderSummaryComponent implements OnInit {

  orderReview: OrderReview | null = null;
  currentLang: string;

  constructor(private route: ActivatedRoute,
              private orderService: PurchaseService,
              private languageService: LanguageService) {
    this.currentLang = 'Ro';
  }

  ngOnInit(): void {
    this.subscribeToLanguageChanges();
    this.loadOrderDetails();
  }

  private subscribeToLanguageChanges(): void {
    this.languageService.language$.subscribe(lang => {
      this.currentLang = lang;
    });
  }

  private loadOrderDetails(): void {
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
