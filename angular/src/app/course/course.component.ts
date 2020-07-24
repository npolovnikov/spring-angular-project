import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {CourseList} from "../_model/course-list";
import {CourseService} from "../_service/course.service";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseComponent implements AfterViewInit {
  /* добавили переменную sizeOption выбора кол-ва элементов на стр-це */
  sizeOption: number[] = [2, 5, 10];
  /* изменили колонки на наши */
  displayedColumns: string[] =
    ['idd', 'name', 'description', 'teacherIdd', 'maxCountStudent', 'startDate', 'endDate', 'status', 'createDate'];
  /* модель RoomListDto (room-list.ts) */
  data: CourseList[];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _courseService: CourseService) {
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          /* получаем данные с бэка */
          return this._courseService.getCourseList(
            this.sort.active, this.sort.direction, this.paginator.pageIndex, this.paginator.pageSize);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          /* данные из нашего интерфейса Page */
          this.resultsLength = data.totalCount;
          /* возвращает list румов */
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
}
