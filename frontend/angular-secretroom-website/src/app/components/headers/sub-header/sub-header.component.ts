import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {CartService} from "../../../services/cart.service";

import {takeUntil} from "rxjs/operators";
import {Observable, Subject} from "rxjs";
import {Menu} from 'primeng/menu';
import {AuthenticationService} from "../../../services/authentication.service"; // Импортируйте Menu из primeng

@Component({
  selector: 'app-sub-header',
  templateUrl: './sub-header.component.html',
  styleUrls: ['./sub-header.component.css']
})
export class SubHeaderComponent implements OnInit {
  totalQuantity: number = 0;
  private destroy$: Subject<void> = new Subject<void>();

  itemsMob: any[] = [];
  items: any[] = [];

  commonItems: any[] = [];
  loggedInItems: any[] = [
    {
      label: 'My account',
      icon: 'pi pi-check',
      command: () => {
        this.router.navigate(['/myAccount']);
      }
    },

    {
      label: 'Logout',
      icon: 'pi pi-sign-out',
      command: () => {
        this.router.navigate(['/login']);
      }
    },

  ];
  loggedOutItems: any[] = [
    {
      label: 'Sign In',
      icon: 'pi pi-sign-in',
      command: () => {
        this.router.navigate(['/login']);
      }
    }
  ];

  isLoggedIn$: Observable<boolean>;

  @ViewChild('menuMain') menuMain!: Menu;


  constructor(private cartService: CartService, private router:Router, private authService: AuthenticationService) {
    this.isLoggedIn$ = this.authService.isLoggedIn();
  }

  ngOnInit() {
    this.authService.isLoggedIn().subscribe((isLoggedIn: boolean) => {
      this.commonItems = isLoggedIn ? this.loggedInItems : this.loggedOutItems;
      this.loadItemsMenu();
    });

    this.cartService.totalQuantity
      .pipe(takeUntil(this.destroy$))
      .subscribe(total => {
        this.totalQuantity = total;
        this.loadItemsMenu();
      });
    this.cartService.computeCartTotals();
  }

  loadItemsMenu() {
    this.itemsMob = [
      {
        label: 'Cart',
        icon: 'pi pi-shopping-cart',
        badge: this.totalQuantity.toString(),
        badgeStyleClass: 'green-badge',
        command: () => {
          this.cartService.setSidebarVisible(true);
        }
      },
      ...this.commonItems
    ];

    this.items = [...this.commonItems];
  }

  onMenuClick(event: any) {
    console.log("click!")
    this.authService.isLoggedIn().subscribe((isLoggedIn: boolean) => {
      if (isLoggedIn) {

        this.menuMain.model = this.loggedInItems;
      } else {

        this.menuMain.model = this.loggedOutItems;
      }
      this.menuMain.toggle(event);
    });
  }
}
