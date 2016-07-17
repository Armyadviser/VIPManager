<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>会员管理系统</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div style="width:100%; height:40%">&nbsp;</div>
<div class="container">
	<div class="row">
		<div class="col-lg-4"></div>
			<div class="col-lg-4">
				<div class="form-group">
					<input class="form-control input-lg" type="password" 
						name="password" id="password" onKeyUp="submitData(this.value)" 
						placeholder="输入密码"/><br>
					<span id="msg" style="color:red"></span>
				</div>
			</div>
		<div class="col-lg-4"></div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#password").focus();
});

function submitData(pwd) {
	$.ajax({
		type:'POST',
		data:{'password':pwd},
		url:'Login',
		success:function() {
			window.location.href='info.jsp';
		},
		error:function() {
			$("#msg").text("密码错误");
		}
	});
}
</script>
</body>
</html>