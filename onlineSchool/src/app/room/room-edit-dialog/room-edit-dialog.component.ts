import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Room} from "../../_model/room";
import {RoomService} from "../../_sevice/room.service";
import {errorObject, subscribeToPromise} from "rxjs/internal-compatibility";
import {SelectionModel} from "@angular/cdk/collections";
import {RoomList} from "../../_model/room-list";
import {AddInstrumentDialogComponent} from "../add-instrument-dialog/add-instrument-dialog.component";
import {InstrumentService} from "../../_sevice/instrument.service";

@Component({
  selector: 'app-room-edit-dialog',
  templateUrl: './room-edit-dialog.component.html',
  styleUrls: ['./room-edit-dialog.component.scss']
})
export class RoomEditDialogComponent implements OnInit {
  data:Room = new Room();
  instrumentDisplayedColumns: string[] = ['select', 'idd', 'name', 'number', 'createDate'];
  historyDisplayedColumns: string[] = ['id', 'number', 'block', 'deleteDate'];
  showInstrumentTable:boolean = false;
  showHistoryTable:boolean = false;
  selection = new SelectionModel(false, []);
  constructor(
    private _roomService:RoomService,
    private _instrumentService:InstrumentService,
    public dialogRef: MatDialogRef<RoomEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number,  public dialog: MatDialog) {}

  ngOnInit(): void {
    if(this.idd){
      this._roomService.getRoomByIdd(this.idd)
        .pipe()
        .subscribe(room=>this.data = room);
    }else {
      this.data.instruments = [];
    }
  }

  setShowInstrumentTable() {
    this.showInstrumentTable = !this.showInstrumentTable;
  }
  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
      if(this.idd){
        this._roomService.updateRoom(this.idd, this.data)
          .toPromise()
          .then(res => this.dialogRef.close())
          .catch(error => console.log(error));
      } else {
        this._roomService.createRoom(this.data)
          .toPromise()
          .then(res => this.dialogRef.close())
          .catch(error => console.log(error));
      }
  }

  onDeleteInstrument() {
      // @ts-ignore
    this.data.instruments = this.data.instruments.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();
  }

  onAddInstrument() {
    const dialogRef = this.dialog.open(AddInstrumentDialogComponent, {
      width: '900px',
    });

    dialogRef.afterClosed().subscribe(result => {
      // @ts-ignore
      this.data.instruments.push(result);
    });
  }
}
