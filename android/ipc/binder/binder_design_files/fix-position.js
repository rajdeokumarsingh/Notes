var disappearTop=2000;
var sidebarOffset=1088;
/*var bottomTemp;
var sideBarTemp;
var sidebar_to_bottom;
var sideBarPos;
var bottomPos;*/
$(document).ready(function(){
	//bottomTemp=$('#bottom');
	//sideBarTemp=$('#sidebar');
	//bottomPos = bottomTemp.position().top;
	var content_wrap = $("#content_wrap");
	var po = content_wrap.offset();
	var tt = po.left+sidebarOffset;//���div���ұ�λ��
	change_po(tt);
//	$("#sidebar").css("display","none");
//alert(navigator.userAgent);
});
$(window).scroll( function() {
	var content_wrap = $("#content_wrap");
	var po = content_wrap.offset();
	var tt = po.left+sidebarOffset;//���div���ұ�λ��
	change_po(tt);
});
$(window).resize(function(){
	var content_wrap = $("#content_wrap");
	var po = content_wrap.offset();
	var tt = po.left+sidebarOffset;//���div���ұ�λ��
	change_po(tt);
});
function change_po(ll){
	//$("#sidebar").css("left",l);
	//sideBarPos= sideBarTemp.position().top;
	//sidebar_to_bottom=bottomPos-sideBarPos;
	//if(sidebar_to_bottom>200 && bottomPos>sideBarPos)
		//$("#sidebar").css({"display":"block", "left":ll, "bottom": 100});
	//else
        //$("#sidebar").css({"display":"block", "left":ll, "bottom": 150});
		//$("#sidebar").css({"display":"block", "left": 1090, "bottom": 250});
    $("#sidebar").css({"display":"block",  "bottom": 215});
	
	var a = document.documentElement.scrollTop;
	
	if(isie6()){
		$("#sidebar").css({"display":"block",  "bottom": 215});
	}
	//var b =$("body").scrollTop();
//	alert(a);
	
	var webro = userBrowser();
	if(webro == "chrome") {
		var a =$("body").scrollTop();
	}
	if(a<disappearTop){
		$("#sidebar").css("display","none");
	}
}
function userBrowser(){//����Ƿ���chrome
    var browserName=navigator.userAgent.toLowerCase();
    if(/chrome/i.test(browserName) && /webkit/i.test(browserName) && /mozilla/i.test(browserName)){
		return "chrome";
	}
}
function isie6() {//����Ƿ���6
    if ($.browser.msie) {
        if ($.browser.version == "6.0") return "ie6";
    }
    return false;
}

