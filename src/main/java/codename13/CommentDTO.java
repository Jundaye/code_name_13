package codename13;

public class CommentDTO {
	int commentId;
	int boardId;
	String commentWriter;
	String commentContent;
	String commentRegdate;

	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getCommentWriter() {
		return commentWriter;
	}
	public void setCommentWriter(String commentWriter) {
		this.commentWriter = commentWriter;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentRegdate() {
		return commentRegdate;
	}
	public void setCommentRegdate(String commentRegdate) {
		this.commentRegdate = commentRegdate;
	}

	@Override
	public String toString() {
		return "CommentDTO [commentId=" + commentId + ", boardId=" + boardId + ", commentWriter=" + commentWriter
				+ ", commentContent=" + commentContent + ", commentRegdate=" + commentRegdate + "]";
	}
}


