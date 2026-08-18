<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>글쓰기</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
	rel="stylesheet">

</head>


<body>

	<div class="container mt-5">

		<h1 class="mb-4">게시글 작성</h1>


		<!-- 에러 메시지 -->
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


		<form action="<%=request.getContextPath()%>/write.do" method="post">


			<!-- 카테고리 -->
			<div class="mb-3">

				<label class="form-label"> 카테고리 </label> <select name="category"
					class="form-select">

					<option value="">카테고리를 선택하세요</option>

					<option value="suggestion">건의사항</option>

					<option value="free">잡담</option>

					<option value="code">코드리뷰</option>

				</select>

			</div>



			<!-- 제목 -->
			<div class="mb-3">

				<label class="form-label"> 제목 </label> <input type="text"
					name="title" class="form-control">

			</div>



			<!-- 작성자 -->
			<div class="mb-3">

				<label class="form-label"> 작성자 </label> <input type="text"
					name="writer" class="form-control">

			</div>



			<!-- 내용 -->
			<div class="mb-3">

				<label class="form-label"> 내용 </label>

				<textarea name="content" class="form-control" rows="10"></textarea>

			</div>



			<button type="submit" class="btn btn-primary">등록</button>


			<a href="<%=request.getContextPath()%>/list.do"
				class="btn btn-secondary"> 취소 </a>

		</form>

	</div>

</body>

</html>
