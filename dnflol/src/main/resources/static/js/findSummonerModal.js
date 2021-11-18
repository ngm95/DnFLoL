$(document).ready(function() {
	$('#findBtn').click(function() {
		$('#resultBody').empty();
		
		var csrf_token = $("meta[name='csrf_token']").attr("content");
		var csrf_parameterName = $("meta[name='csrf_parameterName']").attr("content");
		var encryptedSummonerId = "";
		var summonerName = $('#summonerName').val();
		
		$.ajax({
			url : "/lol/findSummoner/summonerDto/"+summonerName,
			method : "get",
			dataType : "json",
			contentType:"application/json;charset=UTF-8",
			success : function(summoner) {
				if (summoner) {
					var name = summoner.name;
					var summonerLevel = summoner.summonerLevel;
					var profileIconId = summoner.profileIconId;
					input = "<div class=\"d-flex flex-column\"><div class=\"d-flex justify-content-center\"><img src=\"/lol/img/profileicon/" + profileIconId +".png\" width=\"140px\"></div><br/><div class=\"d-flex justify-content-between\"><div class=\"d-flex flex-column\"><div class=\"d-flex flex-column\">" + "아이디 : " + name + ", 레벨 : " + summonerLevel + "</div><div class=\"d-flex flex-column\" id=\"tierInfo\"></div></div><div class=\"d-flex flex-row\"><form action=\"/lol/addSummoner\" method=\"post\"><input type=\"hidden\" name=\"summonerName\" value=\"" + name + "\"/><input name=\"" + csrf_parameterName + "\" type=\"hidden\" value=\"" + csrf_token + "\" /><button type=\"submit\" class=\"btn btn-success\" style=\"float:right; margin-top:7px; margin-left:10px\">계정과 연동하기</button></form></div></div></div>";

					$('#resultBody').append(input);
					
					encryptedSummonerId = summoner.id;
					$.ajax({
						url : "/lol/findSummoner/leagueDto/"+encryptedSummonerId,
						method : "get",
						dataType : "json",
						contentType:"application/json;charset=UTF-8",
						success : function(league) {
							if (league) {
								$('#tierInfo').append("티어 : " + league.tier + " " + league.rank + " " + league.leaguePoints + "LP");
							} else {
								$('#tierInfo').append("티어 : unranked");
							}
						},
						error : function() {
							$('#tierInfo').append("티어 : unranked");
						}
					});
				} else {
					$('#resultBody').append("<h4>해당하는 계정이 없습니다.</h4>");
				}
			},
			error : function() {
				$('#resultBody').append("<h4>계정 검색에서 오류가 발생했습니다.</h4>");
			}
		});
	});
});