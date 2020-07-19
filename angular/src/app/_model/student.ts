import {Course} from './course';

export class Student {
  idd: number;
  firstName: string;
  lastName: string;
  middleName: string;
  passport: string;
  birthDate: string;
  history: [];
  courses: Course[];
}
