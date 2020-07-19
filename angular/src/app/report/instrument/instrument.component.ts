import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {RoomList} from '../../_model/room-list';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatDialog} from '@angular/material/dialog';
import {StudentEditDialogComponent} from '../student/student-edit-dialog/student-edit-dialog.component';
import {merge, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {InstrumentService} from '../../_service/instrument.service';
import {InstrumentList} from '../../_model/instrument-list';

@Component({
  selector: 'app-instrument',
  templateUrl: './instrument.component.html',
  styleUrls: ['./instrument.component.scss']
})
export class InstrumentComponent implements AfterViewInit {
  sizeOption: number[] = [2, 5, 10];
  data: InstrumentList[];
  columns: any[] = [
    {id: 'idd', name: '#'},
    {id: 'name', name: 'Название'},
    {id: 'number', name: 'Инвентарный номер'},
  ];
  displayedColumns: string[] = this.columns.map(obj => obj.id);

  selection = new SelectionModel<RoomList>(false, []);
  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private instrumentService: InstrumentService, public dialog: MatDialog) {
  }

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
          return this.instrumentService.getInstrumentList(
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
