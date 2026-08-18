package codename13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // 1. 회원가입 메서드
	public static boolean join(UserDTO user) {

	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    String sql = "INSERT INTO users (user_id, user_pw, user_name) VALUES (?, ?, ?)";

	    try {
	        conn = DBUtil.getConnection();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, user.getUserId());
	        pstmt.setString(2, user.getUserPw());
	        pstmt.setString(3, user.getUserName());

	        int result = pstmt.executeUpdate();

	        return result > 0;

	    } catch (Exception e) {
	        e.printStackTrace();

	    } finally {
	        // DBUtil.close(pstmt, conn);
	    }

	    return false;
	}

    // 2. 로그인 검증 메서드
    public static UserDTO login(String userId, String userPw) {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM users WHERE user_id = ? AND user_pw = ?";
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getString("user_id"));
                user.setUserPw(rs.getString("user_pw"));
                user.setUserName(rs.getString("user_name"));
                user.setRegDate(rs.getString("reg_date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // DBUtil.close(rs, pstmt, conn);
        }
        return user; // 정보가 맞으면 user 객체 반환, 틀리면 null 반환
    }
}