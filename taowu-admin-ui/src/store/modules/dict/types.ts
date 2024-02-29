export interface DictResponseState {
  label: string;
  value: any;
}

export interface DictListState {
  name: string;
  detail: Array<DictResponseState>;
}
