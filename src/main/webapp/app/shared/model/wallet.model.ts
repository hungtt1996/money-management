export interface IWallet {
  id?: string;
  code?: string;
  nameVi?: string;
  nameEn?: string;
  balance?: number;
  ccy?: string;
  icon?: string;
}

export const defaultValue: Readonly<IWallet> = {};
