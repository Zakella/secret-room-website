import {Component, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";


@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {
  public currentLogo: string = "";

  constructor(private router: Router) {
  }

  ngOnInit() {

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {

        if (this.router.url.includes('/vs')) {
          this.currentLogo = "assets/images/logo/Victorias-Secret-Logo-Cut-3.jpg";
        } else {

          this.currentLogo = "assets/images/logo/Bath-Body-Works-Logo-Cut.png";
        }

      }
    });
  }


  protected readonly alert = alert;

  goToBrandHomePage() {
    if (this.router.url.includes('/vs')) {
      this.router.navigate(['/vs']);
    } else {
      this.router.navigate(['/bb']);
    }
  }
}


