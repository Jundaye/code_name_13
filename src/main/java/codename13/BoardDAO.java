package codename13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

	// 게시글 전체 목록
	public static List<BoardDTO> getBoardList(String category) {
	    List<BoardDTO> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    // 1. 기본 쿼리: 전체 조회 (최신 글이 위로 오도록 ORDER BY 적용)
	    String sql = "SELECT * FROM board ORDER BY board_id DESC";

	    // 2. 카테고리 값이 null이 아니고, 비어있지 않다면? 쿼리에 WHERE 절을 덧붙입니다.
	    if (category != null && !category.trim().isEmpty()) {
	        sql = "SELECT * FROM board WHERE category = ? ORDER BY board_id DESC";
	    }

	    try {
	        conn = DBUtil.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        // 3. WHERE 절이 추가되었으므로, 물음표(?) 자리에 카테고리 값을 세팅합니다.
	        if (category != null && !category.trim().isEmpty()) {
	            pstmt.setString(1, category);
	        }

	        rs = pstmt.executeQuery();

	        // 4. 결과를 DTO에 담는 로직 (기존 코드와 동일하게 유지)
	        while (rs.next()) {
	            BoardDTO board = new BoardDTO();
	            board.setBoardId(rs.getInt("board_id"));
	            board.setCategory(rs.getString("category"));
	            board.setTitle(rs.getString("title"));
	            board.setWriter(rs.getString("writer"));
	            board.setRegDate(rs.getString("reg_date"));
	            board.setViewCount(rs.getInt("view_count"));
	            board.setLikeCount(rs.getInt("like_count"));
	            
	            list.add(board);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // DBUtil.close(rs, pstmt, conn); // 사용하시는 DB 종료 메서드
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
	
	// 특정 유저가 이미 좋아요를 눌렀는지 확인 (누른 적 있으면 true 반환)
		public static boolean checkLike(int boardId, String userId) {
			boolean isLiked = false;
			try (Connection conn = DBUtil.getConnection()) {
				String sql = "SELECT COUNT(*) FROM board_like WHERE board_id = ? AND user_id = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, boardId);
				psmt.setString(2, userId);
				ResultSet rs = psmt.executeQuery();
				
				if (rs.next() && rs.getInt(1) > 0) {
					isLiked = true; // 이미 누른 기록이 존재함
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return isLiked;
		}

		// 좋아요 기록을 board_like 테이블에 추가
		public static void insertLike(int boardId, String userId) {
			try (Connection conn = DBUtil.getConnection()) {
				String sql = "INSERT INTO board_like (board_id, user_id) VALUES (?, ?)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, boardId);
				psmt.setString(2, userId);
				psmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// ==========================================
		// 핫 게시글 (좋아요 3개 이상) 5개 가져오기
		// ==========================================
		public static List<BoardDTO> getHotBoardList() {
			List<BoardDTO> list = new ArrayList<>();

			// 쿼리: 좋아요 3개 이상인 글을 좋아요순 -> 최신순으로 5개만 가져옴
			String sql = "SELECT * FROM board WHERE like_count >= 3 ORDER BY like_count DESC, board_id DESC LIMIT 2";

			try (Connection conn = DBUtil.getConnection();
				 PreparedStatement psmt = conn.prepareStatement(sql);
				 ResultSet rs = psmt.executeQuery()) {

				while (rs.next()) {
					BoardDTO board = new BoardDTO();
					board.setBoardId(rs.getInt("board_id"));
					board.setCategory(rs.getString("category"));
					board.setTitle(rs.getString("title"));
					board.setContent(rs.getString("content"));
					board.setWriter(rs.getString("writer"));
					board.setRegDate(rs.getString("reg_date"));
					board.setViewCount(rs.getInt("view_count"));
					board.setLikeCount(rs.getInt("like_count"));
					
					list.add(board);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return list;
		}
}
