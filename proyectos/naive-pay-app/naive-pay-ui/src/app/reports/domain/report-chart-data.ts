export interface ChartsDataDto {
  transactionsByDateChartDto: TransactionsByDateChartDto;
  transactionAmountsByDateChartDto: TransactionAmountsByDateChartDto;
  transactionsByStatusChartDto: TransactionsByStatusChartDto;
  transactionsByAmountRangeChartDto: TransactionsByAmountRangeChartDto;
  transactionsByTypeChartDto: TransactionsByTypeChartDto;
  transactionsByCommerceChartDto: TransactionsByCommerceChartDto;
}

export interface TransactionsByDateChartDto {
  dates: string[];
  counts: number[];
}

export interface TransactionAmountsByDateChartDto {
  dates: string[];
  amounts: number[];
}

export interface TransactionsByStatusChartDto {
  statuses: string[];
  counts: number[];
}

export interface TransactionsByAmountRangeChartDto {
  ranges: number[];
  frequencies: number[];
}

export interface TransactionsByTypeChartDto {
  types: string[];
  counts: number[];
}

export interface TransactionsByCommerceChartDto {
  commerces: string[];
  amounts: number[];
}
