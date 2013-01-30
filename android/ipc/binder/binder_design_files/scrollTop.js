$(function(){
	$('#gotoTop').click(function(){
		$('html,body').animate(
			{scrollTop: '0px'}, 800
		);
		return false;
	});
})
