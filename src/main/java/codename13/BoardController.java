package codename13;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class BoardController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String page = "boardList.jsp";

		String uri = req.getRequestURI();

		String requestUri = uri.substring(uri.lastIndexOf("/"), uri.length());

		System.out.println("requestUri=" + requestUri);

		switch (requestUri) {

		// ==========================
		// 게시글 목록
		// ==========================
		case "/list.do": {

			List<BoardDTO> list = BoardDAO.getBoardList();

			req.setAttribute("boardList", list);

			page = "boardList.jsp";

			break;
		}

		// ==========================
		// 글쓰기 화면
		// ==========================
		case "/writeForm.do": {

			page = "boardWrite.jsp";

			break;
		}

		// ==========================
		// 글쓰기 처리
		// ==========================
		case "/write.do": {

			String category = req.getParameter("category");

			String title = req.getParameter("title");

			String content = req.getParameter("content");

			String writer = req.getParameter("writer");

			// 빈칸 검사
			if (category == null || category.trim().isEmpty() || title == null || title.trim().isEmpty()
					|| content == null || content.trim().isEmpty() || writer == null || writer.trim().isEmpty()) {

				req.setAttribute("message", "빈칸을 모두 입력해주세요.");

				page = "boardWrite.jsp";

				break;
			}

			BoardDTO board = new BoardDTO();

			board.setCategory(category);

			board.setTitle(title);

			board.setContent(content);

			board.setWriter(writer);

			BoardDAO.insertBoard(board);

			resp.sendRedirect(req.getContextPath() + "/list.do");

			return;
		}

		// ==========================
		// 상세보기
		// ==========================
		case "/detail.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			// 조회수 증가
			BoardDAO.increaseView(boardId);

			// 게시글 가져오기
			BoardDTO board = BoardDAO.getById(boardId);

			// 댓글 가져오기
			List<CommentDTO> comments = CommentDAO.getListByBoardId(boardId);

			req.setAttribute("board", board);

			req.setAttribute("comments", comments);

			page = "boardDetail.jsp";

			break;
		}

		// ==========================
		// 수정 화면
		// ==========================
		case "/updateForm.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			BoardDTO board = BoardDAO.getById(boardId);

			req.setAttribute("board", board);

			page = "boardUpdate.jsp";

			break;
		}

		// ==========================
		// 수정 처리
		// ==========================
		case "/update.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			String category = req.getParameter("category");

			String title = req.getParameter("title");

			String content = req.getParameter("content");

			String writer = req.getParameter("writer");

			BoardDTO board = new BoardDTO();

			board.setBoardId(boardId);

			board.setCategory(category);

			board.setTitle(title);

			board.setContent(content);

			board.setWriter(writer);

			// 빈칸 검사
			if (category == null || category.trim().isEmpty() || title == null || title.trim().isEmpty()
					|| content == null || content.trim().isEmpty() || writer == null || writer.trim().isEmpty()) {

				req.setAttribute("board", board);

				req.setAttribute("message", "빈칸을 모두 입력해주세요.");

				page = "boardUpdate.jsp";

				break;
			}

			BoardDAO.updateBoard(board);

			resp.sendRedirect(req.getContextPath() + "/list.do");

			return;
		}

		// ==========================
		// 게시글 삭제
		// ==========================
		case "/delete.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			BoardDAO.deleteBoard(boardId);

			resp.sendRedirect(req.getContextPath() + "/list.do");

			return;
		}

		// ==========================
		// 좋아요
		// ==========================
		case "/like.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			int newLikeCount = BoardDAO.increaseLike(boardId);

			String json = "{\"result\":\"" + (newLikeCount >= 0 ? "success" : "fail") + "\",\"likeCount\":"
					+ newLikeCount + "}";

			sendJson(resp, json);

			return;
		}

		// ==========================
		// 댓글 등록
		// ==========================
		case "/commentInsert.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			String writer = req.getParameter("commentWriter");

			String content = req.getParameter("commentContent");

			CommentDTO comment = new CommentDTO();

			comment.setBoardId(boardId);

			comment.setCommentWriter(writer);

			comment.setCommentContent(content);

			CommentDAO.insert(comment);

			List<CommentDTO> comments = CommentDAO.getListByBoardId(boardId);

			String json = commentsToJson(comments);

			sendJson(resp, json);

			return;
		}

		// ==========================
		// 댓글 삭제
		// ==========================
		case "/commentDelete.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			int commentId = Integer.parseInt(req.getParameter("commentId"));

			CommentDAO.delete(commentId, boardId);

			List<CommentDTO> comments = CommentDAO.getListByBoardId(boardId);

			String json = commentsToJson(comments);

			sendJson(resp, json);

			return;
		}

		// 댓글 수정 코드

		case "/commentUpdate.do": {

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			int commentId = Integer.parseInt(req.getParameter("commentId"));

			String content = req.getParameter("content");

			CommentDAO.update(commentId, boardId, content);

			List<CommentDTO> comments = CommentDAO.getListByBoardId(boardId);

			String json = commentsToJson(comments);

			sendJson(resp, json);

			return;
		}

		default: {

			List<BoardDTO> list = BoardDAO.getBoardList();

			req.setAttribute("boardList", list);

			page = "boardList.jsp";

			break;
		}

		}

		RequestDispatcher rd = req.getRequestDispatcher(page);

		rd.forward(req, resp);
	}

	// ==============================
	// JSON 출력
	// ==============================
	private void sendJson(HttpServletResponse resp, String json) throws IOException {

		resp.setContentType("application/json; charset=UTF-8");

		PrintWriter out = resp.getWriter();

		out.print(json);
		out.flush();
	}

	// ==============================
	// 댓글 → JSON
	// ==============================
	private String commentsToJson(List<CommentDTO> comments) {

		StringBuilder sb = new StringBuilder();

		sb.append("{\"comments\":[");

		for (int i = 0; i < comments.size(); i++) {

			CommentDTO c = comments.get(i);

			if (i > 0) {
				sb.append(",");
			}

			sb.append("{");

			sb.append("\"commentId\":").append(c.getCommentId()).append(",");

			sb.append("\"commentWriter\":\"").append(escape(c.getCommentWriter())).append("\",");

			sb.append("\"commentContent\":\"").append(escape(c.getCommentContent())).append("\",");

			sb.append("\"commentRegdate\":\"").append(escape(c.getCommentRegdate())).append("\"");

			sb.append("}");
		}

		sb.append("]}");

		return sb.toString();
	}

	// ==============================
	// JSON 특수문자 처리
	// ==============================
	private String escape(String value) {

		if (value == null) {
			return "";
		}

		return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
	}
}
