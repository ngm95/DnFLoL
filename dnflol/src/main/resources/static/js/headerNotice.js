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
						
						var deny = new Array();
						deny[0] = "<form action=\"/lol/denyApply\" method=\"post\">";
						deny[1] = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						deny[2] = "<input name=\"lapplyId\" type=\"hidden\" value=\"" + lapplyId + "\" /> ";
						deny[3] = "<input name=\"lgroupId\" type=\"hidden\" value=\"" + lgroupId + "\" /> ";
						deny[4] = "<button type=\"submit\" class=\"btn btn-danger\" style=\"float:left\">거절</button></form>";
						
						var accept = new Array();
						accept[0] = "<form action=\"/lol/acceptApply\" method=\"post\">";
						accept[1] = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						accept[2] = "<input name=\"lapplyId\" type=\"hidden\" value=\"" + lapplyId + "\" /> ";
						accept[3] = "<input name=\"lgroupId\" type=\"hidden\" value=\"" + lgroupId + "\" /> ";
						accept[4] = "<button type=\"submit\" class=\"btn btn-success\" style=\"float:right\">수락</button></form>";
						
						var header = "<li><b>" + lcharName + "</b>이/가 <b>" + lgroupName + "</b>에 신청했습니다.<br /> ";
						var footer = "</li><li><hr class=\"dropdown-divider\" style=\"margin-top: 45px\"></li>";
						
						var denyHtml = "";
						var acceptHtml = "";
						for (var i = 0; i <= 4; i++) {
							denyHtml += deny[i];
							acceptHtml += accept[i];
						}
						$("#lolNotice").append(header+denyHtml+acceptHtml+footer);
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
				if (data.length > 0) {
					for (var i in data) {
						var dapplyId = data[i].dapplyId;
						var dgroupId = data[i].dgroupId;
						var dcname = data[i].dcname;
						var dgroupName = data[i].dgroupName;
						var csrf_token = $("meta[name='csrf_token']").attr("content");
						var csrf_parameterName = $("meta[name='csrf_parameterName']").attr("content");
						
						var deny = new Array();
						deny[0] = "<form action=\"/dnf/denyApply\" method=\"post\">";
						deny[1] = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						deny[2] = "<input name=\"dapplyId\" type=\"hidden\" value=\"" + dapplyId + "\" /> ";
						deny[3] = "<input name=\"dgroupId\" type=\"hidden\" value=\"" + dgroupId + "\" /> ";
						deny[4] = "<button type=\"submit\" class=\"btn btn-danger\" style=\"float:left\">거절</button></form>";
						
						var accept = new Array();
						accept[0] = "<form action=\"/dnf/acceptApply\" method=\"post\">";
						accept[1] = "<input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /> ";
						accept[2] = "<input name=\"dapplyId\" type=\"hidden\" value=\"" + dapplyId + "\" /> ";
						accept[3] = "<input name=\"dgroupId\" type=\"hidden\" value=\"" + dgroupId + "\" /> ";
						accept[4] = "<button type=\"submit\" class=\"btn btn-success\" style=\"float:right\">수락</button></form>";
						
						var header = "<li><b>" + dcname + "</b>이/가 <b>" + dgroupName + "</b>에 신청했습니다.<br /> ";
						var footer = "</li><li><hr class=\"dropdown-divider\" style=\"margin-top: 45px\"></li>";
						
						var denyHtml = "";
						var acceptHtml = "";
						for (var i = 0; i <= 4; i++) {
							denyHtml += deny[i];
							acceptHtml += accept[i];
						}
						$("#dnfNotice").append(header+denyHtml+acceptHtml+footer);
					}
				} 
				else {
					var myNotice = "<li>신청이 없습니다.</li>";
					$("#dnfNotice").append(myNotice);
				}
		},
		error : function() {
			$('#dnfNotice').empty();
			var myNotice = "<li>신청 리스트 갱신 중 오류 발생</li>";
			$("#dnfNotice").append(myNotice);
		}
	});
	
	
});