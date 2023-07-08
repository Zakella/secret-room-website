import { Injectable } from '@angular/core';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  private searchResults: Subject<string> = new Subject<string>();

  setSearchResults(results: string): void {
    this.searchResults.next(results);
  }

}
