import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {User} from "../../_model/user";
import {UserService} from "../../_sevice/user.service";
import {errorObject, subscribeToPromise} from "rxjs/internal-compatibility";

@Component({
  selector: 'app-user-edit-dialog',
  templateUrl: './user-edit-dialog.component.html',
  styleUrls: ['./user-edit-dialog.component.scss']
})
export class UserEditDialogComponent implements OnInit {
  data:User = new User();
  historyDisplayedColumns: string[] = ['id', 'firstName', 'middleName',
    'lastName', 'passport', 'birthDate', 'deleteDate', 'status'];
  showHistoryTable:boolean = false;
  constructor(
    private _userService:UserService,
    public dialogRef: MatDialogRef<UserEditDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public idd: number) {}

  ngOnInit(): void {
    if(this.idd){
      this._userService.getUserByIdd(this.idd)
        .pipe()
        .subscribe(user=>this.data = user);
    }
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    if(this.idd){
      this._userService.updateUser(this.idd, this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._userService.createUser(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
}
