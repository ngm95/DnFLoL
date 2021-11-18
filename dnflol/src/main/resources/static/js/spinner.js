$(document).ready(function() {
	$('.btn-info').each(function() {
		$(this).click(function() {
			$('#spinner').show();
		})
	});
});