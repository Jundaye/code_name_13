<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>글쓰기</title>

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
        max-width: 800px;
        margin: 0 auto;
        border: 2px solid #FFFFFF;
        padding: 40px;
        background: #0000AA;
        box-shadow: 5px 5px 0px #000000;
    }

    /* 제목 스타일 */
    h1 {
        color: #FFFF00;
        text-shadow: 2px 2px #000000;
        text-align: center;
        margin-bottom: 30px;
    }
    h1::before, h1::after { content: ' === '; color: #00FFFF; }

    /* 에러 메시지 스타일 */
    .alert {
        border: 1px dashed #FFFF00;
        color: #FFFF00;
        padding: 15px;
        margin-bottom: 25px;
        text-align: center;
        background-color: #000055;
    }

    /* 입력 폼 그룹 영역 */
    .form-group { margin-bottom: 25px; }
    
    label { 
        display: block; 
        color: #00FFFF; 
        margin-bottom: 8px; 
        font-size: 1.1rem; 
    }
    label::before { content: '▶ '; color: #FFFF00; }

    /* 입력창 (Input, Select, Textarea) 스타일 */
    input[type="text"], select, textarea {
        width: 100%;
        padding: 12px;
        background-color: #0000AA;
        color: #FFFFFF;
        border: 2px solid #FFFFFF;
        font-family: 'NeoDunggeunmo', monospace;
        font-size: 1.1rem;
    }
    input[type="text"]:focus, select:focus, textarea:focus {
        outline: none;
        border-color: #FFFF00;
        background-color: #000055;
    }

    /* select 옵션 배경색 처리 */
    option { background-color: #0000AA; color: #FFFFFF; }

    /* 하단 버튼 영역 */
    .btn-group {
        display: flex;
        justify-content: space-between;
        margin-top: 40px;
        border-top: 1px dashed #FFFFFF;
        padding-top: 20px;
    }

    .btn {
        display: inline-block;
        padding: 8px 20px;
        background-color: #0000AA; color: #FFFFFF;
        border: 2px solid #FFFFFF; 
        font-family: 'NeoDunggeunmo', monospace;
        font-size: 1.1rem; cursor: pointer;
        text-align: center;
        text-decoration: none;
    }
    .btn:hover { background-color: #00FFFF; color: #0000AA; border-color: #00FFFF; }
</style>

</head>


<body>

	<div class="container">

		<h1>게시글 작성</h1>

		<!-- 에러 메시지 -->
		<%
		String message = (String) request.getAttribute("message");

		if (message != null) {
		%>
		<div class="alert">
			[안내] <%=message%>
		</div>
		<%
		}
		%>

		<!-- 🚨 form 로직은 유지하되, 모든 입력칸에 required 속성 추가 -->
		<form action="<%=request.getContextPath()%>/write.do" method="post">

			<!-- 카테고리 -->
			<div class="form-group">
				<label>카테고리</label> 
                <select name="category" required>
					<option value="">[분류를 선택하십시오]</option>
					<option value="suggestion">1. 건의사항</option>
					<option value="free">2. 잡담게시판</option>
					<option value="code">3. 코드리뷰</option>
				</select>
			</div>

			<!-- 제목 -->
			<div class="form-group">
				<label>제목</label> 
                <input type="text" name="title" required placeholder="제목을 입력하십시오.">
			</div>

			<!-- 작성자 -->
			<div class="form-group">
				<label>작성자</label>

				<!-- value 속성에 세션에 담긴 닉네임을 불러옵니다. -->
				<input type="text" name="writer" required placeholder="작성자를 입력하십시오."
					value="${sessionScope.loginUser.userName}"
					${not empty sessionScope.loginUser ? 'readonly' : ''}>
			</div>

			<!-- 내용 -->
			<div class="form-group">
				<label>내용</label>
				<textarea name="content" rows="10" required placeholder="내용을 입력하십시오."></textarea>
			</div>

			<!-- 하단 버튼 영역 -->
			<div class="btn-group">
                <a href="<%=request.getContextPath()%>/list.do" class="btn">취소(C)</a>
				<button type="submit" class="btn">등록(Enter)</button>
			</div>

		</form>

	</div>

</body>

</html>