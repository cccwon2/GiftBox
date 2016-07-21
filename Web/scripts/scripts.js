

var windowHeight = 0;
var phoneScreenIdx = 0;


$(function(){
    
    $(window).bind('resize', windowResizeHandler);
    $(window).bind('scroll', scrollHandler);
    
    windowResizeHandler();
    scrollHandler();
    
    setInterval(function(){
        phoneScreenIdx++;
        phoneScreenChange(phoneScreenIdx);
    }, 5000);
    phoneScreenChange(phoneScreenIdx);
    
    setTimeout(function(){
        $('body').addClass('show');
    });
    
});


function phoneScreenChange(idx){
    if(idx >= $('.screen-wrap li').length) phoneScreenIdx = 0;
    
    
    $('.screen-wrap li').eq(phoneScreenIdx).addClass('on');
    setTimeout(function(){
        $.each($('.screen-wrap li'), function(idx, val){
            if(idx != phoneScreenIdx){
                $(val).removeClass('on');
            };
        });
    }, 500);
};

function windowResizeHandler(){
    
    if($(window).width() >= 768){
        windowHeight = $(window).height();
        if(windowHeight > 576){
            $('.section-1').height(windowHeight);
        }else{
            $('.section-1').height(576);
        }
    }else{
        $('.section-1').removeAttr('style');
    }

};

function scrollHandler(){
    var scrollPos = $(window).scrollTop();
    var windowHeight = $(window).height();
    
    if( scrollPos >= (windowHeight*0.65) ){
        if(!$('#header').hasClass('show')){
            $('#header').addClass('show');
        }
    }else{
        $('#header').removeClass('show');
    };
};

















