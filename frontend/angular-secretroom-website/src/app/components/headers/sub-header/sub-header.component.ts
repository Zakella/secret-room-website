import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {CartService} from "../../../services/cart.service";

import {takeUntil} from "rxjs/operators";
import {Observable, Subject} from "rxjs";
import {Menu} from 'primeng/menu';
import {AuthenticationService} from "../../../services/authentication.service";
import {UserDetails} from "../../../model/user-details";
import {TranslocoService} from "@ngneat/transloco";

@Component({
  selector: 'app-sub-header',
  templateUrl: './sub-header.component.html',
  styleUrls: ['./sub-header.component.css']
})
export class SubHeaderComponent implements OnInit {
  totalQuantity: number = 0;
  private destroy$: Subject<void> = new Subject<void>();

  mobileMenu: any[] = [];
  mainMenu: any[] = [];
  userDetails: UserDetails | null = null;



  userIsLoggedIn: boolean = false;

  @ViewChild('menuMain') menuMain!: Menu;

  constructor(
    private cartService: CartService,
    private router: Router,
    private authService: AuthenticationService,
    private translocoService: TranslocoService
  ) {}

  ngOnInit() {
    this.subscribeToUserStatus();
    this.subscribeToCartQuantity();
    this.cartService.computeCartTotals();
    this.updateUserDetails();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // Update user details and menu items
  updateUserDetails() {
    this.userDetails = this.authService.getUserDetails(); // Get user details

  }

  // Private methods

  // Subscribe to user status
  private subscribeToUserStatus() {
    this.authService.loggedIn.subscribe((isLoggedIn: boolean) => {
      this.userIsLoggedIn = isLoggedIn;
    });

  }

  // Subscribe to cart quantity
  private subscribeToCartQuantity() {
    this.cartService.totalQuantity
      .pipe(takeUntil(this.destroy$))
      .subscribe(total => {
        this.totalQuantity = total;
      });
  }

  setMobileMenu(event: any) {
    if (this.userIsLoggedIn) {
      this.userDetails = this.authService.getUserDetails(); // Get user details

      this.mobileMenu = [
        {
          label: this.translocoService.translate('Cart'),
          icon: 'pi pi-shopping-cart',
          badge: this.totalQuantity.toString(),
          badgeStyleClass: 'green-badge',
          command: () => {
            this.cartService.setSidebarVisible(true);
          }
        },

        {
          label: this.userDetails?.givenName,
          styleClass: '.vs-title',
          icon: 'pi pi-check',
          command: () => {
            this.router.navigate(['/myAccount']);
          }
        },
        {
          label: this.translocoService.translate('Logout'),
          styleClass: '.vs-title',
          icon: 'pi pi-sign-out',
          command: () => {
            this.logout();
          }
        }

      ]

    } else {

      this.mobileMenu = [
        {
          label: this.translocoService.translate('Cart'),
          icon: 'pi pi-shopping-cart',
          badge: this.totalQuantity.toString(),
          badgeStyleClass: 'green-badge',
          command: () => {
            this.cartService.setSidebarVisible(true);
          }
        },

        {
          label: this.translocoService.translate('Sign In'),
          icon: 'pi pi-sign-in',
          command: () => {
            this.router.navigate(['/login']);
          }
        }
        ]

    }

    this.menuMain.model = this.mobileMenu;
    this.menuMain.toggle(event);

  }

  setMainMenu(event: any) {
    if (this.userIsLoggedIn) {
      this.userDetails = this.authService.getUserDetails(); // Get user details

      this.mainMenu = [
        {
          label: this.userDetails?.givenName,
          styleClass: '.vs-title',
          icon: 'pi pi-check',
          command: () => {
            this.router.navigate(['/myAccount']);
          }
        },
        {
          label: this.translocoService.translate('Logout'),
          styleClass: '.vs-title',
          icon: 'pi pi-sign-out',
          command: () => {
            this.logout();
          }
        }

      ]

    } else {

      this.mainMenu = [

        {
          label: this.translocoService.translate('Sign In') ,
          icon: 'pi pi-sign-in',
          command: () => {
            this.router.navigate(['/login']);
          }
        }
      ]

    }

    this.menuMain.model = this.mobileMenu;
    this.menuMain.toggle(event);

  }


}
