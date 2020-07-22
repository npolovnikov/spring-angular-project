import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {SelectionModel} from "@angular/cdk/collections";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {MatDialog} from "@angular/material/dialog";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {LessonService} from "../_service/lesson.service";
import {LessonList} from "../_model/lesson-list";
import { InstrumentList } from '../_model/instrument-list';
import {InstrumentService} from "../_service/instrument.service";
import {InstrumentEditDialogComponent} from "../instrument/instrument-edit-dialog/instrument-edit-dialog.component";
import {LessonEditDialogComponent} from "./lesson-edit-dialog/lesson-edit-dialog.component";

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.scss']
})
export class LessonComponent implements AfterViewInit {
  sizeOption:number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'name', 'description', 'lessonDateStart', 'lessonDateEnd','createDate'];
  data: LessonList[];
  selection = new SelectionModel<LessonList>(false, []);

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
      width: '750px',
      data: this.selection.selected[0]?.idd
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
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
