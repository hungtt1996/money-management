import { IGroup } from 'app/shared/model/group.model';

export interface ICategory {
  id?: string;
  code?: string;
  nameVi?: string;
  nameEn?: string;
  icon?: string;
  groupId?: string;
  group?: IGroup;
  parentId?: string | null;
  level?: number;
}

export const defaultValue: Readonly<ICategory> = {};
