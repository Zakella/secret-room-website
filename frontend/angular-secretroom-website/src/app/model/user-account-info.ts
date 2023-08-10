import {Order} from "./order";

export class UserAccountInfo {
  orders: Order[];

  constructor(ordersHistory: Order[]) {
    this.orders = ordersHistory;
  }
}
