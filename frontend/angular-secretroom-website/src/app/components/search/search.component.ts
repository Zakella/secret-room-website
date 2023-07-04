import {Component, OnInit} from '@angular/core';
import {debounceTime, distinctUntilChanged, Subject, switchMap} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{

  searchSubject: Subject<string> = new Subject<string>();

  brand: string = '';
  defaultRoot:string = '';
  constructor(private router:Router) {
  }



  ngOnInit(): void {

    const currentUrl = this.router.url;
    this.brand = currentUrl.includes('vs') ? 'vs' : currentUrl.includes('bb') ? 'bb' : '';
    this.defaultRoot = `${this.brand}/all-${this.brand}-products`

  }


  search(value: string): void {
    const currentUrl = this.router.url;
    this.brand = currentUrl.includes('vs') ? 'vs' : currentUrl.includes('bb') ? 'bb' : '';
    this.defaultRoot = `${this.brand}/all-${this.brand}-products`

    if (value){
      this.router.navigate([`${this.brand}/search/${value}`])
    }

  else {

      this.router.navigate([this.defaultRoot])
    }


    // console.log(value);
    // this.searchSubject.next(value);
  }


}
