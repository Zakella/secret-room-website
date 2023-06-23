import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";

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

  ngOnInit() {


    this.selectedLanguage = {name: 'Romanian', code: 'Ro', icon: 'assets/flags/romania.png'};

    this.languages = [
      this.selectedLanguage,
      {name: 'Russian', code: 'Ru', icon: 'assets/flags/russia.png'},
    ];
  }

  constructor(private router: Router) { }


  putSelectedInStorage() {
    localStorage.setItem("lang", <string>this.selectedLanguage?.code);
  }
}
