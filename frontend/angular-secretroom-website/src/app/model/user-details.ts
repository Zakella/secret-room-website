export class UserDetails {

  firstName: string;
  lastName: string;
  email: string;

  constructor(givenName: string, familyName: string, email: string) {

    this.firstName = givenName;
    this.lastName = familyName;
    this.email = email;

  }
}
