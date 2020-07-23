import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Room} from "../../_model/room";
import {RoomService} from "../../_service/room.service";
import {SelectionModel} from "@angular/cdk/collections";
import {AddInstrumentDialogComponent} from "./add-instrument-dialog/add-instrument-dialog.component";

@Component({
  selector: 'app-room-edit-dialog',
  templateUrl: './room-edit-dialog.component.html',
  styleUrls: ['./room-edit-dialog.component.scss']
})
export class RoomEditDialogComponent implements OnInit {
  data: Room = new Room();

  instrumentsDisplayedColumns: string[] = ['select', 'idd', 'name', 'number', 'createDate'];
  /* multiple - можно ли выделить неск элементов, initiallySelectedValues - изначально помеченные */
  showInstrumentTable: boolean = false;
  selection = new SelectionModel(false, []);

  historyDisplayedColumns: string[] = ['id', 'number', 'block', 'deleteDate'];
  showHistoryTable: boolean = false;

  constructor(
    private _roomService: RoomService,
    public dialogRef: MatDialogRef<RoomEditDialogComponent>,
    /* запрашиваем idd и по нему получаем элемент с бэка*/
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}


  /* данные, которые первоначально выводятся в диалог окне */
  ngOnInit(): void {
    if (this.idd) {
      this._roomService.getRoomByIdd(this.idd)
        .pipe()
        .subscribe(room => {
          this.data = room
        });
    } else {
      this.data.instruments = [];
    }
  }

  setShowInstrumentTable() {
    this.showInstrumentTable = !this.showInstrumentTable;
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  /* при нажатии закрывает диалоговое окно */
  onCancelClick() {
    this.dialogRef.close();
  }

  /* обновляет комнату, если есть idd или сохраняет новую */
  onSaveClick() {
    /* если idd есть */
    if (this.idd) {
      this._roomService.updateRoom(this.idd, this.data)
        .toPromise()
        /* затем закрыть окно */
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._roomService.createRoom(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
  /* убираем на фронте инстурмент - он удаляется при сохранении */
  onDeleteInstrument() {
    this.data.instruments
      = this.data.instruments.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();
  }

  /* добавляем на фронте инструмент - он добавляется при сохранении */
  onAddInstrument() {
    const dialogRef = this.dialog.open(AddInstrumentDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      /* добавляет выбранный инструмент в список */
      this.data.instruments.push(result);
    });
  }
}
