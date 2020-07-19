import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {StudentService} from '../../_service/student.service';
import {Student} from '../../_model/student';
import {MatDialog} from '@angular/material/dialog';
import {SelectionModel} from '@angular/cdk/collections';
import {RoomList} from '../../_model/room-list';
import {StudentEditDialogComponent} from './student-edit-dialog/student-edit-dialog.component';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  data: Student[];
  columns: any[] = [
    {id: 'select', name: null},
    {id: 'idd', name: '#'},
    {id: 'lastName', name: 'Фамилия'},
    {id: 'firstName', name: 'Имя'},
    {id: 'middleName', name: 'Отчество'},
    {id: 'passport', name: 'Паспорт'},
    {id: 'birthDate', name: 'Дата рождения'},
  ];
  displayedColumns: string[] = this.columns.map(obj => obj.id);

  selection = new SelectionModel<RoomList>(false, []);
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private studentService: StudentService, public dialog: MatDialog) {}

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();
  }

  openEditDialog() {
    const dialogRef = this.dialog.open(StudentEditDialogComponent, {
      width: '750px',
      data: this.selection.selected[0]?.idd
    });

    dialogRef.afterClosed().subscribe(result => {
      this.refresh();
      this.selection.clear();
    });
  }

  refresh() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.studentService.getStudentList(
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
