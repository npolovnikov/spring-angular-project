import {BaseListEntity} from "./base-list-entity";
import {BaseHistoryEntity} from "./base-history-entity";

export abstract class BaseEntity <HistoryEntity extends BaseHistoryEntity> extends BaseListEntity{
  history:HistoryEntity[];
  getName() {
    throw new Error("Method 'getName()' must be implemented.");
  }
}
