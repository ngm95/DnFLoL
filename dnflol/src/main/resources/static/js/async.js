$(document).ready(function() {
	
	$.ajax({
		url: "/myLoLNotice",
		dataType: "json",
		contentType:"application/json;charset=UTF-8",
		success: function(data) {
			$('#lolNotice').empty();
				if (data.length > 0) {
					for (var i in data) {
						var lapplyId = data[i].lapplyId;
						var lgroupId = data[i].lgroupId;
						var lcharName = data[i].lcharName;
						var lgroupName = data[i].lgroupName;
						var csrf_token = $("meta[name='csrf_token']").attr("content");
						var csrf_parameterName = $("meta[name='csrf_parameterName']").attr("content");
						
						var header = "<li><b>" + lcharName + "</b>이/가 <b>" + lgroupName + "</b>에 신청했습니다.<br /> ";
						
						var deny1 = "<form action=\"/lol/denyApply\" method=\"post\">";
						var deny2 = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						var deny3 = "<input name=\"lapplyId\" type=\"hidden\" value=\"" + lapplyId + "\" /> ";
						var deny4 = "<input name=\"lgroupId\" type=\"hidden\" value=\"" + lgroupId + "\" /> ";
						var deny5 = "<button type=\"submit\" class=\"btn btn-danger\" style=\"float:left\">거절</button></form>";
						
						var accept1 = "<form action=\"/lol/acceptApply\" method=\"post\">";
						var accept2 = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						var accept3 = "<input name=\"lapplyId\" type=\"hidden\" value=\"" + lapplyId + "\" /> ";
						var accept4 = "<input name=\"lgroupId\" type=\"hidden\" value=\"" + lgroupId + "\" /> ";
						var accept5 = "<button type=\"submit\" class=\"btn btn-success\" style=\"float:right\">수락</button></form>";
						
						var footer = "</li><li><hr class=\"dropdown-divider\" style=\"margin-top: 45px\"></li>";
						$("#lolNotice").append(header+deny1+deny2+deny3+deny4+deny5+accept1+accept2+accept3+accept4+accept5+footer);
					}
				} 
				else {
					var myNotice = "<li>신청이 없습니다.</li>";
					$("#lolNotice").append(myNotice);
				}
		},
		error : function() {
			$('#lolNotice').empty();
			var myNotice = "<li>신청 리스트 갱신 중 오류 발생</li>";
			$("#lolNotice").append(myNotice);
		}
	});
	
	$.ajax({
		url: "/myDnFNotice",
		dataType: "json",
		contentType:"application/json;charset=UTF-8",
		success: function(data) {
			$('#dnfNotice').empty();
			if (data == null) {
				var myNotice = "<li>신청이 없습니다.</li>";
				$("#dnfNotice").append(myNotice);
			}
			else {
				if (data.length > 0) {
					for (var i in data) {
						var dapplyId = data[i].dapplyId;
						var dgroupId = data[i].dgroupId;
						var dcname = data[i].dcname;
						var dgroupName = data[i].dgroupName;
						var csrf_token = $("meta[name='csrf_token']").attr("content");
						var csrf_parameterName = $("meta[name='csrf_parameterName']").attr("content");
						
						var header = "<li><b>" + dcname + "</b>이/가 <b>" + dgroupName + "</b>에 신청했습니다.<br /> ";
						
						var deny1 = "<form action=\"/dnf/denyApply\" method=\"post\">";
						var deny2 = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						var deny3 = "<input name=\"dapplyId\" type=\"hidden\" value=\"" + dapplyId + "\" /> ";
						var deny4 = "<input name=\"dgroupId\" type=\"hidden\" value=\"" + dgroupId + "\" /> ";
						var deny5 = "<button type=\"submit\" class=\"btn btn-danger\" style=\"float:left\">거절</button></form>";
						
						var accept1 = "<form action=\"/dnf/acceptApply\" method=\"post\">";
						var accept2 = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						var accept3 = "<input name=\"dapplyId\" type=\"hidden\" value=\"" + dapplyId + "\" /> ";
						var accept4 = "<input name=\"dgroupId\" type=\"hidden\" value=\"" + dgroupId + "\" /> ";
						var accept5 = "<button type=\"submit\" class=\"btn btn-success\" style=\"float:right\">수락</button></form>";
						
						var footer = "</li><li><hr class=\"dropdown-divider\" style=\"margin-top: 45px\"></li>";
						$("#dnfNotice").append(header+deny1+deny2+deny3+deny4+deny5+accept1+accept2+accept3+accept4+accept5+footer);
					}
				} 
				else {
					var myNotice = "<li>신청이 없습니다.</li>";
					$("#dnfNotice").append(myNotice);
				}
			}
		},
		error : function() {
			$('#dnfNotice').empty();
			var myNotice = "<li>신청 리스트 갱신 중 오류 발생</li>";
			$("#dnfNotice").append(myNotice);
		}
	});
	
	
});