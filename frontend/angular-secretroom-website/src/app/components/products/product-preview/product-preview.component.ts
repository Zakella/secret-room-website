import {Component, OnDestroy, OnInit} from '@angular/core';
import {CartService} from "../../../services/cart.service";
import {takeUntil} from "rxjs/operators";
import {Subject} from "rxjs";
import {CartItem} from "../../../model/cart-item";

@Component({
  selector: 'app-product-preview',
  templateUrl: './product-preview.component.html',
  styleUrls: ['./product-preview.component.css']
})
export class ProductPreviewComponent implements OnInit{
  visible: boolean = true;
  cartItems: CartItem[] ;


  constructor(private cartService:CartService) {
    this.cartItems = cartService.loadCartItemsFromStorage();
  }

  ngOnInit(): void {

    this.showProductPreviewSideBar()
  }


  showProductPreviewSideBar() {
    this.cartService.showPreview.subscribe(
      (data) => {
        console.log(data)
        this.visible = data
      }
    )

  }


}
