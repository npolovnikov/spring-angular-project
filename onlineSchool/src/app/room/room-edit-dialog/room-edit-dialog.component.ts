import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Room} from "../../_model/room";
import {RoomService} from "../../_sevice/room.service";
import {errorObject, subscribeToPromise} from "rxjs/internal-compatibility";

@Component({
  selector: 'app-room-edit-dialog',
  templateUrl: './room-edit-dialog.component.html',
  styleUrls: ['./room-edit-dialog.component.scss']
})
export class RoomEditDialogComponent implements OnInit {
  data:Room = new Room();
  instrumentDisplayedColumns: string[] = ['idd', 'name', 'number', 'createDate'];
  historyDisplayedColumns: string[] = ['id', 'number', 'block', 'deleteDate'];
  showInstrumentTable:boolean = false;
  showHistoryTable:boolean = false;
  constructor(
    private _roomService:RoomService,
    public dialogRef: MatDialogRef<RoomEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number) {}

  ngOnInit(): void {
    if(this.idd){
      this._roomService.getRoomByIdd(this.idd)
        .pipe()
        .subscribe(room=>this.data = room);
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
}
