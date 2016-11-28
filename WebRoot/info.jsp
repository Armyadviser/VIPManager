<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>会员管理系统</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/lanrenzhijia.css" rel="stylesheet">
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script>
var vipEnabled = false;
var orderEnabled = false;

$(document).ready(function() {
	enableVipManage(1, '');
});
function showWindow(win_id, docId, vipNo) {
	$('#popmask').fadeIn(100);
	$('#' + win_id).slideDown(200);
	$('#' + docId).val(vipNo);
}
function closeWindow(win_id) {
	$('#popmask').fadeOut(100);
	$('#' + win_id).slideUp(200);
}
function enableOrderList(page, key) {
	orderEnabled = true;
	vipEnabled = false;
	
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
	
	$.post("SearchOrder", {
		'key' : key,
		'page' : page,
		'size' : 10
		}, function(data, textStatus, req) {
			console.table(data.data);
			//清空原数据
			$("#tablecontent").html('');
			
			$.each(data.data, function(i, order) {
				var text = "<tr class=\"success\">" + 
					"<td>" + order.no + "</td>" + 
					"<td>" + order.vipNo + "</td>" + 
					"<td>" + order.vipName + "</td>" + 
					"<td>" + order.name + "</td>" + 
					"<td>" + order.date + "</td>" + 
					"<td>" + order.money + "</td>" + 
					"</tr>";
				$("#tablecontent").append(text);
			});
			
			showPageNumber(page, data.page);
		}, "json"
	);
	
}
function enableVipManage(page, key) {
	vipEnabled = true;
	orderEnabled = false;
	
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
	
	$.post("SearchVip", {
		'key' : key,
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
			text += "<td><button class=\"btn btn-primary btn-sm\" onclick=\"showWindow('win_add_order', 'order_vip_no', '" + vip.no +"')\">消费</button>&nbsp;" + 
				"<button class=\"btn btn-primary btn-sm\" onclick=\"showWindow('win_charge', 'charge_vip_no', '" + vip.no +"')\">充值</button></td>&nbsp;" +
				"<button class=\"btn btn-primary btn-sm\" onclick=\"deleteVip(" + vip.no + ")\">注销</button></tr>";
			$("#tablecontent").append(text);
		});
		
		showPageNumber(page, data.page);
	}, "json");
}

function deleteVip(no) {
	$.post("DeleteVip",{'no' : no}, function(data, status, req) {
		if (status == 200) {
			alert("注销成功");
		} else {
			alert("注销失败");
		}
	});
}

function showPageNumber(page, pageData) {
	var text = "";
	$.each(pageData, function(i, item) {
		text += "<li";
		if (item == page) {
			text += " class=\"active\"";
		}
		if (vipEnabled) {
			text += "><a href=\"enableVipManage(" + page + ", '')\">" + item + "</a></li>";
		} else {
			text += "><a href=\"enableOrderList(" + page + ", '')\">" + item + "</a></li>";
		}
	});
	$("#pager").html(text);
}

function postOrder() {
	$.post(
		"AddOrder",
		{
			'vipNo' : $('#order_vip_no').val(),
			'name' : $('#order_name').val(),
			'money' : $('#order_money').val()
		}, function(data, textStatus, req) {
			if (textStatus == 200) {
				alert("消费成功");
			} else {
				alert("消费失败");
			}
			enableOrderList(1);
		}
	);
}

function search() {
	var keyword = $("#keyword").val();
	if (vipEnabled) {
		enableVipManage(1, keyword);
	}
	if (orderEnabled) {
		enableOrderList(1, keyword);
	}
}
</script>
</head>
<body>
<div style="width:100%; height:20%">&nbsp;</div>
	<div class="container">
		<div class="row">
			<div class="col-lg-3">
				<h3 style="display:inline">查询：</h3>
				<input class="input-sm search-query" style="vertical-align:middle" 
					type="text" id="keyword" onblur="search()"/>
			</div>
			<div class="col-lg-8">&nbsp;</div>
			<div class="col-lg-1">
				<a class="btn btn-primary btn-sm" onclick="showWindow('win_add_vip')">添加</a>
				<div id="win_add_vip" class="theme-popover">
				     <div class="theme-poptit">
				          <a onclick="closeWindow('win_add_vip')" title="关闭" class="close">×</a>
				          <h4>添加VIP信息</h4>
				     </div>
				     <div class="theme-popbod dform">
				     	<form class="theme-signin" action="CreateOrModifyVip" method="post">
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
									<td><h3>折扣</h3></td>
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
				<div id="win_add_order" class="theme-popover">
				     <div class="theme-poptit">
				          <a onclick="closeWindow('win_add_order')" title="关闭" class="close">×</a>
				          <h4>新增订单</h4>
				     </div>
				     <div class="theme-popbod dform">
				     	<form class="theme-signin" onsubmit="postOrder()" method="post">
				     		<input type="hidden" name="vipNo" id="order_vip_no"/>
				        	<table width="70%" height="100%" cellspacing="20" >
				        		<tr>
				        			<td width="40%" align="right"><h3>商品/服务</h3></td>
				        			<td width="60%" align="left"><input class="input-sm" type="text" id="order_name" size="20" /></td>
				        		</tr>
				        		<tr><td>&nbsp;</td></tr>
				        		<tr>
									<td align="right"><h3>原价(元)</h3></td>
									<td align="left"><input class="input-sm" type="text" id="order_money" size="20" /></td>
								</tr>
								<tr><td>&nbsp;</td></tr>
				        		<tr><td colspan="2" style="text-align:center;color:red">&nbsp;注：将自动对应会员折扣&nbsp;</td></tr>
				        		<tr><td>&nbsp;</td></tr>
				        		<tr>
									<td colspan="2" align="center">
										<input class="btn btn-primary" style="width: 80%;" type="submit" name="submit" value=" 确 定 " />
									</td>
				        		</tr>
				        	</table>
			           </form>
				     </div>
				</div>
				<div id="win_charge" class="theme-popover">
				     <div class="theme-poptit">
				          <a onclick="closeWindow('win_charge')" title="关闭" class="close">×</a>
				          <h4>充值</h4>
				     </div>
				     <div class="theme-popbod dform">
				     	<form class="theme-signin" action="Charge" method="post">
				     		<input type="hidden" name="vipNo" id="charge_vip_no"/>
				        	<table width="50%" height="100%" cellspacing="20" style="margin-top: 50px">
				        		<tr>
				        			<td><h3>金额</h3></td>
				        			<td align="right"><input class="input-sm" type="text" name="name" size="20" /></td>
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
				<div id="popmask" class="theme-popover-mask"></div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span12">
				<table class="table table-hover table-bordered">
					<thead id="tabletitle"></thead>
					<tbody id="tablecontent"></tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-lg-6" style="text-align:left">
				<ul class="nav nav-pills">
				   <li id="btnVipManage" class="active"><a onclick="enableVipManage(1, '')">会员管理</a></li>
				   <li id="btnOrderList"><a onclick="enableOrderList(1, '')">消费记录</a></li>
				</ul>
			</div>
			<div class="col-lg-6" style="text-align:right">
				<ul class="pagination" id="pager">
				  <li><a href="#">&laquo;</a></li>
				  <li><a href="#">1</a></li>
				  <li><a href="#">2</a></li>
				  <li><a href="#">&raquo;</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
