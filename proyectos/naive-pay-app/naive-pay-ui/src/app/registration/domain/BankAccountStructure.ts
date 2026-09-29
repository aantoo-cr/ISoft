export class BankAccount {
  id: number;
  accountNumber: string;
  balance: number;

  constructor(
    id: number,
    accountNumber: string,
    balance: number
  ) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.balance = balance;
  }
}
