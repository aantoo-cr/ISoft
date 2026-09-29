import {State} from "./state";
import {BankAccount} from "./bank-account";
import {Time} from "@angular/common";

export interface Transaction {
    token: string;
    state: State;
    amount: number;
    date: Date;
    time: Time;
    description: string;
    originAccount: BankAccount;
    destinationAccount: BankAccount;
}
