import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from "../../services/cart.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  sidebarVisible: boolean = false;

  constructor(private cartService: CartService, private router: Router) {
  }


  ngOnInit(): void {
    this.observeSidebarVisible()
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
