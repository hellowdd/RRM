$().ready(function(){
 $(".all-down").click(function(){
    /*清除全选*/
    if($(".all-down label").hasClass("doshow")){
      $(".all-down label").removeClass("doshow");
      $(".more-imgbox label").removeClass('doshow'); 
    }
    /*全选*/
    else{
       $(".all-down label").addClass("doshow"); 
       $(".more-imgbox label").addClass('doshow'); 
    }
   
 });
 $(".imgList label").click(function(){
    if($(this).hasClass("doshow")){
        $(this).removeClass("doshow");
    }
    else{
        $(this).addClass("doshow");
    }
 })
});