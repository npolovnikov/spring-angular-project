import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MatTable} from "@angular/material/table";
import {InstrumentList} from "../../_model/instrument-list";
import {SelectionModel} from "@angular/cdk/collections";
import {RoomService} from "../../_service/room.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {AddInstrumentDialogComponent} from "../../room/room-edit-dialog/add-instrument-dialog/add-instrument-dialog.component";
import {Student} from "../../_model/student";
import {StudentService} from "../../_service/student.service";
import {AddCourseDialogComponent} from "./add-course-dialog/add-course-dialog.component";

@Component({
  selector: 'app-student-edit-dialog',
  templateUrl: './student-edit-dialog.component.html',
  styleUrls: ['./student-edit-dialog.component.scss']
})
export class StudentEditDialogComponent implements OnInit {
  @ViewChild(MatTable) courseTable: MatTable<InstrumentList>;
  data: Student = new Student();

  coursesDisplayedColumns: string[] = ['select', 'idd', 'name', 'description', 'teacherIdd', 'maxCountStudent', 'startDate', 'endDate', 'status', 'createDate'];
  /* multiple - можно ли выделить неск элементов, initiallySelectedValues - изначально помеченные */
  showCourseTable: boolean = false;
  selection = new SelectionModel(false, []);

  historyDisplayedColumns: string[] = ['id', 'firstName', 'lastName', 'passport' , 'deleteDate'];
  showHistoryTable: boolean = false;

  constructor(
    private _studentService: StudentService,
    public dialogRef: MatDialogRef<StudentEditDialogComponent>,
    /* запрашиваем idd и по нему получаем элемент с бэка*/
    @Inject(MAT_DIALOG_DATA) public idd: number,
    public dialog: MatDialog) {}


  /* данные, которые первоначально выводятся в диалог окне */
  ngOnInit(): void {
    if (this.idd) {
      this._studentService.getStudentByIdd(this.idd)
        .pipe()
        .subscribe(student => {
          this.data = student
        });
    } else {
      this.data.courses = [];
    }
  }

  setShowCourseTable() {
    this.showCourseTable = !this.showCourseTable;
  }

  setShowHistoryTable() {
    this.showHistoryTable = !this.showHistoryTable;
  }

  /* при нажатии закрывает диалоговое окно */
  onCancelClick() {
    this.dialogRef.close();
  }

  /* обновляет студента, если есть idd или сохраняет новую */
  onSaveClick() {
    /* если idd есть */
    if (this.idd) {
      this._studentService.updateStudent(this.idd, this.data)
        .toPromise()
        /* затем закрыть окно */
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    } else {
      this._studentService.createStudent(this.data)
        .toPromise()
        .then(res => this.dialogRef.close())
        .catch(error => console.log(error));
    }
  }
  /* убираем на фронте курс - он удаляется при сохранении */
  onDeleteCourse() {
    this.data.courses
      = this.data.courses.filter(obj => obj.idd !== this.selection.selected[0].idd);
    this.selection.clear();

  }

  /* добавляем на фронте курс - он добавляется при сохранении */
  onAddCourse() {
    const dialogRef = this.dialog.open(AddCourseDialogComponent, {
      width: '750px'
    });

    dialogRef.afterClosed().subscribe(result => {
      /* добавляет выбранный курс в список */
      this.data.courses.push(result);
      // this.courseTable.renderRows();
    });
  }
}
