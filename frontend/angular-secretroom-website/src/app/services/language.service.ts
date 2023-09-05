import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  private languageSubject = new BehaviorSubject<string>(localStorage.getItem('lang') || 'Ro');
  language$ = this.languageSubject.asObservable();

  changeLanguage(lang: string) {
    localStorage.setItem('lang', lang);
    this.languageSubject.next(lang);
  }
}
