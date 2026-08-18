<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<title>게시판</title>

<!-- ========================================== -->
<!-- 🎨 디자인(CSS) 영역: PC통신 레트로 스타일 적용 -->
<!-- ========================================== -->
<style type="text/css">
    /* 레트로 픽셀 폰트 적용 */
    @import url('https://cdn.jsdelivr.net/gh/neodgm/neodgm-webfont@1.530/neodgm/style.css');

    * { box-sizing: border-box; }

    /* 바탕 및 기본 글꼴 설정 */
    body {
        font-family: 'NeoDunggeunmo', '돋움', Dotum, monospace;
        background-color: #0000AA; /* 진파랑 배경 */
        color: #FFFFFF; /* 흰색 글씨 */
        line-height: 1.6;
        padding: 30px;
    }

    ::selection { background: #FFFFFF; color: #0000AA; }

    /* 메인 컨테이너 */
    .container {
        max-width: 950px;
        margin: 0 auto;
        border: 2px solid #FFFFFF;
        padding: 40px;
        background: #0000AA;
        box-shadow: 5px 5px 0px #000000;
    }

    /* 제목 스타일 */
    H1 {
        color: #FFFF00;
        text-shadow: 2px 2px #000000;
        text-align: center;
        margin-bottom: 30px;
    }
    H1::before, H1::after { content: ' === '; color: #00FFFF; }

    /* 상단 메뉴바 레이아웃 */
    .menu-bar {
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
        margin-bottom: 15px;
    }

    /* 링크 버튼 스타일 */
    a { color: #00FFFF; text-decoration: none; }
    a:hover { background-color: #FFFFFF; color: #0000AA; }

    .btn {
        display: inline-block;
        padding: 6px 15px;
        background-color: #0000AA; color: #FFFFFF;
        border: 2px solid #FFFFFF; 
        font-family: 'NeoDunggeunmo', monospace;
        font-size: 1.1rem; cursor: pointer;
        text-align: center;
    }
    .btn:hover { background-color: #00FFFF; color: #0000AA; border-color: #00FFFF; }

    /* 표(Table) 스타일 */
    table {
        width: 100%;
        border-collapse: collapse;
        margin: 10px 0 30px 0;
        border: 2px solid #FFFFFF;
    }
    th, td {
        border: 1px solid #FFFFFF;
        padding: 10px;
        text-align: center;
    }
    th {
        background-color: #000055;
        color: #FFFF00;
        white-space: nowrap;
        font-size: 1.1rem;
    }
    
    /* 테이블 내부 행 호버 효과 */
    tbody tr:hover { background-color: #000055; }
    
    /* 제목 열만 좌측 정렬 및 여백 주기 */
    td.title-cell { text-align: left; padding-left: 15px; }
    
    /* 🚨 하단 버튼 정렬 (양쪽 끝으로 배치되도록 수정됨) */
    .bottom-bar { 
        display: flex; 
        justify-content: space-between; 
        margin-top: 10px; 
    }
</style>

</head>

<body>

	<div class="container">

		<H1>게시판</H1>

		<!-- 상단 카테고리 / 상태 바 -->
		<div class="menu-bar">
			<a class="btn" href="${pageContext.request.contextPath}/list.do">[전체 보기]</a>
            <span style="color: #00FFFF;">현재 위치: 홈 > 전체 게시판</span>
		</div>

		<!-- 게시글 목록 테이블 -->
		<table>
			<thead>
				<tr>
					<th width="8%">번호</th>
					<th width="15%">카테고리</th>
					<th width="auto">제목</th>
					<th width="12%">작성자</th>
					<th width="15%">작성일</th>
					<th width="8%">조회</th>
					<th width="8%">추천</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="board" items="${boardList}" varStatus="status">
					<tr>
						<!-- 화면용 번호 -->
						<td>${status.count}</td>

						<!-- 카테고리 -->
						<td>
                            <c:choose>
								<c:when test="${board.category == 'code'}">코드리뷰</c:when>
								<c:when test="${board.category == 'suggestion'}">건의사항</c:when>
								<c:when test="${board.category == 'free'}">잡담</c:when>
								<c:otherwise>${board.category}</c:otherwise>
							</c:choose>
                        </td>

						<!-- 제목 -->
						<td class="title-cell">
                            <a href="${pageContext.request.contextPath}/detail.do?boardId=${board.boardId}">
    							${board.title}
						    </a>
                        </td>

						<!-- 작성자, 작성일, 조회수, 좋아요 -->
						<td>${board.writer}</td>
						<td>${board.regDate}</td>
						<td>${board.viewCount}</td>
						<td style="color: #00FFFF;">${board.likeCount}</td>
					</tr>
				</c:forEach>

				<!-- 게시글이 없을 경우 -->
				<c:if test="${empty boardList}">
					<tr>
						<td colspan="7" style="padding: 40px; color: #AAAAAA; font-size: 1.1rem;">
                            등록된 게시글이 없습니다.
                        </td>
					</tr>
				</c:if>
			</tbody>
		</table>

		<!-- 🚨 하단 버튼 영역 (초기화면 버튼이 추가되었습니다) -->
		<div class="bottom-bar">
			<a class="btn" href="${pageContext.request.contextPath}/main.do">초기화면(T)</a>
			<a class="btn" href="${pageContext.request.contextPath}/writeForm.do">글쓰기(W)</a>
		</div>

	</div>

</body>

</html>