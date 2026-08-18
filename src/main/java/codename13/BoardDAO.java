package codename13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

	// 게시글 전체 목록
	public static List<BoardDTO> getBoardList() {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try (Connection conn = DBUtil.getConnection()) {
			String sql = "select * from board " + "order by board_id desc";
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardId(rs.getInt("board_id"));
				board.setCategory(rs.getString("category"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getString("reg_date"));
				board.setLikeCount(rs.getInt("like_count"));
				board.setViewCount(rs.getInt("view_count"));
				list.add(board);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 게시글 하나 조회
	public static BoardDTO getById(int boardId) {
		BoardDTO board = null;

		try (Connection conn = DBUtil.getConnection()) {
			String sql = "select * from board " + "where board_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, boardId);
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				board = new BoardDTO();
				board.setBoardId(rs.getInt("board_id"));
				board.setCategory(rs.getString("category"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWriter(rs.getString("writer"));
				board.setRegDate(rs.getString("reg_date"));
				board.setLikeCount(rs.getInt("like_count"));
				board.setViewCount(rs.getInt("view_count"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return board;
	}

	// 게시글 등록
	public static int insertBoard(BoardDTO board) {
		int result = -1;

		try (Connection conn = DBUtil.getConnection()) {
			String sql = "insert into board" + "(category, title, content, writer) " + "values(?, ?, ?, ?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getCategory());
			psmt.setString(2, board.getTitle());
			psmt.setString(3, board.getContent());
			psmt.setString(4, board.getWriter());
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 게시글 수정
	public static int updateBoard(BoardDTO board) {
		int result = -1;

		try (Connection conn = DBUtil.getConnection()) {
			String sql = "update board " + "set category = ?, " + "title = ?, " + "content = ?, " + "writer = ? " + "where board_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, board.getCategory());
			psmt.setString(2, board.getTitle());
			psmt.setString(3, board.getContent());
			psmt.setString(4, board.getWriter());
			psmt.setInt(5, board.getBoardId());
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 게시글 삭제
	public static int deleteBoard(int boardId) {
		int result = -1;

		try (Connection conn = DBUtil.getConnection()) {
			String sql = "delete from board " + "where board_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, boardId);
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 조회수 증가
	public static int increaseView(int boardId) {
		int result = -1;

		try (Connection conn = DBUtil.getConnection()) {
			String sql = "update board " + "set view_count = view_count + 1 " + "where board_id = ?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, boardId);
			result = psmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 좋아요 증가
	public static int increaseLike(int boardId) {
		int newLikeCount = -1;

		try (Connection conn = DBUtil.getConnection()) {
			String updateSql = "update board " + "set like_count = like_count + 1 " + "where board_id = ?";
			PreparedStatement updatePsmt = conn.prepareStatement(updateSql);
			updatePsmt.setInt(1, boardId);
			updatePsmt.executeUpdate();
			
			String selectSql = "select like_count " + "from board " + "where board_id = ?";
			PreparedStatement selectPsmt = conn.prepareStatement(selectSql);
			selectPsmt.setInt(1, boardId);
			ResultSet rs = selectPsmt.executeQuery();

			if (rs.next()) {

				newLikeCount = rs.getInt("like_count");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newLikeCount;
	}
}
