export class User {
  email: string;
  password: string;
  firstname?: string;
  lastName?: string;

  constructor(email: string, password: string, firstname: string, lastName: string) {
    this.email = email;
    this.password = password;
    this.firstname = firstname;
    this.lastName = lastName;
  }
}
