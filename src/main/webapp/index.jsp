<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${hotBoardList == null}"><c:redirect url="/main.do"/></c:if>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title>슬기로운 개발일지</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style> <%@ include file="style.jsp" %> </style>
</head>
<body>
    <div class="container">
        <!-- 좌측 사이드바 -->
        <aside class="sidebar">
            <div class="profile-section">
                <c:choose>
                    <%-- 1. 로그인이 되어 있는 경우 --%>
                    <c:when test="${not empty sessionScope.loginUser}">
                        <div class="profile-img">ID</div>
                        <p class="profile-name">[ ${sessionScope.loginUser.userName} ]</p>
                        <p style="font-size: 1.1rem; margin-top: 10px; color:#00FFFF;">접속중...</p>
                        <button type="button" class="btn-primary" onclick="location.href='logout.do'">접속종료 (Q)</button>
                    </c:when>

					<%-- 2. 로그인이 안 되어 있는 경우 --%>
					<c:otherwise>
						<!-- action 주소에 절대경로 추가 -->
						<form action="${pageContext.request.contextPath}/login.do"
							method="post" style="width: 100%;">
							<h3 style="font-size: 1.3rem; margin-bottom: 10px; text-align: center; color: #FFFF00;">[이용자 접속 ]</h3>

							<input type="text" name="userId" class="input-box"
								placeholder="아이디(ID)"> <input type="password"
								name="userPw" class="input-box" placeholder="비밀번호(PW)">

							<!-- location.href 주소에도 절대경로 추가 -->
							<button type="button" class="btn-primary" style="margin-bottom: 10px;"
								onclick="location.href='${pageContext.request.contextPath}/joinForm.do'">가입 (REGISTER)</button>

							<!-- 접속 버튼 -->
							<button type="submit" class="btn-primary">접 속 (ENTER)</button>
						</form>
					</c:otherwise>
				</c:choose>
            </div>

            <nav class="category-menu">
    <h3>[ 메 뉴 선 택 ]</h3>
    
    <!-- 폼 시작: list.do로 데이터를 보냅니다. GET 방식이라 주소창에 파라미터가 붙습니다. -->
    <form action="list.do" method="get">
        <ul>
            <li>
                <!-- name="category"와 value="원하는값"이 핵심입니다! -->
                <button type="submit" name="category" value="suggestion" class="category-btn">
                    <span class="num">1.</span> 건의사항
                </button>
            </li>
            <li>
                <button type="submit" name="category" value="free" class="category-btn">
                    <span class="num">2.</span> 잡담 게시판
                </button>
            </li>
            <li>
                <button type="submit" name="category" value="code" class="category-btn">
                    <span class="num">3.</span> 코드 리뷰
                </button>
            </li>
        </ul>
    </form>
    
    <p style="margin-top:20px; font-size:1.1rem; color:#00FFFF;">선택(1-3) <span class="blink">_</span></p>
</nav>
        </aside>

        <!-- 우측 메인 영역 -->
        <main class="main-content">
            <header>
                <h1 class="header-title"><span>PC통신:</span> 슬기로운 개발일지</h1>
            </header>

            <div class="post-container">
                <div style="position:relative;">
                    <h2 class="section-title">[ 핫 게시물 ]</h2>
                </div>

                <c:if test="${empty hotBoardList}">
                    <article class="post-card empty">
                        <h3 class="post-title" style="color: #AAAAAA; font-size: 1.2rem;">
                            등록된 자료가 없습니다.
                        </h3>
                    </article>
                </c:if>

                <c:forEach var="post" items="${hotBoardList}">
                    <article class="post-card">
                        <!-- 제목을 누르면 해당 글로 이동하도록 링크 추가 -->
                        <h3 class="post-title">
                            <a href="${pageContext.request.contextPath}/detail.do?boardId=${post.boardId}" style="color: #FFFF00; text-decoration: none;">
                                ${post.title}
                            </a>
                        </h3>
                        <p class="post-body">${post.content}</p>
                        <div class="post-reaction">추천: ${post.likeCount} | 조회: ${post.viewCount}</div>
                    </article>
                </c:forEach>
                
            </div>

            <!-- 오늘의 노래 섹션 -->
            <section class="song-section">
                <h2 class="section-title">[ 오늘의 노동요 ]</h2>
                <p style="font-size: 1.1rem; margin-top: 10px; color: #00FFFF;">안내: 유튜브 주소를 입력 후 실행하십시오.</p>
                <div class="song-input-area" id="inputArea">
                    <input type="text" id="songInput" class="input-box" placeholder="URL 입력...">
                    <button id="submitSongBtn" class="btn-primary" style="margin-top: 0; width: auto; padding: 0 30px;">실 행</button>
                </div>

                <div class="video-area" id="videoArea"></div>
            </section>
        </main>
    </div>

    <script>
$(document).ready(function() {
    
    // ==========================================================
    // 1. [자동 실행] 메인 화면에 들어오면 '오늘의 노래'가 있는지 확인!
    // ==========================================================
    $.get("getTodaySong.do", function(data) {
        // 서버가 "NONE"이 아닌 유튜브 ID를 보내줬다면 (누군가 이미 등록했다면)
        if (data !== "NONE") {
            $('#inputArea').hide(); // 입력창은 숨기고
            $('#videoArea').html('<iframe src="https://www.youtube.com/embed/' + data + '" allowfullscreen></iframe>').show(); // 영상을 띄웁니다!
        }
    });

    // ==========================================================
    // 2. [클릭 실행] 사용자가 '실행' 버튼을 눌렀을 때 선착순 등록 요청!
    // ==========================================================
    $('#submitSongBtn').on('click', function() {
        let url = $('#songInput').val(); 
        
        if(url === "") {
            alert("오류: URL을 입력하십시오.");
            return;
        }
        
        let extractedId = "";
        if(url.includes("v=")) {
            extractedId = url.split("v=")[1].substring(0, 11);
        } else if(url.includes("youtu.be/")) {
            extractedId = url.split("youtu.be/")[1].substring(0, 11);
        } else {
            alert("오류: 올바른 유튜브 형식이 아닙니다.");
            return;
        }

        // 단순히 화면만 바꾸는 게 아니라, 서버(SongController)로 데이터를 보냅니다!
        $.post("songRegister.do", 
        { 
            videoId: extractedId 
        }, 
        function(response) {
            if (response === "SUCCESS") {
                alert("축하합니다! 오늘의 노동요 1등 등록에 성공하셨습니다.");
                $('#inputArea').hide();
                $('#videoArea').html('<iframe src="https://www.youtube.com/embed/' + extractedId + '" allowfullscreen></iframe>').fadeIn();
            } 
            else if (response === "ALREADY_EXIST") {
                alert("앗! 간발의 차이로 다른 이용자가 먼저 오늘의 노래를 등록했습니다.");
                // 이미 등록되었으니 새로고침해서 그 승리자의 노래를 띄워줍니다.
                location.reload(); 
            }
            else if (response === "GUEST") {
                alert("이용자 접속(로그인) 후 이용하실 수 있습니다.");
            }
        });
    });
});
</script>
</body>
</html>