import { Component } from '@angular/core';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Router } from '@angular/router';
import { SearchService } from './search.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {
  private searchTerms: Subject<string> = new Subject<string>();
  brand: string = '';
  defaultRoot: string = '';

  constructor(private router: Router, private searchService: SearchService) {
    this.searchTerms.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(value => {
      this.searchProducts(value);
    });
  }

  search(value: string): void {
    this.searchTerms.next(value);
  }

  searchProducts(value: string): void {
    const currentUrl = this.router.url;
    this.brand = currentUrl.includes('vs') ? 'vs' : currentUrl.includes('bb') ? 'bb' : '';
    this.defaultRoot = `${this.brand}/all-${this.brand}-products`;

    this.searchService.setSearchResults(value);

    if (value) {
      this.router.navigate([`${this.brand}/search/${value}`]);
    } else {
      this.router.navigate([this.defaultRoot]);
    }
  }
}
