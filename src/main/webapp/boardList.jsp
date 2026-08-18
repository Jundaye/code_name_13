<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">

<title>게시판</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>

<body>

	<div class="container mt-5">

		<h1 class="text-center mb-4">게시판</h1>


		<!-- 카테고리 메뉴 -->
		<div class="mb-3">

			<a class="btn btn-outline-primary"
				href="${pageContext.request.contextPath}/list.do"> 전체 </a>

		</div>


		<table class="table table-bordered table-hover">

			<thead class="table-light">

				<tr>
					<th>번호</th>
					<th>카테고리</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
					<th>좋아요</th>
				</tr>

			</thead>


			<tbody>

				<c:forEach var="board" items="${boardList}" varStatus="status">

					<tr>

						<!-- 화면용 번호 -->
						<td>${status.count}</td>


						<td>${board.category}</td>


						<td><a
							href="${pageContext.request.contextPath}/detail.do?boardId=${board.boardId}">

								${board.title} </a></td>


						<td>${board.writer}</td>


						<td>${board.regDate}</td>


						<td>${board.viewCount}</td>


						<td>${board.likeCount}</td>

					</tr>

				</c:forEach>


				<!-- 게시글이 없을 경우 -->
				<c:if test="${empty boardList}">

					<tr>

						<td colspan="7" class="text-center">등록된 게시글이 없습니다.</td>

					</tr>

				</c:if>

			</tbody>

		</table>


		<div class="text-end">

			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/writeForm.do"> 글쓰기 </a>

		</div>

	</div>

</body>

</html>
