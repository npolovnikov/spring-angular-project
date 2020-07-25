import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Course} from "../_model/course";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {CourseService} from "../_service/course.service";
import {merge, of as observableOf} from "rxjs";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {Student} from "../_model/student";
import {StudentService} from "../_service/student.service";
import {SelectionModel} from "@angular/cdk/collections";
import {Room} from "../_model/room";
import {RoomService} from "../_service/room.service";
import {MatDialog} from "@angular/material/dialog";
import {RoomEditDialogComponent} from "../room/room-edit-dialog/room-edit-dialog.component";
import {StudentEditDialogComponent} from "./student-edit-dialog/student-edit-dialog.component";

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements AfterViewInit {
  sizeOption: number[] = [2, 5, 10];
  displayedColumns: string[] = ['select', 'idd', 'firstName','middleName',
    'lastName', 'passport', 'status', 'birthDate', 'createDate'];
  data: Student[];
  selection = new SelectionModel<Student>(false, []);
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _studentService: StudentService, public dialog: MatDialog) {}

  ngAfterViewInit() {

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();

  }

  openEditDialog() {
    const dialogRef = this.dialog.open(StudentEditDialogComponent, {
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
    this._studentService.deleteStudent(this.selection.selected[0].idd);

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
