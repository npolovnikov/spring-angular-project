import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {InstrumentList} from "../_model/instrument-list";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {InstrumentService} from "../_service/instrument.service";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {LessonList} from "../_model/lesson-list";
import {LessonService} from "../_service/lesson.service";
import {AuthService} from "../_service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.scss']
})
export class LessonComponent implements AfterViewInit {
  sizeOption:number[] = [2, 5, 10];
  displayedColumns: string[] =
    ['idd', 'name', 'description', 'courseIdd', 'roomIdd', 'extraInstruments', 'lessonDateStart', 'lessonDateEnd', 'createDate'];
  data: LessonList[];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _lessonService: LessonService,
              private _authService: AuthService,
              private router: Router) {}

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this._lessonService.getLessonList(
            this.sort.active, this.sort.direction, this.paginator.pageIndex, this.paginator.pageSize);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.resultsLength = data.totalCount;

          return data.list;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          // Catch if the GitHub API has reached its rate limit. Return empty data.
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }

  logOut(): void {
    this._authService.logout()
      .pipe()
      .subscribe(res => {
        this.router.navigateByUrl('/login');
      });
  }
}
