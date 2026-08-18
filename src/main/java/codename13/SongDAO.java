package codename13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SongDAO {

    // 1. 오늘 등록된 노래가 있는지(몇 개인지) 검사하는 메서드
    public static int getTodaySongCount() {
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // DB가 오라클(Oracle)일 경우를 가정한 쿼리입니다. 
        // 오늘 날짜(SYSDATE)와 등록일(reg_date)의 '년-월-일'이 같은 데이터의 개수를 셉니다.
        String sql = "SELECT COUNT(*) FROM song_table WHERE TO_CHAR(reg_date, 'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD')";

        try {
            // 다예님이 만들어두신 DBUtil을 통해 DB와 연결합니다.
            conn = DBUtil.getConnection(); 
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // DB 연결 해제 (DBUtil에 close 메서드가 있다면 맞게 수정해 주세요)
            // DBUtil.close(rs, pstmt, conn); 
        }
        return count;
    }

    // 2. 선착순 1등의 유튜브 링크를 DB에 저장(INSERT)하는 메서드
    public static void insertSong(SongDTO song) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        // 시퀀스(song_seq.NEXTVAL)를 이용해 글 번호를 자동 증가시킵니다.
        String sql = "INSERT INTO song_table (song_id, video_id, user_id, reg_date) " +
                     "VALUES (song_seq.NEXTVAL, ?, ?, SYSDATE)";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, song.getVideoId()); // 컨트롤러에서 파싱한 11자리 유튜브 ID
            pstmt.setString(2, song.getUserId());  // 1등으로 등록한 유저의 ID
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // DBUtil.close(pstmt, conn);
        }
    }

    // 3. 메인 화면에 띄울 '오늘의 영상 ID'를 가져오는 메서드
    public static String getTodayVideoId() {
        String videoId = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT video_id FROM song_table WHERE TO_CHAR(reg_date, 'YYYY-MM-DD') = TO_CHAR(SYSDATE, 'YYYY-MM-DD')";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 오늘 날짜로 등록된 데이터가 존재한다면 videoId 변수에 담아줍니다.
            if (rs.next()) {
                videoId = rs.getString("video_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // DBUtil.close(rs, pstmt, conn);
        }
        return videoId;
    }
}