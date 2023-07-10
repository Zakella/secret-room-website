import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { CartService } from "../../../services/cart.service";
import { CartItem } from "../../../model/cart-item";

@Component({
  selector: 'app-cart-status',
  templateUrl: './cart-status.component.html',
  styleUrls: ['./cart-status.component.css']
})
export class CartStatusComponent implements OnInit, OnDestroy {
  totalQuantity: number = 0;
  cartItems: CartItem[] = [];
  private destroy$: Subject<void> = new Subject<void>();

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.updateCartStatus();
    this.cartItems = this.cartService.cartItems;
    this.cartService.computeCartTotals(this.cartItems);

    this.cartService.totalQuantity
      .pipe(takeUntil(this.destroy$))
      .subscribe(data => this.totalQuantity = data);
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
