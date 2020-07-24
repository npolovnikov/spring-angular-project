import {InstrumentList} from "./instrument-list";
import {Room} from "./room";
import {Course} from "./course";
import {CourseList} from "./course-list";
import {RoomList} from "./room-list";

export class Lesson {
  idd: number;
  name: string;
  description: string;
  course: CourseList;
  room: RoomList;
  lessonDateStart: string;
  lessonDateEnd: string;
  createDate: string;
  instruments: InstrumentList[];
  history:[];
}
