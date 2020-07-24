import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {StudentList} from "../_model/student-list";
import {StudentService} from "../_sevice/student.service";
import {SelectionModel} from "@angular/cdk/collections";
import {StudentEditDialogComponent} from "../student/student-edit-dialog/student-edit-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements AfterViewInit {
  displayedColumns: string[] = ['select', 'idd', 'firstName', 'middleName', 'lastName', 'passport', 'status', 'birthDate', 'createDate'];
  sizeOption:number[] = [1, 5, 10];
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;
  data: StudentList[];
  selection = new SelectionModel<StudentList>(false, []);


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _studentService: StudentService,   public dialog: MatDialog) {}

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
  }

  openEditDialog() {
    const dialogRef = this.dialog.open(StudentEditDialogComponent, {
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
          return this._studentService.getStudentList(
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
