export class Post {
    postId: number;
    subRedditName: String;
    postName: String;
    url: String;
    description: String;
    username: String;
    voteCount: number;
    commentCount: number;
    upVote: boolean = false;
    downVote: boolean = false;
    duration: String;
}
