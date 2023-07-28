export class Address {
  constructor(street: string, city: string, country: string, zipCode: string) {
    this.street = street;
    this.city = city;
    this.country = country;
    this.zipCode = zipCode;
  }
    street: string;
    city: string;
    country: string;
    zipCode: string;
}
