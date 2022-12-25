export interface ICategory {
  id?: string;
  code?: string;
  nameVi?: string;
  nameEn?: string;
  icon?: string;
  groupId?: string;
  parentId?: string | null;
  level?: number;
}

export const defaultValue: Readonly<ICategory> = {};
