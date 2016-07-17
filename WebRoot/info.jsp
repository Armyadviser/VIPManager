<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>会员管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/lanrenzhijia.css" rel="stylesheet">
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
	enableVipManage(1);
});
function showWindow() {
	$('.theme-popover-mask').fadeIn(100);
	$('.theme-popover').slideDown(200);
}
function closeWindow() {
	$('.theme-popover-mask').fadeOut(100);
	$('.theme-popover').slideUp(200);
}
function enableOrderList() {
	$("#btnVipManage").removeClass('active');
	$("#btnOrderList").addClass('active');
	
	$.getJSON("json/initialize.json", function(data) {
		var text = "<tr class=\"warning\">";
		$.each(data.title.orderList, function(i, item) {
			text += "<th>" + item + "</th>"
		});
		text += "</tr>";
		$("#tabletitle").html(text);
	});
	
	$.post("", {}, function(data, textStatus, req) {
		
	}, "json");
}
function enableVipManage(page) {
	$("#btnOrderList").removeClass('active');
	$("#btnVipManage").addClass('active');
	
	$.getJSON("json/initialize.json", function(data) {
		var text = "<tr class=\"warning\">";
		$.each(data.title.vipManage, function(i, item) {
			text += "<th>" + item + "</th>"
		});
		text += "</tr>";
		$("#tabletitle").html(text);
	});
	
	$.post("Search", {
		'key' : '',
		'page' : page,
		'size' : 10
	}, function(data, textStatus, req) {
		//清空原数据
		$("#tablecontent").html('');
		
		//刷新新数据
		$.each(data.data, function(i, vip) {
			var text = "<tr class=\"success\">" + 
				"<td>" + vip.no + "</td>" + 
				"<td>" + vip.name + "</td>" + 
				"<td>" + vip.tel + "</td>" + 
				"<td>" + vip.date + "</td>" + 
				"<td>" + vip.credit + "</td>" + 
				"<td>" + vip.point + "</td>" + 
				"<td>" + vip.level + "</td>";
			text += "<td><button>消费</button>/<button>充值</button></td></tr>";
			$("#tablecontent").append(text);
		});
		
		var text = "";
		$.each(data.page, function(i, item) {
			text += "<li";
			if (item == page) {
				text += " class=\"active\"";
			}
			text += "><a href=\"#\">" + item + "</a></li>";
		});
		$("#pager").html(text);
	}, "json");
}
</script>
</head>
<body>
<div style="width:100%; height:20%">&nbsp;</div>
	<div class="container">
		<div class="row">
			<div class="col-lg-3">
				<h3 style="display:inline">查询：</h3><input class="input-sm search-query" style="vertical-align:middle" type="text" />
			</div>
			<div class="col-lg-8">&nbsp;</div>
			<div class="col-lg-1">
				<a class="btn btn-primary btn-sm" onclick="showWindow()">添加</a>
				<div class="theme-popover">
				     <div class="theme-poptit">
				          <a onclick="closeWindow()" title="关闭" class="close">×</a>
				          <h4>添加VIP信息</h4>
				     </div>
				     <div class="theme-popbod dform">
				     	<form class="theme-signin" action="CreateOrModify" method="post">
				        	<table width="50%" height="100%" cellspacing="20" >
				        		<tr>
				        			<td><h3>姓名</h3></td>
				        			<td align="right"><input class="input-sm" type="text" name="name" size="20" /></td>
				        		</tr>
				        		<tr><td>&nbsp;</td></tr>
				        		<tr>
									<td><h3>电话</h3></td>
									<td align="right"><input class="input-sm" type="text" name="tel" size="20" /></td>
								</tr>
				        		<tr><td>&nbsp;</td></tr>
								<tr>
									<td><h3>余额</h3></td>
									<td align="right"><input class="input-sm" type="text" name="credit" size="20" /></td>
								</tr>
				        		<tr><td>&nbsp;</td></tr>
								<tr>
									<td><h3>等级</h3></td>
									<td align="right"><input class="input-sm" type="text" name="level" size="20" /></td>
				        		</tr>
				        		<tr><td>&nbsp;</td></tr>
				        		<tr>
									<td colspan="2">
										<input class="btn btn-primary" style="width: 100%;" type="submit" name="submit" value=" 确 定 " />
									</td>
				        		</tr>
				        	</table>
			           </form>
				     </div>
				</div>
				<div class="theme-popover-mask"></div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span12">
				<table class="table table-hover table-bordered">
					<thead id="tabletitle">
						<tr class="warning">
							<th>会员号</th>
							<th>姓名</th>
							<th>电话</th>
							<th>创建日期</th>
							<th>余额</th>
							<th>积分</th>
							<th>等级</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="tablecontent">
						<tr class="success">
							<td>1</td>
							<td>TB - Monthly</td>
							<td>01/04/2012</td>
							<td>Approved</td>
							<td>Approved</td>
							<td>Approved</td>
							<td>Approved</td>
							<td>消费/充值</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-lg-6" style="text-align:left">
				<ul class="nav nav-pills">
				   <li id="btnVipManage" class="active"><a onclick="enableVipManage(1)">会员管理</a></li>
				   <li id="btnOrderList"><a onclick="enableOrderList()">消费记录</a></li>
				</ul>
			</div>
			<div class="col-lg-6" style="text-align:right">
				<ul class="pagination" id="pager">
				  <li><a href="#">&laquo;</a></li>
				  <li><a href="#">1</a></li>
				  <li><a href="#">2</a></li>
				  <li><a href="#">3</a></li>
				  <li><a href="#">4</a></li>
				  <li><a href="#">5</a></li>
				  <li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
