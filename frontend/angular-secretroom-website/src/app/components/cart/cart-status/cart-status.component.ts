import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { CartService } from "../../../services/cart.service";

@Component({
  selector: 'app-cart-status',
  templateUrl: './cart-status.component.html',
  styleUrls: ['./cart-status.component.css']
})
export class CartStatusComponent implements OnInit, OnDestroy {
  totalQuantity: number = 0;
  private destroy$: Subject<void> = new Subject<void>();

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.updateCartStatus();
    this.cartService.computeCartTotals();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  updateCartStatus() {
    this.cartService.totalQuantity
      .pipe(takeUntil(this.destroy$))
      .subscribe(data => this.totalQuantity = data);
  }
}
