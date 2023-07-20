import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CartService} from "../../../services/cart.service";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";



@Component({
  selector: 'app-sub-header',
  templateUrl: './sub-header.component.html',
  styleUrls: ['./sub-header.component.css']
})
export class SubHeaderComponent implements OnInit {
  totalQuantity: number = 0;
  private destroy$: Subject<void> = new Subject<void>();

  items: any[] = [];

  constructor(private cartService: CartService) {}

  ngOnInit() {
    this.cartService.totalQuantity
      .pipe(takeUntil(this.destroy$))
      .subscribe(total => {
        this.totalQuantity = total;
        this.updateItems(); // обновляем элементы меню
      });
    this.cartService.computeCartTotals();
  }

  updateItems() {
    this.items = [
      {
        label: 'Cart',
        icon: 'pi pi-shopping-cart',
        badge: this.totalQuantity.toString(), // badge принимает строку, поэтому мы преобразуем число в строку
        badgeStyleClass: 'green-badge',
        command: () => {
          this.cartService.setSidebarVisible(true);
        }
      },
      {
        label: 'Sign In',
        icon: 'pi pi-user',
        command: () => {
          // Действие при нажатии
        }
      }
      // Другие элементы меню...
    ];
  }





}
