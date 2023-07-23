export class ShippingOption {
  constructor(public id: string, public name: string, public cost: number, public description: string) {
    this.id = id;
    this.name = name;
    this.cost = cost;
    this.description = description;
  }
}
