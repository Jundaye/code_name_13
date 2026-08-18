package codename13;

public class UserDTO {
    private String userId;
    private String userPw;
    private String userName;
    private String regDate;

    // 기본 생성자
    public UserDTO() {}

    // Getter & Setter
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserPw() { return userPw; }
    public void setUserPw(String userPw) { this.userPw = userPw; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getRegDate() { return regDate; }
    public void setRegDate(String regDate) { this.regDate = regDate; }
}