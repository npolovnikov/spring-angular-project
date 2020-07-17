import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {LessonService} from "../_service/lesson.service";
import {LessonList} from "../_model/lesson-list";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {LessonEditDialogComponent} from "./lesson-edit-dialog/lesson-edit-dialog.component";
import {Course} from "../_model/course";
import {CourseList} from "../_model/course-list";
import {CourseService} from "../_service/course.service";

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.scss']
})
export class LessonComponent implements AfterViewInit {

  courses: CourseList[];
  selectedCourse: CourseList;

  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'name', 'description', 'lessonDateStart', 'lessonDateEnd', 'createDate'];
  data: LessonList[];
  selection = new SelectionModel<LessonList>(false, []);

  resultsLength = 0;
  isLoadingResults = false;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _lessonService: LessonService, private _courseService: CourseService, public dialog: MatDialog) {
  }

  ngAfterViewInit() {
    this._courseService.getCourseList(null, null, 0, 1000)
      .pipe()
      .subscribe(res => this.courses = res.list);
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
  }

  openCreateDialog() {
    if (this.selectedCourse) {
      const dialogRef = this.dialog.open(LessonEditDialogComponent, {
        width: '750px',
        data: {
          lessonIdd: null,
          courseIdd: this.selectedCourse.idd
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        this.refresh();
      });
    }
  }

  openEditDialog() {
    if (this.selection.selected[0] == null) {
      return;
    }
    const dialogRef = this.dialog.open(LessonEditDialogComponent, {
      width: '750px',
      data: {
        lessonIdd: this.selection.selected[0]?.idd,
        courseIdd: this.selectedCourse?.idd
      }
    });
    this.selection.clear();
    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteLesson() {
    if (this.selection.selected[0] == null) {
      return;
    }
    this._lessonService.deleteLessonByIdd(this.selection.selected[0].idd);
    this.selection.clear();
    this.refresh();
  }

  refresh() {
    if (this.selectedCourse.idd) {
      merge(this.sort.sortChange, this.paginator.page)
        .pipe(
          startWith({}),
          switchMap(() => {
            this.isLoadingResults = true;
            return this._lessonService.getLessonListByCourseIdd(
              this.sort.active, this.sort.direction, this.paginator.pageIndex, this.paginator.pageSize, this.selectedCourse.idd);
          }),
          map(data => {
            this.isLoadingResults = false;
            this.isRateLimitReached = false;
            this.resultsLength = data.totalCount;

            return data.list;
          }),
          catchError(() => {
            this.isLoadingResults = false;
            this.isRateLimitReached = true;
            return observableOf([]);
          })
        ).subscribe(data => this.data = data);
    } else {
      this.isLoadingResults = false;
      this.isRateLimitReached = false;
    }
  }
}
