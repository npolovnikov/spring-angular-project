import {AfterViewInit, Component, ViewChild} from '@angular/core';
import {Student} from '../../_model/student';
import {SelectionModel} from '@angular/cdk/collections';
import {RoomList} from '../../_model/room-list';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatDialog} from '@angular/material/dialog';
import {StudentEditDialogComponent} from '../student/student-edit-dialog/student-edit-dialog.component';
import {merge, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {UserService} from '../../_service/user.service';
import {UserAddDialogComponent} from './user-add-dialog/user-add-dialog.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements AfterViewInit {

  sizeOption: number[] = [2, 5, 10];
  data: Student[];
  columns: any[] = [
    {id: 'login', name: 'Логин'},
    {id: 'fio', name: 'ФИО'},
    {id: 'isActive', name: 'Активность'},
    {id: 'lastLoginDate', name: 'Авторизацция'},
    {id: 'delete', name: null},
  ];
  displayedColumns: string[] = this.columns.map(obj => obj.id);

  selection = new SelectionModel<RoomList>(false, []);
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private userService: UserService, public dialog: MatDialog) {
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();
  }

  openEditDialog() {
    const dialogRef = this.dialog.open(UserAddDialogComponent, {
      width: '750px'
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
          return this.userService.getUsersList(
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

  delete(id: number): void {
    this.userService.deleteUser(id).pipe().subscribe(
      () => this.refresh()
    );
  }
}
