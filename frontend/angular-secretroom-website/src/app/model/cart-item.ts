import {Product} from "./product";
import {Size} from "./sizes";

export class CartItem {
  id?: string;

  quantity: number;

  amount: number;

  product:Product;

  size?: Size;




  constructor(product: Product, quantity:number, size?:Size) {
    this.product = product;
    this.size = size;
    this.quantity = quantity;
    this.amount = this.quantity * (this.product.unitPrice || 0);

  }
}

