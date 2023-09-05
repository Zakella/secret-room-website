import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TranslocoService} from "@ngneat/transloco";
import {Subject} from "rxjs";
import {LanguageService} from "../../../services/language.service";

interface Languages {
  name: string;
  code: string;
  icon: string;
}

@Component({
  selector: 'app-language-pannel',
  templateUrl: './language-panel.component.html',
  styleUrls: ['./language-panel.component.css']
})
export class LanguagePanelComponent implements OnInit {
  languages: Languages[] | undefined;
  selectedLanguage: Languages | undefined;

  constructor(private router: Router,
              private translocoService: TranslocoService,
              private languageService: LanguageService) {
  }

  ngOnInit() {
    const lang = localStorage.getItem('lang') || 'Ro';

    this.languages = [
      {name: 'Romanian', code: 'Ro', icon: 'assets/flags/romania.png'},
      {name: 'Russian', code: 'Ru', icon: 'assets/flags/russia.png'},
    ];

    this.selectedLanguage = this.languages.find(language => language.code === lang) || this.languages[0];

    this.translocoService.setActiveLang(this.selectedLanguage.code.toLowerCase());
  }


  putSelectedInStorage() {
    const lang = <string>this.selectedLanguage?.code;
    localStorage.setItem("lang", lang);
    this.translocoService.setActiveLang(lang.toLowerCase());
    this.languageService.changeLanguage(lang);
  }
}
