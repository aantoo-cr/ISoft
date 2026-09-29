import {AccountHolder} from "./account-holder";

export interface BankAccount {
    accountNumber: string;
    balance: number;
    rutAccountHolder: AccountHolder;
}
