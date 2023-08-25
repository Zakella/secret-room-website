import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { UserAccountInfo } from "../../model/user-account-info";
import { Order } from "../../model/order";
import { UserService } from "../../services/user/user.service";
import { UserDetails } from "../../model/user-details";
import { Router } from "@angular/router";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  userAccount: UserAccountInfo = new UserAccountInfo([]);
  orders: Order[] = [];
  displayOrders: Order[] = [];
  searchQuery: Subject<string> = new Subject<string>();

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    const storedUser = localStorage.getItem("user");
    if (storedUser && storedUser !== "null") {
      const userDetails: UserDetails = JSON.parse(storedUser);
      const email = userDetails.email;

      this.userService.getCustomerOrders(email).subscribe({
        next: (data: UserAccountInfo) => {
          this.userAccount = data;
          this.orders = this.userAccount.orders;
          this.displayOrders = this.orders;
          const shippingAddress = data.orders.map(order => order.shippingAddress);
          console.log(shippingAddress);
        },
        error: (err: any) => {
          console.log(err);
          this.router.navigate(["account-not-found"]);
        }
      });

      this.searchQuery.pipe(
        debounceTime(300)
      ).subscribe((query: string) => this.searchOrders(query));
    }
  }

  onSearch(event: Event) {
    const query = (event.target as HTMLInputElement).value;
    this.searchQuery.next(query);
  }

  searchOrders(query: string) {
    if (query === "") {
      this.displayOrders = this.orders;
      return;
    }

    const queryNumber = Number(query);
    if (isNaN(queryNumber)) {
      return;
    }

    this.displayOrders = this.orders.filter((order: Order) => order.id === queryNumber);
  }
}
