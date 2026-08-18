<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="board_error.jsp" import="java.util.*"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE HTML>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>게시글 상세보기</title>

<link rel="stylesheet" href="board.css" type="text/css" media="screen" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous">
	
</script>


<style type="text/css">
#btnLike {
	cursor: pointer;
}

.commentContent {
	text-align: left;
}
</style>

</head>


<body>

	<div align="center">


		<H2>게시글 상세보기</H2>

		<HR>

		<!-- ======================== -->
		<!-- 게시글 목록 -->
		<!-- ======================== -->

		[ <a href="list.do"> 목록으로 </a> ]

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

					<button type="button" id="btnLike">좋아요</button></td>
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

			[ <a href="updateForm.do?boardId=${board.boardId}"> 수정 </a> ] &nbsp;

			[ <a href="delete.do?boardId=${board.boardId}"
				onclick="return confirm('정말 게시글을 삭제하시겠습니까?');"> 삭제 </a> ]
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


			작성자 <input type="text" id="writer" maxlength="20"> 내용 <input
				type="text" id="content" maxlength="200"> <input
				type="submit" value="댓글등록">


		</form>


	</div>



	<script type="text/javascript">
		// 현재 게시글 번호
		let boardId = $
		{
			board.boardId
		};

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
