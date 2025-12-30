import {Account} from './account';

export interface Operation{
  id:number,
  date:string,
  amount:number,
  type:string,
  account:Account,
  description:string,
}
