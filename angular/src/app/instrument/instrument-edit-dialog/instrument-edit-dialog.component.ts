import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTable} from "@angular/material/table";
import {SelectionModel} from "@angular/cdk/collections";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {RoomList} from "../../_model/room-list";
import {InstrumentService} from "../../_service/instrument.service";
import {Instrument} from "../../_model/instrument";

@Component({
  selector: 'app-instrument-edit-dialog',
  templateUrl: './instrument-edit-dialog.component.html',
  styleUrls: ['./instrument-edit-dialog.component.scss']
})
export class InstrumentEditDialogComponent implements OnInit {
  @ViewChild(MatTable) roomTable: MatTable<RoomList>;
  data: Instrument = new Instrument();

  roomsDisplayedColumns: string[] = [ 'idd', 'number', 'block']; //добавить 'select', если сделаю добавление и удаление комнаты
  /* multiple - можно ли выделить неск элементов, initiallySelectedValues - изначально помеченные */
  showRoomTable: boolean = false;
  selection = new SelectionModel(false, []);

  historyDisplayedColumns: string[] = ['id', 'name', 'number', 'userId', 'deleteDate'];
  showHistoryTable: boolean = false;

  constructor(
    private _instrumentService: InstrumentService,
    public dialogRef: MatDialogRef<InstrumentEditDialogComponent>,
    /* запрашиваем idd и по нему получаем элемент с бэка*/
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {
  }


  /* данные, которые первоначально выводятся в диалог окне */
  ngOnInit(): void {
    if (this.idd) {
      this._instrumentService.getInstrumentByIdd(this.idd)
        .pipe()
        .subscribe(instrument => {
          this.data = instrument
        });
    } else {
      this.data.rooms = [];
    }
  }

  setShowRoomTable() {
    this.showRoomTable = !this.showRoomTable;
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  /* при нажатии закрывает диалоговое окно */
  onCancelClick() {
    this.dialogRef.close();
  }

  /* обновляет instrument, если есть idd или сохраняет новую */
  onSaveClick() {
    /* если idd есть */
    if (this.idd) {
      this._instrumentService.updateInstrument(this.idd, this.data)
        .toPromise()
        /* затем закрыть окно */
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._instrumentService.createInstrument(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
}
