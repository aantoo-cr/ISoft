import { UserStructure } from '../../shared/domain/UserStructure';
import { ForumReplyStructure } from './ForumReplyStructure';

export class ForumStructure {
  id: number;
  title: string;
  description: string;
  user: UserStructure;
  replies: ForumReplyStructure[];
  createdAt: string;
  updatedAt?: string;
  slug: string;

  constructor(
    id: number,
    title: string,
    description: string,
    user: UserStructure,
    replies: ForumReplyStructure[],
    createdAt: string,
    slug: string,
    updatedAt?: string
  ) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.user = user;
    this.replies = replies.map(reply =>
      new ForumReplyStructure(
        reply.id,
        reply.content,
        reply.user,
        reply.createdAt,
        reply.updatedAt,
        reply.subReplies
      )
    );
    this.createdAt = createdAt;
    this.slug = slug;
    this.updatedAt = updatedAt;
  }
}
