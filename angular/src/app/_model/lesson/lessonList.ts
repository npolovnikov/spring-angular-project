import {BaseListEntity} from "../base/base-list-entity";
import {Room} from "../room/room";

export class LessonList extends BaseListEntity{
  name:string;
  description:string;
  room:Room;
  lessonDateStart:string;
  lessonDateEnd:string;
}
