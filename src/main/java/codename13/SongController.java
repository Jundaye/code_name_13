package codename13;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 두 개의 주소를 이 컨트롤러 하나가 모두 처리하도록 설정합니다.
@WebServlet({"/songRegister.do", "/getTodaySong.do"})
public class SongController extends HttpServlet {
	
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 한글 깨짐 방지 및 Ajax 통신을 위해 응답 형식을 일반 텍스트로 설정
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");
        
        String uri = req.getRequestURI();
        String requestUri = uri.substring(uri.lastIndexOf("/"));
        PrintWriter out = resp.getWriter();

        // ========================================================
        // [1] 선착순 노래 등록 로직 (사용자가 '실행' 버튼을 눌렀을 때)
        // ========================================================
        if ("/songRegister.do".equals(requestUri)) {
            
            String videoId = req.getParameter("videoId");

            // 1. 현재 접속 중인(로그인한) 사람의 아이디를 세션에서 꺼내옵니다.
            // HttpSession session = req.getSession();
            // UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
            // String userId = loginUser.getUserId();
            String userId = "tester"; // (나중에 로그인 기능 연동 시 위 코드로 교체)

            // 2. [핵심] DAO에게 "오늘 날짜로 등록된 노래가 몇 개야?" 라고 물어봅니다.
            // int todaySongCount = SongDAO.getTodaySongCount();
            int todaySongCount = 0; // 임시 데이터 (나중에 DAO 연결)

            // 3. 선착순 검사 분기 처리
            if (todaySongCount > 0) {
                // 이미 누군가 오늘치 노래를 등록했다면 거절 메시지를 보냅니다.
                out.print("ALREADY_EXIST");
            } else {
                // 0개라면 내가 1등! DTO에 담아서 DB에 저장(INSERT)합니다.
                SongDTO song = new SongDTO();
                song.setVideoId(videoId);
                song.setUserId(userId);
                
                // SongDAO.insertSong(song);
                
                // 성공 메시지를 보냅니다.
                out.print("SUCCESS");
            }

        // ========================================================
        // [2] 오늘의 노래 불러오기 로직 (메인 화면이 새로고침 될 때)
        // ========================================================
        } else if ("/getTodaySong.do".equals(requestUri)) {
            
            // DAO에게 "오늘 등록된 노래 영상 ID 좀 가져와줘!" 라고 부탁합니다.
            // String todayVideoId = SongDAO.getTodayVideoId();
            String todayVideoId = null; // 임시 데이터
            
            if (todayVideoId != null) {
                out.print(todayVideoId); // 이미 등록된 영상이 있으면 ID 반환
            } else {
                out.print("NONE");       // 아무도 등록 안 했으면 NONE 반환
            }
        }
    }
}