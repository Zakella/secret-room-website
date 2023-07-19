import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-empty-cart',
  templateUrl: './empty-cart.component.html',
  styleUrls: ['./empty-cart.component.css']
})
export class EmptyCartComponent {
  @Input()
  sidebarVisible: boolean = false;

  goHomePage() {

  }
}
