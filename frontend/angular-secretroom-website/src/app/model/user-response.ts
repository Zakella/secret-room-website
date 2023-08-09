export class UserResponse {
  accessToken: string;
  givenName: string;
  familyName: string;
  email: string;

  constructor(accessToken: string, givenName: string, familyName: string, email: string) {
    this.accessToken = accessToken;
    this.givenName = givenName;
    this.familyName = familyName;
    this.email = email;
  }
}
