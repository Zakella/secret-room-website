import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {CartService} from "../../../services/cart.service";

import {takeUntil} from "rxjs/operators";
import {Observable, Subject} from "rxjs";
import {Menu} from 'primeng/menu';
import {AuthenticationService} from "../../../services/authentication.service";
import {UserDetails} from "../../../model/user-details";

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
  userDetails: UserDetails | null = null;

  commonItems: any[] = [];

  loggedInItems: any[] = [];

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
    this.subscribeToUserStatus();
    this.subscribeToCartQuantity();
    this.cartService.computeCartTotals();
    this.updateUserDetails();
  }

  onMenuClick(event: any) {
    this.authService.isLoggedIn().subscribe((isLoggedIn: boolean) => {
      if (isLoggedIn) {
        this.updateUserDetails();
        this.menuMain.model = this.loggedInItems;
      } else {
        this.menuMain.model = this.loggedOutItems;
      }
      this.menuMain.toggle(event);
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // Update user details and menu items
  updateUserDetails() {
    this.userDetails = this.authService.getUserDetails(); // Get user details
    this.updateMenuItems();
  }

  // Private methods

  // Subscribe to user status
  private subscribeToUserStatus() {
    this.authService.isLoggedIn().subscribe((isLoggedIn: boolean) => {
      this.commonItems = isLoggedIn ? this.loggedInItems : this.loggedOutItems;
      this.loadItemsMenu();
    });
    this.updateUserDetails();
  }

  // Subscribe to cart quantity
  private subscribeToCartQuantity() {
    this.cartService.totalQuantity
      .pipe(takeUntil(this.destroy$))
      .subscribe(total => {
        this.totalQuantity = total;
        this.loadItemsMenu();
      });
  }

  // Update menu items based on user details
  private updateMenuItems() {
    this.loggedInItems = [
      {
        label: this.userDetails?.givenName,
        styleClass: '.vs-title',
        icon: 'pi pi-check',
        command: () => {
          this.router.navigate(['/myAccount']);
        }
      },
      {
        label: 'Logout',
        styleClass: '.vs-title',
        icon: 'pi pi-sign-out',
        command: () => {
          this.logout();
        }
      }
    ];
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
}
