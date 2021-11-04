$(document).ready(function() {
//	$('#refresh').click(function() {
//		var url = "/user/myLoLNotice"
//		$.getJSON(url, function(data) {
//			$('#lolNotice').empty();
//			$.each(data, function(index, value) {
//				var myNotice = "<li><b>" + value.lcharName + "</b> -> " + value.lgroupName + "<br/>";
//					myNotice += "<form:form modelAttribute=\"applyForm\" action=\"/lol/denyApply\" method=\"post\"><form:input path=\"lapplyId\" value=\"" + lapplyId + "\"/>";
//					myNotice += "<form:input path=\"lgroupId\" value=\"" + value.lgroupId + "\"/>";
//					myNotice += "<form:button class=\"btn btn-sm btn-danger\" type=\"submit\" style=\"float:left\">거절</form:button></form:form>";
//					myNotice += "<form:form modelAttribute=\"applyForm\" action=\"/lol/denyApply\" method=\"post\"><form:input path=\"lapplyId\" value=\"" + lapplyId + "\"/>";
//					myNotice += "<form:input path=\"lgroupId\" value=\"" + value.lgroupId + "\"/>";
//					myNotice += "<form:button class=\"btn btn-sm btn-success\" type=\"submit\" style=\"float:left\">수락</form:button></form:form></li>";
//					myNotice += "<li><hr class=\"dropdown-divider\" style=\"margin-top:45px\"></li>";
//				$('#lolNotice').append(myNotice);
//			})
//		})
//		
//		url = "/user/myDnFNotice"
//	});
	
	$.ajax({
		url: "/user/myLoLNotice",
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
					
					var notice1 = "<li><b>" + lcharName + "</b> -> " + lgroupName + "<br /> ";
					var	notice2 = "<button class=\"btn btn-sm btn-danger\" type=\"button\" style=\"float:left\" onclick=\"location.href='lol/denyApply/" + lapplyId + "&" + lgroupId + "'\">거절</button>";
					var	notice3 = "<button class=\"btn btn-sm btn-success\" type=\"button\" style=\"float:right\" onclick=\"location.href='lol/acceptApply/" + lapplyId + "&" + lgroupId + "'\">수락</button>";
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
			var myNotice = "<li>리스트 갱신 중 오류가 발생했습니다.</li>";
			$("#lolNotice").append(myNotice);
		}
	});
});