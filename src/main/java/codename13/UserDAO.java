package codename13;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // [회원가입] AddrBookDAO.insert() 패턴 그대로 활용
    public static int insert(UserDTO dto) {
        int result = -1;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "insert into users(user_id, user_pw, user_name, email) values(?, ?, ?, ?)";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getUserId());
            psmt.setString(2, dto.getUserPw());
            psmt.setString(3, dto.getUserName());
            psmt.setString(4, dto.getUserEmail());
            
            result = psmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // [로그인 검증] - 회원 관리에 필수적인 기능!
    public static UserDTO login(String userId, String userPw) {
        UserDTO user = null;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "select * from users where user_id = ? and user_pw = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            psmt.setString(2, userPw);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getString("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user; // 로그인 성공 시 객체 반환, 실패 시 null 반환
    }

    // [회원 상세 정보 조회] AddrBookDAO.getById() 패턴 활용
    public static UserDTO getById(String userId) {
        UserDTO user = null;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "select * from users where user_id = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getString("user_id"));
                user.setUserPw(rs.getString("user_pw"));
                user.setUserName(rs.getString("user_name"));
                user.setUserEmail(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // [회원 정보 수정] AddrBookDAO.update() 패턴 활용
    public static int update(UserDTO dto) {
        int result = -1;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "update users set user_pw = ?, user_name = ?, email = ? where user_id = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getUserPw());
            psmt.setString(2, dto.getUserName());
            psmt.setString(3, dto.getUserEmail());
            psmt.setString(4, dto.getUserId());
            
            result = psmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // [회원 탈퇴/삭제]
    public static int delete(String userId) {
        int result = -1;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "delete from users where user_id = ?";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1, userId);
            
            result = psmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}