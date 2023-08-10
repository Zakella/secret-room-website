import {Order} from "./order";

export class UserAccountInfo {
  ordersHistory: Order[];

  constructor(ordersHistory: Order[]) {
    this.ordersHistory = ordersHistory;
  }
}
