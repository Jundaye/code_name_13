package codename13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

	// ==========================
	// 1. 댓글 목록 조회
	// ==========================
	public static List<CommentDTO> getListByBoardId(int boardId) {
		List<CommentDTO> result = new ArrayList<CommentDTO>();
		String sql = "select * from comment where board_id = ? order by comment_Id asc";
		
		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setInt(1, boardId);
			
			try (ResultSet rs = psmt.executeQuery()) {
				while (rs.next()) {
					CommentDTO comment = new CommentDTO();
					comment.setCommentId(rs.getInt("comment_id"));
					comment.setBoardId(rs.getInt("board_id"));
					comment.setCommentWriter(rs.getString("comment_writer"));
					comment.setCommentContent(rs.getString("comment_content"));
					comment.setCommentRegdate(rs.getString("comment_regdate"));
					result.add(comment);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// ==========================
	// 2. 댓글 등록
	// ==========================
	public static int insert(CommentDTO comment) {
		int result = -1;
		String sql = "insert into comment(board_id, comment_writer, comment_content) values(?, ?, ?)";
		
		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setInt(1, comment.getBoardId());
			psmt.setString(2, comment.getCommentWriter());
			psmt.setString(3, comment.getCommentContent());
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	// ==========================
	// 3. 댓글 갯수 조회
	// ==========================
	public static int getCount(int boardId) {
		int count = 0;
		String sql = "select count(*) as cnt from comment where board_id = ?";
		
		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setInt(1, boardId);
			
			try (ResultSet rs = psmt.executeQuery()) {
				if (rs.next()) {
					count = rs.getInt("cnt");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}

	// ==========================
	// 4. 댓글 삭제
	// ==========================
	public static int delete(int commentId, int boardId) {
		int result = -1;
		String sql = "delete from comment where comment_id = ? and board_id = ?";
		
		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement psmt = conn.prepareStatement(sql)) {
			
			psmt.setInt(1, commentId);
			psmt.setInt(2, boardId);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// ==========================
	// 5. 댓글 수정
	// ==========================
	public static int update(int commentId, int boardId, String content) {
		int result = -1;
		String sql = "update comment set comment_content = ? where comment_id = ? and board_id = ?";

		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement psmt = conn.prepareStatement(sql)) {

			psmt.setString(1, content);
			psmt.setInt(2, commentId);
			psmt.setInt(3, boardId);

			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}