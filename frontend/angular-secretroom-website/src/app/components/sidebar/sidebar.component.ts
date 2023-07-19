import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {Router} from "@angular/router";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit , OnDestroy{

  sidebarVisible: boolean = false;

  cartIsEmpty: boolean = false;

  private ngUnsubscribe = new Subject<void>();

  constructor(private cartService: CartService, private router: Router) {
  }


  ngOnInit(): void {
    this.observeSidebarVisible()
    this.subscribeToCartItems();
  }


  private subscribeToCartItems() {
    this.cartService.cartItems.pipe(takeUntil(this.ngUnsubscribe)).subscribe(
      (items) => {
        this.cartIsEmpty = items.length === 0;
      }
    );
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }


  private observeSidebarVisible() {
    this.cartService.sidebarVisible.subscribe(
      (data) => {
        this.sidebarVisible = data;
      }
    )
  }

  checkout() {
    this.router.navigate(['/checkout']);
    this.sidebarVisible = false;

  }
}
