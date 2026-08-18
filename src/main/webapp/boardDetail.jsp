<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="board_error.jsp" import="java.util.*"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE HTML>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>게시글 상세보기</title>

<!-- 기존 외부 CSS는 주석 처리하거나 지워도 무방합니다 (스타일 충돌 방지) -->
<!-- <link rel="stylesheet" href="board.css" type="text/css" media="screen" /> -->

<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous">
</script>

<!-- ========================================== -->
<!-- 🎨 디자인(CSS) 영역만 PC통신 스타일로 변경 -->
<!-- ========================================== -->
<style type="text/css">
    /* 레트로 폰트 적용 */
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
    div[align="center"] {
        max-width: 900px;
        margin: 0 auto;
        border: 2px solid #FFFFFF;
        padding: 40px;
        background: #0000AA;
        box-shadow: 5px 5px 0px #000000; /* 약간의 그림자 투박하게 */
    }

    /* 제목 스타일 */
    H2, H3 { 
        color: #FFFF00; 
        text-shadow: 2px 2px #000000;
        margin-bottom: 20px;
    }
    H2::before, H2::after { content: ' === '; color: #00FFFF; }
    H3::before, H3::after { content: ' = '; color: #00FFFF; }

    HR { border: 0; border-top: 1px dashed #FFFFFF; margin: 20px 0; }

    /* 링크 버튼 스타일 */
    a { color: #00FFFF; text-decoration: none; padding: 2px 5px; }
    a:hover { background-color: #FFFFFF; color: #0000AA; }

    /* 표(Table) 스타일 */
    table {
        width: 100%;
        border-collapse: collapse;
        margin: 20px 0;
        border: 2px solid #FFFFFF;
    }
    th, td {
        border: 1px solid #FFFFFF;
        padding: 12px;
    }
    th {
        background-color: #000055;
        color: #FFFF00;
        white-space: nowrap;
    }
    
    /* 좋아요 카운트 포인트 색상 */
    #likeCount { color: #00FFFF; font-weight: bold; margin-right: 10px; }

    /* 본문 및 댓글 텍스트 정렬 */
    .commentContent { text-align: left; padding-left: 15px; }
    td[colspan="3"] { text-align: left; padding: 20px; }

    /* 입력창(Input) 스타일 */
    input[type="text"] {
        padding: 6px;
        border: 2px solid #FFFFFF; 
        background-color: #0000AA; 
        color: #FFFFFF;
        font-family: 'NeoDunggeunmo', monospace;
        font-size: 1rem;
    }
    input[type="text"]:focus { outline: none; border-color: #FFFF00; background-color: #000055; }
    
    #writer { width: 120px; margin-right: 15px; }
    #content { width: 350px; margin-right: 15px; }

    /* 버튼 스타일 */
    button, input[type="submit"] {
        padding: 6px 12px;
        background-color: #0000AA; color: #FFFFFF;
        border: 2px solid #FFFFFF; 
        font-family: 'NeoDunggeunmo', monospace;
        font-size: 1rem; cursor: pointer;
    }
    button:hover, input[type="submit"]:hover { 
        background-color: #00FFFF; color: #0000AA; border-color: #00FFFF; 
    }

    #btnLike { margin-left: 10px; background-color: #000055; color: #FFFF00; }
    #btnLike:hover { background-color: #FFFF00; color: #0000AA; }

    /* 댓글 작성 영역 박스 처리 */
    #commentForm {
        margin-top: 30px;
        background-color: #000055;
        padding: 20px;
        border: 1px dashed #FFFFFF;
        display: inline-block;
    }
</style>
</head>


<body>

	<div align="center">

		<H2>게시글 상세보기</H2>

		<HR>

		<!-- ======================== -->
		<!-- 이동 메뉴 -->
		<!-- ======================== -->

		[ <a href="main.do"> 초기화면(T) </a> ] &nbsp;
		[ <a href="list.do"> 목록으로(L) </a> ]

		<p>

		<!-- ======================== -->
		<!-- 게시글 내용 -->
		<!-- ======================== -->
		<table border="1">

			<tr>
				<th>카테고리</th>
				<td colspan="3">${board.category}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3">${board.title}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${board.writer}</td>
				<th>작성일</th>
				<td>${board.regDate}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${board.viewCount}</td>
				<th>좋아요</th>
				<td><span id="likeCount"> ${board.likeCount} </span>
					<button type="button" id="btnLike">♥ 좋아요</button></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3" style="text-align: left;">${board.content}</td>
			</tr>

		</table>

		<!-- ======================== -->
		<!-- 수정 / 삭제 -->
		<!-- ======================== -->

		<p>
			[ <a href="updateForm.do?boardId=${board.boardId}"> 수정(E) </a> ] &nbsp;
			[ <a href="delete.do?boardId=${board.boardId}"
				onclick="return confirm('정말 게시글을 삭제하시겠습니까?');"> 삭제(D) </a> ]
		<p>
		<!-- ======================== -->
		<!-- 댓글 -->
		<!-- ======================== -->
		<H3>댓글</H3>

		<table border="1" id="commentTable">

			<thead>
				<tr>
					<th>작성자</th>
					<th>내용</th>
					<th>작성일</th>
					<th>관리</th>
				</tr>
			</thead>

			<tbody id="commentListBody">

				<c:choose>
					<%-- 댓글 없음 --%>
					<c:when test="${empty comments}">
						<tr>
							<td colspan="4">등록된 댓글이 없습니다.</td>
						</tr>
					</c:when>

					<%-- 댓글 있음 --%>
					<c:otherwise>
						<c:forEach items="${comments}" var="cm">
							<tr data-comment-id="${cm.commentId}">
								<td>${cm.commentWriter}</td>
								<td class="commentContent">${cm.commentContent}</td>
								<td>${cm.commentRegdate}</td>
								<td>
									<button type="button" class="btnCommentEdit">수정</button>
									<button type="button" class="btnCommentDelete">삭제</button>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<p>

		<!-- ======================== -->
		<!-- 댓글 작성 -->
		<!-- ======================== -->
		<form id="commentForm">
			작성자 <input type="text" id="writer" maxlength="20"> 
            내용 <input type="text" id="content" maxlength="200"> 
            <input type="submit" value="댓글등록">
		</form>
	</div>

	<!-- ========================================== -->
	<!-- ⚙️ 로직(JavaScript) 영역은 원본 100% 동일 유지 -->
	<!-- ========================================== -->
	<script type="text/javascript">
		// 현재 게시글 번호
		let boardId = ${board.boardId};

		// =====================================
		// 좋아요
		// =====================================
		$("#btnLike").on("click", function() {
			$.post("like.do",
			{
				boardId : boardId
			},
			function(data) {
				if (data.result == "success") {
					$("#likeCount").text(data.likeCount);
				}
			}
			);
		});

		// =====================================
		// 댓글 등록
		// =====================================
		$("#commentForm").on("submit", function(e) {
			// form 기본 전송 막기
			e.preventDefault();

			let writer = $("#writer").val();
			let content = $("#content").val();

			// 빈칸 검사
			if (writer.trim() == "" || content.trim() == "") {
				alert("작성자와 내용을 모두 입력하세요.");
				return;
			}

			$.post(
			"commentInsert.do",
			{
				boardId : boardId,
				commentWriter : writer,
				commentContent : content
			},
			function(data) {
				// 댓글 목록 다시 출력
				showComments(data.comments);
				// 입력창 비우기
				$("#writer").val("");
				$("#content").val("");
			}
			);
		});

		// =====================================
		// 댓글 수정
		// =====================================
		$("#commentListBody").on("click", ".btnCommentEdit", function() {
			let tr = $(this).closest("tr");
			let commentId = tr.data("comment-id");
			let oldContent = tr.find(".commentContent").text().trim();
			let newContent = prompt("수정할 내용을 입력하세요.", oldContent);

			if (newContent == null || newContent.trim() == "") {
				return;
			}

			$.post("commentUpdate.do",
			{
				commentId : commentId,
				boardId : boardId,
				content : newContent
			},
			function(data) {
				showComments(data.comments);
			});
		});

		// =====================================
		// 댓글 삭제
		// =====================================
		$("#commentListBody").on(
		"click",
		".btnCommentDelete",
		function() {
			let tr = $(this).closest("tr");
			let commentId = tr.data("comment-id");
			let result = confirm("정말 댓글을 삭제하시겠습니까?");

			if (!result) {
				return;
			}

			$.post(
			"commentDelete.do",
			{
				commentId : commentId,
				boardId : boardId
			},
			function(data) {
				showComments(data.comments);
			}
			);
		}
		);

		// =====================================
		// 댓글 목록 다시 출력
		// =====================================
		function showComments(comments) {
			let body = $("#commentListBody");
			// 기존 댓글 제거
			body.empty();

			// 댓글이 없는 경우
			if (comments.length == 0) {
				body.append(
				"<tr>" + "<td colspan='4'>" + "등록된 댓글이 없습니다." + "</td>"
						+ "</tr>"
				);
				return;
			}

			// 댓글 반복
			for (let i = 0; i < comments.length; i++) {
				let comment = comments[i];
				let html = "";
				html += "<tr data-comment-id='"
                + comment.commentId
                + "'>";
				html += "<td>" + comment.commentWriter + "</td>";
				html += "<td class='commentContent'>" + comment.commentContent
						+ "</td>";
				html += "<td>" + comment.commentRegdate + "</td>";
				html += "<td>";
				html += "<button "
                + "type='button' "
                + "class='btnCommentEdit'>"
						+ "수정" + "</button>";
				html += " ";
				html += "<button "
                + "type='button' "
                + "class='btnCommentDelete'>"
						+ "삭제" + "</button>";
				html += "</td>";
				html += "</tr>";

				body.append(html);
			}
		}
	</script>

</body>

</html>