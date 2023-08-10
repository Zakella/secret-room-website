import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user/user.service";
import {UserAccountInfo} from "../../model/user-account-info";
import {UserDetails} from "../../model/user-details";
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  userAccount: UserAccountInfo = new UserAccountInfo([]);

  responsiveOptions: any[] | undefined;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {

    const storedUser = localStorage.getItem("user");
    if (storedUser && storedUser !== "null") {
      const userDetails: UserDetails = JSON.parse(storedUser);
      const email = userDetails.email;

      this.userService.getCustomerOrders(email).subscribe({

        next: (data: UserAccountInfo) => {
          this.userAccount = data;
          console.log(data);
        },
        error: (err: any) => {
          console.log(err);
          this.router.navigate(["account-not-found"]);
        }
      });

      this.responsiveOptions = [
        {
          breakpoint: '1199px',
          numVisible: 1,
          numScroll: 1
        },
        {
          breakpoint: '991px',
          numVisible: 2,
          numScroll: 1
        },
        {
          breakpoint: '767px',
          numVisible: 1,
          numScroll: 1
        }
      ];
    }




  }





}
