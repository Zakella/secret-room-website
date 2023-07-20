import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";



@Component({
  selector: 'app-sub-header',
  templateUrl: './sub-header.component.html',
  styleUrls: ['./sub-header.component.css']
})
export class SubHeaderComponent implements OnInit{


  items: any[] = [];

  ngOnInit() {
    this.items = [
      {
        label: 'Cart',
        icon: 'pi pi-shopping-cart', // Replace with your icon
        command: () => {
          // Do something when Item 1 is clicked
        }
      },
      {
        label: 'Sign In',
        icon: 'pi pi-user', // Replace with your icon
        command: () => {
          // Do something when Item 2 is clicked
        }
      }
      // Add more items as needed
    ];


}
}
