import { UserStructure } from '../../shared/domain/UserStructure';

export class MessageStructure {
  id: number;
  content: string;
  timestamp: Date;
  sender: UserStructure;

  constructor(
    id: number,
    content: string,
    timestamp: string,
    sender: UserStructure
  ) {
    this.id = id;
    this.content = content;
    this.timestamp = new Date(timestamp);
    this.sender = sender;
  }
}
