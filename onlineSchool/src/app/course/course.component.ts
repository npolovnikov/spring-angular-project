import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {CourseService} from "../_sevice/course.service";
import {CourseEditDialogComponent} from "../course/course-edit-dialog/course-edit-dialog.component";
import {SelectionModel} from "@angular/cdk/collections";
import {CourseList} from "../_model/course-list";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.scss']
})
export class CourseComponent implements AfterViewInit {
  displayedColumns: string[] = ['select', 'idd', 'name', 'description', 'teacherId', 'maxCountOfStudent', 'startDate', 'endDate', 'createDate', 'status'];
  sizeOption:number[] = [1, 5, 10];
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  data: CourseList[];
  selection = new SelectionModel<CourseList>(false, []);


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _courseService: CourseService,  public dialog: MatDialog) {}

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
  }

  openEditDialog() {
    const dialogRef = this.dialog.open(CourseEditDialogComponent, {
      width: '900px',
      data: this.selection.selected[0]?.idd
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
    });
  }

  refresh(){
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
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }
}
