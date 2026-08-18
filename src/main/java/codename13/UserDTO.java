package codename13;

public class UserDTO { // 기본 생성자: 자바 빈즈 규약 및 프레임워크 호환성을 위해 필수 
	String userId;
	String userPw;
	String userName;
	String userEmail;
	String userTel;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	@Override
    public String toString() {
        return "UserDTO [userId=" + userId + ", userName=" + userName + ", userEmail=" + userEmail + ", userTel=" + userTel + "]";
    }
}
	/*CREATE TABLE users (
		    user_id    VARCHAR(30)  NOT NULL,           -- 사용자 로그인 아이디 (기본키)
		    user_pw    VARCHAR(100) NOT NULL,           -- 비밀번호 (암호화 고려하여 길이 여유 있게)
		    user_name  VARCHAR(20)  NOT NULL,           -- 이름
		    email      VARCHAR(50)  DEFAULT NULL,       -- 이메일
		    tel        VARCHAR(20)  DEFAULT NULL,       -- 전화번호
		    reg_date   DATETIME     DEFAULT NOW(),      -- 가입일자
		    PRIMARY KEY (user_id)
		);
*/