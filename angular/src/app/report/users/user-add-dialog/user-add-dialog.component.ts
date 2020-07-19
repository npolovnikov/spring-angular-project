import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTable} from '@angular/material/table';
import {Course} from '../../../_model/course';
import {SelectionModel} from '@angular/cdk/collections';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {UserService} from '../../../_service/user.service';
import {User} from '../../../_model/user';

@Component({
  selector: 'app-user-add-dialog',
  templateUrl: './user-add-dialog.component.html',
  styleUrls: ['./user-add-dialog.component.scss']
})
export class UserAddDialogComponent implements OnInit {

  @ViewChild(MatTable) instrumentTable: MatTable<Course>;

  data: User = new User();

  selection = new SelectionModel(false, []);

  constructor(
    private userService: UserService,
    public dialogRef: MatDialogRef<UserAddDialogComponent>,
    public dialog: MatDialog) {
  }


  ngOnInit(): void {
  }

  onCancelClick() {
    this.dialogRef.close();
  }

  onSaveClick() {
    this.userService.createUser(this.data)
      .toPromise()
      .then(res => this.dialogRef.close())
      .catch(error => console.log(error));
  }
}
