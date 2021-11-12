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
						
						var notice1 = "<li><b>" + lcharName + "</b>가 <b>" + lgroupName + "</b>에 신청했습니다.<br /> ";
						var	notice2 = "<button class=\"btn btn-sm btn-danger\" type=\"button\" style=\"float:left\" onclick=\"location.href='/lol/denyApply/" + lapplyId + "&" + lgroupId + "'\">거절</button>";
						var	notice3 = "<button class=\"btn btn-sm btn-success\" type=\"button\" style=\"float:right\" onclick=\"location.href='/lol/acceptApply/" + lapplyId + "&" + lgroupId + "'\">수락</button>";
						var notice4 = "</li><li><hr class=\"dropdown-divider\" style=\"margin-top: 45px\"></li>";
						$("#lolNotice").append(notice1+notice2+notice3+notice4);
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
						
						var notice1 = "<li><b>" + dcname + "</b>가 <b>" + dgroupName + "</b>에 신청했습니다.<br /> ";
						var	notice2 = "<button class=\"btn btn-sm btn-danger\" type=\"button\" style=\"float:left\" onclick=\"location.href='/dnf/denyApply/" + dapplyId + "&" + dgroupId + "'\">거절</button>";
						var	notice3 = "<button class=\"btn btn-sm btn-success\" type=\"button\" style=\"float:right\" onclick=\"location.href='/dnf/acceptApply/" + dapplyId + "&" + dgroupId + "'\">수락</button>";
						var notice4 = "</li><li><hr class=\"dropdown-divider\" style=\"margin-top: 45px\"></li>";
						$("#dnfNotice").append(notice1+notice2+notice3+notice4);
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