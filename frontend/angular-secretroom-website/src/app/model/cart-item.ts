import {Product} from "./product/Product";

export class CartItem {
  quantity: number;

  amount: number;

  product:Product;

  constructor(product: Product, quantity:number) {
    this.product = product;
    this.quantity = quantity;
    this.amount = this.quantity * (this.product.unitPrice || 0);
  }
}

