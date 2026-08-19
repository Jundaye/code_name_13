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
import jakarta.servlet.http.HttpSession;

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
		// 1. 메인 화면 (핫 게시글 & 노래 로딩)
		// ==========================
		case "/main.do": {
			List<BoardDTO> hotBoardList = BoardDAO.getHotBoardList();
			req.setAttribute("hotBoardList", hotBoardList);

			// 💡 메모리가 아닌 DB(SongDAO)에서 오늘의 노래를 꺼내옵니다!
			String todaySong = SongDAO.getTodayVideoId();
			req.setAttribute("todaySong", todaySong);

			page = "index.jsp";
			break;
		}

		// ==========================
		// 2. 게시글 목록
		// ==========================
		case "/list.do": {
			String category = req.getParameter("category");
			List<BoardDTO> boardList = BoardDAO.getBoardList(category);
			req.setAttribute("boardList", boardList);
			page = "boardList.jsp";
			break;
		}

		// ==========================
		// 3. 글쓰기 화면
		// ==========================
		case "/writeForm.do": {
			page = "boardWrite.jsp";
			break;
		}

		// ==========================
		// 4. 글쓰기 처리
		// ==========================
		case "/write.do": {
			String category = req.getParameter("category");
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			String writer = req.getParameter("writer");

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
			resp.sendRedirect(req.getContextPath() + "/list.do?category=" + category);
			return;
		}

		// ==========================
		// 5. 상세보기
		// ==========================
		case "/detail.do": {
			// 🚨 로그인 체크 추가: 비로그인 상태면 알럿 띄우고 뒤로 가기
			HttpSession session = req.getSession();
			if (session.getAttribute("loginUser") == null) {
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('로그인 후 글 확인이 가능합니다.'); history.back();</script>");
				out.flush();
				return;
			}

			int boardId = Integer.parseInt(req.getParameter("boardId"));

			BoardDAO.increaseView(boardId);
			BoardDTO board = BoardDAO.getById(boardId);
			List<CommentDTO> comments = CommentDAO.getListByBoardId(boardId);

			req.setAttribute("board", board);
			req.setAttribute("comments", comments);
			page = "boardDetail.jsp";
			break;
		}

		// ==========================
		// 6. 수정 화면
		// ==========================
		case "/updateForm.do": {
			int boardId = Integer.parseInt(req.getParameter("boardId"));
			BoardDTO board = BoardDAO.getById(boardId);

			HttpSession session = req.getSession();
			UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

			// 로그인하지 않았거나 작성자가 아닌 경우 알럿 출력 후 이전 페이지로 이동
			if (loginUser == null || !loginUser.getUserName().equals(board.getWriter())) {
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('작성자가 아니면 수정/삭제할 수 없습니다!'); history.back();</script>");
				out.flush();
				return;
			}

			req.setAttribute("board", board);
			page = "boardUpdate.jsp";
			break;
		}

		// ==========================
		// 7. 수정 처리
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

			if (category == null || category.trim().isEmpty() || title == null || title.trim().isEmpty()
					|| content == null || content.trim().isEmpty() || writer == null || writer.trim().isEmpty()) {
				req.setAttribute("board", board);
				req.setAttribute("message", "빈칸을 모두 입력해주세요.");
				page = "boardUpdate.jsp";
				break;
			}

			BoardDAO.updateBoard(board);
			resp.sendRedirect(req.getContextPath() + "/list.do?category=" + category);
			return;
		}

		// ==========================
		// 8. 게시글 삭제
		// ==========================
		case "/delete.do": {
			int boardId = Integer.parseInt(req.getParameter("boardId"));
			BoardDTO board = BoardDAO.getById(boardId);

			HttpSession session = req.getSession();
			UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

			// 로그인하지 않았거나 작성자가 아닌 경우 알럿 출력 후 이전 페이지로 이동
			if (loginUser == null || !loginUser.getUserName().equals(board.getWriter())) {
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('작성자가 아니면 수정/삭제할 수 없습니다!'); history.back();</script>");
				out.flush();
				return;
			}

			BoardDAO.deleteBoard(boardId);
			resp.sendRedirect(req.getContextPath() + "/list.do");
			return;
		}

		// ==========================
		// 9. 좋아요 처리
		// ==========================
		case "/like.do": {
			resp.setContentType("application/json;charset=UTF-8");
			HttpSession session = req.getSession();
			UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
			
			if (loginUser == null) {
				resp.getWriter().print("{\"result\":\"guest\"}");
				return;
			}
			
			String userId = loginUser.getUserId();
			int boardId = Integer.parseInt(req.getParameter("boardId"));
			
			boolean isLiked = BoardDAO.checkLike(boardId, userId);
			if (isLiked) {
				resp.getWriter().print("{\"result\":\"duplicate\"}");
				return;
			}
			
			BoardDAO.insertLike(boardId, userId);
			int newCount = BoardDAO.increaseLike(boardId); 
			
			resp.getWriter().print("{\"result\":\"success\", \"likeCount\":" + newCount + "}");
			return;
		}
		
		// ==========================
		// 10. 오늘의 노래 조회
		// ==========================
		case "/getTodaySong.do": {
			resp.setContentType("text/plain;charset=UTF-8");

			// 💡 DB(SongDAO)에서 노래를 확인합니다.
			String todaySong = SongDAO.getTodayVideoId();

			if (todaySong == null) {
				resp.getWriter().print("NONE");
			} else {
				resp.getWriter().print(todaySong);
			}
			return;
		}

		// ==========================
		// 11. 오늘의 노래 등록 (DB에 저장!)
		// ==========================
		case "/songRegister.do": {
			resp.setContentType("text/plain;charset=UTF-8");

			HttpSession session = req.getSession();
			UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

			if (loginUser == null) {
				resp.getWriter().print("GUEST");
				return;
			}

			String videoId = req.getParameter("videoId");

			// 💡 DB(SongDAO)에 오늘 등록된 노래가 있는지 개수를 물어봅니다.
			int todaySongCount = SongDAO.getTodaySongCount();

			if (todaySongCount == 0) {
				// 💡 아무도 등록하지 않았다면 DTO에 담아서 DB에 INSERT!
				SongDTO song = new SongDTO();
				song.setVideoId(videoId);
				song.setUserId(loginUser.getUserId());

				SongDAO.insertSong(song);
				resp.getWriter().print("SUCCESS");
			} else {
				// 누군가 간발의 차이로 먼저 DB에 등록함
				resp.getWriter().print("ALREADY_EXIST");
			}
			return;
		}

		// ==========================
		// 12. 댓글 등록
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
		// 13. 댓글 삭제
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

		// ==========================
		// 14. 댓글 수정 
		// ==========================
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
		
		// ==========================
		// 15. 로그인 처리
		// ==========================
		case "/login.do": {
			String userId = req.getParameter("userId");
			String userPw = req.getParameter("userPw");
			
			UserDTO user = UserDAO.login(userId, userPw);
			
			if (user != null) { 
				HttpSession session = req.getSession();
				session.setAttribute("loginUser", user);
				resp.sendRedirect(req.getContextPath() + "/main.do");
			} else { 
				req.setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
				req.getRequestDispatcher("/main.do").forward(req, resp);
			}
			return;
		}

		// ==========================
		// 16. 로그아웃 처리
		// ==========================
		case "/logout.do": {
			HttpSession session = req.getSession();
			session.invalidate(); 
			resp.sendRedirect(req.getContextPath() + "/main.do");
			return;
		}

		// ==========================
		// 17. 회원가입 폼 이동
		// ==========================
		case "/joinForm.do": {
			req.getRequestDispatcher("/join.jsp").forward(req, resp);
			return;
		}

		// ==========================
		// 18. 회원가입 처리
		// ==========================
		case "/join.do": {
			UserDTO user = new UserDTO();
			user.setUserId(req.getParameter("userId"));
			user.setUserPw(req.getParameter("userPw"));
			user.setUserName(req.getParameter("userName"));
			
			boolean isSuccess = UserDAO.join(user);
			
			if (isSuccess) {
				resp.sendRedirect(req.getContextPath() + "/main.do");
			} else {
				req.setAttribute("message", "회원가입에 실패했습니다. 아이디 중복을 확인해주세요.");
				req.getRequestDispatcher("/join.jsp").forward(req, resp);
			}
			return;
		}

		// ==========================
		// 19. 기본 처리 (목록)
		// ==========================
		default: {
			String category = req.getParameter("category");
			List<BoardDTO> list = BoardDAO.getBoardList(category);
			req.setAttribute("boardList", list);
			page = "boardList.jsp";
			break;
		}
		}

		RequestDispatcher rd = req.getRequestDispatcher(page);
		rd.forward(req, resp);
	}

	// ==============================
	// 공통: JSON 출력
	// ==============================
	private void sendJson(HttpServletResponse resp, String json) throws IOException {
		resp.setContentType("application/json; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(json);
		out.flush();
	}

	// ==============================
	// 공통: 댓글 → JSON 변환
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
	// 공통: JSON 특수문자 이스케이프
	// ==============================
	private String escape(String value) {
		if (value == null) {
			return "";
		}
		return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
	}
}