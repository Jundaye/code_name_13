package codename13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SongDAO {

    // 1. 오늘 등록된 노래가 있는지(몇 개인지) 검사하는 메서드
    public static int getTodaySongCount() {
        int count = 0;
        // 💡 MySQL 문법: DATE(reg_date) = CURDATE() 로 오늘 날짜를 비교합니다.
        String sql = "SELECT COUNT(*) FROM song_table WHERE DATE(reg_date) = CURDATE()";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // 2. 선착순 1등의 유튜브 링크를 DB에 저장(INSERT)하는 메서드
    public static void insertSong(SongDTO song) {
        // 💡 MySQL 문법: song_id는 AUTO_INCREMENT로 자동 증가, reg_date는 NOW()로 현재 시간 입력
        String sql = "INSERT INTO song_table (video_id, user_id, reg_date) VALUES (?, ?, NOW())";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, song.getVideoId());
            pstmt.setString(2, song.getUserId());
            pstmt.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. 메인 화면에 띄울 '오늘의 영상 ID'를 가져오는 메서드
    public static String getTodayVideoId() {
        String videoId = null;
        // 💡 MySQL 문법: 오늘 등록된 영상 1개를 확실하게 가져옵니다.
        String sql = "SELECT video_id FROM song_table WHERE DATE(reg_date) = CURDATE() ORDER BY song_id DESC LIMIT 1";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                videoId = rs.getString("video_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoId;
    }
}