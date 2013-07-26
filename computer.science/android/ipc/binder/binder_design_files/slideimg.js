// JavaScript Document
var scrollWrapW,myScroll,scrollWrapWEbook;
var rollDirection = 0; //0为左 1为右
var show_index=0;
var speed = 4000;
function __scrollBox(){
    scrollWrapW = -($('#show_wrap li').eq(0).outerWidth(true)) + 'px';
    scrollWrapWEbook = -($('.e_content li').eq(0).outerWidth(true)) + 'px';
  $('#show_wrap').animate({left:scrollWrapW}, {
    	duration:500,
    	complete:function(){
			show_index=show_index+1;
			show_index=show_index%4;
			$('.slider_content_wrap div').css('display','none');
	    	$('.slider_content_wrap div:eq(' + show_index + ')').css('display','block');
			$('#idDotNum li').removeClass("on");
			$('#idDotNum li:eq('+show_index+')').addClass("on");
 	 		$('#show_wrap').append($('#show_wrap li:first'));
  			$('#show_wrap').css("left","0");
  		}
   });
    $('.e_content ul').animate({left:scrollWrapWEbook}, {
                duration:1000,
                complete:function(){
                $('.e_content ul').append($('.e_content ul li:first'));
                  $('.e_content ul').css("left","0");
            }
        });

}
$(document).ready(function(){
	$('.slider_content_wrap div').css('display','none');
	$('.slider_content_wrap div:eq(0)').css('display','block');
	$('#idDotNum li:eq(0)').addClass("on");
	myScroll = setInterval(__scrollBox,speed);
	/*$('#show_wrap').hover(function(){
	  clearInterval(myScroll);        
	},function(){
	  myScroll = setInterval(__scrollBox,speed); 
	});*/
	
	
	$('#idDotNum li').click(function(){
		clearInterval(myScroll); 
		$('#idDotNum li').removeClass("on");
		$(this).addClass("on");
		var now=($('#idDotNum li').index($(".on")));
		var tmp,tmp_now;
		tmp_now=now;
		if(now <show_index)
			now=now+4;
		tmp=now-show_index;
		if(tmp==1)
			scrollWrapW = -($('#show_wrap li').eq(0).outerWidth(true)) + 'px';
		else if(tmp==2)//move 2 step
			scrollWrapW = -($('#show_wrap li').eq(0).outerWidth(true))*2 + 'px';
		else if(tmp==3)
			scrollWrapW = -($('#show_wrap li').eq(0).outerWidth(true))*2 + 'px';
		else
			scrollWrapW =0;
		if(tmp < 3)
		{
			$('#show_wrap').animate({left:scrollWrapW}, {
				duration:500,
				complete:function()
				{
					show_index=tmp_now;
					if(tmp!=0)
					{
						//if(tmp==1)
						$('#show_wrap').append($('#show_wrap li:first'));
						if(tmp==2)
							$('#show_wrap').append($('#show_wrap li:first'));
						$('#show_wrap').css("left","0");
					}
				}
			});
		}
		else if(tmp ==3)
		{
			$('#show_wrap').animate({left:scrollWrapW}, {
				duration:20,
				complete:function()
				{
					show_index=tmp_now;
					$('#show_wrap').prepend($('#show_wrap li:last'));
					$('#show_wrap').css("left",'0');
				}
				});
		}
		$('.slider_content_wrap div').css('display','none');
	    $('.slider_content_wrap div:eq(' + tmp_now + ')').css('display','block');
		myScroll = setInterval(__scrollBox,speed);
	});
});
