export class PageParams {
  start: number;
  page: number;
  orderBy: string;
  orderDir: string;
  params;

  constructor(start: number, page: number, params, orderBy: string, orderDir: string){
    this.start = start;
    this.page = page;
    this.params = params;
    this.orderBy = orderBy;
    this.orderDir = orderDir;
  }
}
