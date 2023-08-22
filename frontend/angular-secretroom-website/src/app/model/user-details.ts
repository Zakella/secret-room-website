export class UserDetails {
  accessToken: string;
  refreshToken: string;
  givenName: string;
  familyName: string;
  email: string;

  constructor(accessToken: string, refreshToken: string,givenName: string, familyName: string, email: string) {
    this.accessToken = accessToken;
    this.givenName = givenName;
    this.familyName = familyName;
    this.email = email;
    this.refreshToken = refreshToken;
  }
}
