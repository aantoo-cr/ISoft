import {State} from "./state";
import {AccountHolder} from "./account-holder";
import {BankAccount} from "./bank-account";

export interface Transaction {
    token: string;
    state: State;
    amount: number;
    date: [number, number, number];
    time: [number, number, number];
    description: string;
    originAccount: BankAccount;
    destinationAccount: BankAccount;
}
