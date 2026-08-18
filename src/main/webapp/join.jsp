<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style type="text/css">
    @import url('https://cdn.jsdelivr.net/gh/neodgm/neodgm-webfont@1.530/neodgm/style.css');
    * { box-sizing: border-box; }
    body {
        font-family: 'NeoDunggeunmo', '돋움', Dotum, monospace;
        background-color: #0000AA; color: #FFFFFF; line-height: 1.6; padding: 30px;
    }
    ::selection { background: #FFFFFF; color: #0000AA; }
    
    .container {
        max-width: 600px; margin: 0 auto; border: 2px solid #FFFFFF;
        padding: 40px; background: #0000AA; box-shadow: 5px 5px 0px #000000;
    }
    h1 { color: #FFFF00; text-shadow: 2px 2px #000000; text-align: center; margin-bottom: 30px; }
    h1::before, h1::after { content: ' === '; color: #00FFFF; }
    
    .alert {
        border: 1px dashed #FFFF00; color: #FFFF00; padding: 15px;
        margin-bottom: 25px; text-align: center; background-color: #000055;
    }
    
    .form-group { margin-bottom: 25px; }
    label { display: block; color: #00FFFF; margin-bottom: 8px; font-size: 1.1rem; }
    label::before { content: '▶ '; color: #FFFF00; }
    
    input[type="text"], input[type="password"] {
        width: 100%; padding: 12px; background-color: #0000AA; color: #FFFFFF;
        border: 2px solid #FFFFFF; font-family: 'NeoDunggeunmo', monospace; font-size: 1.1rem;
    }
    input[type="text"]:focus, input[type="password"]:focus {
        outline: none; border-color: #FFFF00; background-color: #000055;
    }
    
    .btn-group {
        display: flex; justify-content: space-between; margin-top: 40px;
        border-top: 1px dashed #FFFFFF; padding-top: 20px;
    }
    .btn {
        display: inline-block; padding: 8px 20px; background-color: #0000AA; color: #FFFFFF;
        border: 2px solid #FFFFFF; font-family: 'NeoDunggeunmo', monospace;
        font-size: 1.1rem; cursor: pointer; text-decoration: none; text-align: center;
    }
    .btn:hover { background-color: #00FFFF; color: #0000AA; border-color: #00FFFF; }
</style>
</head>
<body>
	<div class="container">
		<h1>신규 이용자 가입</h1>

		<%
		String message = (String) request.getAttribute("message");
		if (message != null) {
		%>
		<div class="alert">[안내] <%=message%></div>
		<% } %>

		<form action="<%=request.getContextPath()%>/join.do" method="post">
			<div class="form-group">
				<label>희망 아이디 (ID)</label> 
                <input type="text" name="userId" required placeholder="영문, 숫자 조합">
			</div>

			<div class="form-group">
				<label>비밀번호 (PASSWORD)</label> 
                <input type="password" name="userPw" required placeholder="비밀번호를 입력하십시오.">
			</div>

			<div class="form-group">
				<label>이름 (별명)</label> 
                <input type="text" name="userName" required placeholder="사용하실 이름을 입력하십시오.">
			</div>

			<div class="btn-group">
                <a href="<%=request.getContextPath()%>/index.jsp" class="btn">취소(C)</a>
				<button type="submit" class="btn">가입완료(Enter)</button>
			</div>
		</form>
	</div>
</body>
</html>