$().ready(function() {
    $(".more-down label").click(function() {
        /*清除全选*/
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
            $(".text-item label").removeClass('doshow');
        }
        /*全选*/
        else {
            $(this).addClass("doshow");
            $(".text-item label").addClass('doshow');
        }

    });
    $(".text-item label").click(function(event) {

        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
        } else {
            $(this).addClass("doshow");
        }
        event.stopPropagation();
    })
});