<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>게시글 수정</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>


<body>

	<div class="container mt-5">

		<h1 class="mb-4">게시글 수정</h1>


		<!-- 오류 메시지 -->
		<%
		String message = (String) request.getAttribute("message");

		if (message != null) {
		%>

		<div class="alert alert-danger">

			<%=message%>

		</div>

		<%
		}
		%>



		<form action="<%=request.getContextPath()%>/update.do" method="post">


			<!-- 게시글 번호 -->
			<input type="hidden" name="boardId" value="${board.boardId}">



			<!-- 카테고리 -->
			<div class="mb-3">

				<label class="form-label"> 카테고리 </label> <select name="category"
					class="form-select">


					<option value="suggestion"
						${board.category == 'suggestion' ? 'selected' : ''}>건의사항

					</option>


					<option value="free" ${board.category == 'free' ? 'selected' : ''}>

						잡담</option>


					<option value="code" ${board.category == 'code' ? 'selected' : ''}>

						코드리뷰</option>

				</select>

			</div>



			<!-- 제목 -->
			<div class="mb-3">

				<label class="form-label"> 제목 </label> <input type="text"
					name="title" value="${board.title}" class="form-control">

			</div>



			<!-- 작성자 -->
			<div class="mb-3">

				<label class="form-label"> 작성자 </label> <input type="text"
					name="writer" value="${board.writer}" class="form-control">

			</div>



			<!-- 내용 -->
			<div class="mb-3">

				<label class="form-label"> 내용 </label>


				<textarea name="content" class="form-control" rows="10">${board.content}</textarea>

			</div>



			<button type="submit" class="btn btn-warning">수정완료</button>



			<a
				href="<%=request.getContextPath()%>/detail.do?boardId=${board.boardId}"
				class="btn btn-secondary"> 취소 </a>

		</form>

	</div>

</body>

</html>
