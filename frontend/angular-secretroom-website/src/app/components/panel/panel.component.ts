import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";


@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit, AfterViewInit{
  public currentLogo : string = "";

  constructor( private router:Router) {}

  ngOnInit() {

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.currentLogo = this.router.url;
        console.log(this.router.url);

      }
    });
  }

  doSomethingOnRouteChange() {
    this.currentLogo = this.router.url;
  }

  ngAfterViewInit(): void {
    console.log(this.router.url);
    console.log(this.router);
  }
}


