import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";


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
          this.currentLogo = "vs";
        } else {

          this.currentLogo = "bb";
        }

      }
    });
  }


}


