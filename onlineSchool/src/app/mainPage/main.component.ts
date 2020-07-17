import {HttpClient} from '@angular/common/http';
import {Component, ViewChild, AfterViewInit} from '@angular/core';

@Component({
  selector: 'app-room',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements AfterViewInit {
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  ngAfterViewInit(): void {
  }

}
