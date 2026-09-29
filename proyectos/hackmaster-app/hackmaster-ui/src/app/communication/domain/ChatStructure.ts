import { UserStructure } from '../../shared/domain/UserStructure';
import { MessageStructure } from './MessageStructure';


export class ChatStructure {
  id: number;
  name: string;
  avatar: string;
  users: UserStructure[];
  messages: MessageStructure[]

  constructor(
    id: number,
    name: string,
    avatar: string, // Añadir avatar
    users: UserStructure[],
    messages: MessageStructure[]
  ) {
    this.id = id;
    this.name = name;
    this.avatar = avatar;
    this.users = users;
    this.messages = messages;
  }
}
