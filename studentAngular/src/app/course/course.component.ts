import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {Course} from "../_model/course";
import {CourseService} from "../_service/course.service";
import {SelectionModel} from "@angular/cdk/collections";
import {RoomEditDialogComponent} from "../room/room-edit-dialog/room-edit-dialog.component";
import {CourseEditDialogComponent} from "./course-edit-dialog/course-edit-dialog.component";
import {RoomService} from "../_service/room.service";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseComponent implements AfterViewInit {
  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'name','description',
    'maxCountStudent', 'startDate', 'endDate', 'createDate'];
  selection = new SelectionModel<Course>(false, []);
  data: Course[];
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _courseService: CourseService, public dialog: MatDialog) {}

  ngAfterViewInit() {

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();

  }

  openEditDialog() {
    const dialogRef = this.dialog.open(CourseEditDialogComponent, {
      width: '850px',
      data: this.selection.selected[0]?.idd,
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  deleteEntity() {
    if (this.selection.selected[0] == null) {
      return;
    }
    this._courseService.deleteCourse(this.selection.selected[0].idd);

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
          return this._courseService.getCourseList(
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
