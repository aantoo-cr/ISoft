import { UserStructure } from '../../shared/domain/UserStructure';

export class ForumReplyStructure {
  id: number;
  content: string;
  user: UserStructure;
  createdAt: string;
  updatedAt?: string;
  subReplies: ForumReplyStructure[];

  constructor(
    id: number,
    content: string,
    user: UserStructure,
    createdAt: string,
    updatedAt?: string,
    subReplies: ForumReplyStructure[] = []
  ) {
    this.id = id;
    this.content = content;
    this.user = user;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.subReplies = subReplies;
  }
}
