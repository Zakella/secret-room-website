import {Component, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import {Router} from '@angular/router';
import {SearchService} from './search.service';
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  private searchTerms: Subject<string> = new Subject<string>();
  brand: string = '';
  defaultRoot: string = '';

  searchForm = this.fb.control('');

  constructor(private router: Router, private searchService: SearchService, private fb: FormBuilder) {
  }

  search(value: string): void {
    this.searchTerms.next(value);
  }

  searchProducts(value: string): void {
    const currentUrl = this.router.url;
    this.brand = currentUrl.includes('vs') ? 'vs' : currentUrl.includes('bb') ? 'bb' : '';
    this.defaultRoot = `${this.brand}/all-${this.brand}-products`;

    this.searchService.setSearchResults(value);
    if (!this.brand) {
      this.brand = 'vs';
    }

    if (value) {
      this.router.navigate([`${this.brand}/search/${value}`]);
    } else {
      this.router.navigate([this.defaultRoot]);
    }
  }

  ngOnInit(): void {
    this.searchForm.valueChanges.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(value => {
      if (value !== null) {
        console.log(value);  // optional: logs the current form value
        this.searchProducts(value);
      }
    });
  }


}
