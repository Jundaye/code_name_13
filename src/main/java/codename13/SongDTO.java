package codename13;

public class SongDTO {
    
    private int songId;       // 노래 고유 번호 (Primary Key)
    private String videoId;   // 11자리 유튜브 영상 ID
    private String userId;    // 등록에 성공한 사람의 아이디 (선착순 승리자!)
    private String regDate;   // 등록된 날짜 (예: "2026-08-18")

    public SongDTO() {}

    public int getSongId() {
        return songId;
    }
    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getVideoId() {
        return videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}