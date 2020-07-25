import {Component, ViewChild, AfterViewInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, Observable, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {RoomList} from "../_model/room-list";
import {RoomService} from "../_service/room.service";
import {RoomEditDialogComponent} from "./room-edit-dialog/room-edit-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {SelectionModel} from "@angular/cdk/collections";
import {AuthService} from "../_service/auth.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent implements AfterViewInit {
  /* добавили переменную sizeOption выбора кол-ва элементов на стр-це */
  sizeOption: number[] = [2, 5, 10];
  /* изменили колонки на наши */
  displayedColumns: string[] = ['select', 'idd', 'number', 'block', 'createDate'];
  /* модель RoomListDto (room-list.ts) */
  data: RoomList[];
  /* multiple - можно ли выделить неск элементов, initiallySelectedValues - изначально помеченные */
  selection = new SelectionModel<RoomList>(false, []);

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  /* добавили в конструктор roomService */

  /* dialog: MatDialog, из которого вызываем openDialog() */
  constructor(private _roomService: RoomService,
              public dialog: MatDialog,
              private _authService: AuthService,
              private router: Router) {
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.refresh();
  }

  /* при щелчке на кнопку открывает окно диалога RoomEditDialogComponent*/
  openEditDialog(): void {
    /*  открытие окна */
    const dialogRef = this.dialog.open(RoomEditDialogComponent, {
      width: '750px',

      /* selected[0] - получаем выбранный в чекбоксе рум дто */
      /* ? - проверка на null, если this.selection.selected[0]= null, то дальше не пойдет и вернет null */
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
          return this._roomService.getRoomList(
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

  ondDeleteObject(): void {
    /* REP-IR!*/
    const deleteIdd: number = this.selection.selected[0]?.idd;
    if (deleteIdd == null) {
      return;
    }
    this._roomService.deleteObjectByIdd(deleteIdd);
    this.selection.clear();
    this.refresh();
  }

  logOut(): void {
    this._authService.logout()
      .pipe()
      .subscribe(res => {
        this.router.navigateByUrl('/login');
      });
  }
}

