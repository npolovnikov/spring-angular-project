import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Course} from "../_model/course";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CourseService} from "../_service/course.service";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {Lesson} from "../_model/lesson";
import {LessonService} from "../_service/lesson.service";
import {RoomService} from "../_service/room.service";
import {MatDialog} from "@angular/material/dialog";
import {RoomEditDialogComponent} from "../room/room-edit-dialog/room-edit-dialog.component";
import {LessonEditDialogComponent} from "./lesson-edit-dialog/lesson-edit-dialog.component";
import {SelectionModel} from "@angular/cdk/collections";
import {Room} from "../_model/room";

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.scss']
})
export class LessonComponent implements AfterViewInit {
  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select','id', 'name','description',
    'extraInstruments', 'lessonDateStart', 'lessonDateEnd'];
  data: Lesson[];
  selection = new SelectionModel<Lesson>(false, []);
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _lessonService: LessonService, public dialog: MatDialog) {}

  ngAfterViewInit() {

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();

  }

  openEditDialog() {
    const dialogRef = this.dialog.open(LessonEditDialogComponent, {
      width: '850px',
      data: this.selection.selected[0]?.id,
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteEntity() {
    if (this.selection.selected[0] == null) {
      return;
    }
    this._lessonService.deleteLesson(this.selection.selected[0].id);

    this.refresh();
    this.selection.clear();
    this.refresh();
  }

  refresh() {
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
}
