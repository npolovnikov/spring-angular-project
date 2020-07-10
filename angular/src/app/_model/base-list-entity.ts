export abstract class BaseListEntity {
  idd:number;
  createDate:string;
  getName() {
    throw new Error("Method 'getName()' must be implemented.");
  }
}
