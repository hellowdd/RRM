$().ready(function(){
 $(".all-down label").click(function(){
    /*清除全选*/
    if($(this).hasClass("doshow")){
      $(this).removeClass("doshow");
      $(".more-imgbox label").removeClass('doshow'); 
    }
    /*全选*/
    else{
       $(this).addClass("doshow"); 
       $(".more-imgbox label").addClass('doshow'); 
    }
   
 });
 $(".imgbox label").click(function(event){
   
    if($(this).hasClass("doshow")){
        $(this).removeClass("doshow");
    }
    else{
        $(this).addClass("doshow");
    }
    event.stopPropagation();
 });
 search(1);
});
//开始获取图片的列表
var str = {

};

function search(curr) {
    str = {
        pageNum: curr,
        pageSize: 20,
        resourceType: '1'
    }
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: 'http://192.168.200.197:8080/rrm/manager/resource/queryResource',
        dataType: 'json',
        cache: false,
        data: JSON.stringify(str),
        success: function(res) {
            $(".imgbox").children().remove();
            var reslen = res.page.list.length;
            console.log(res.page);
            if (reslen > 0) {
                var imglist = res.page.list;
                var imgcon = '';
                for (var i = 0; i < reslen; i++) {
                    imgcon += '<li class="img-item"><a href="../image/upload.jpg" class="test" title="'+imglist[i].resourceName+'" data-fancybox-group="button"><div class="img-item-box"><img src="../image/test.png"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft"><label for="down-item" class=" down-item"></label><input type="checkbox" id="down-item">'+imglist[i].taskYear+'</p><a href="" class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a><a href="" class="img-down fright"><i class="fa fa-trash-o" aria-hidden="true"></i></a><a href="" class="img-down fright"><i class="fa fa-download" aria-hidden="true"></i></a></div></li>';
                }
                $(".imgbox").append(imgcon);
                laypage({
                    cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.page.total / 20), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#428bca',
                    jump: function(obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            search(obj.curr);
                        }
                    }
                });
            } else {
                layer.alert("没有搜索到合适");
            }
        },

        error: function() {
            alert("获取驾校列表失败");
        }
    })
}

//根据条件进行搜索
var find = {

};

function found(obj) {
    var count = 1;
    var sevalue = '';
    find = {
        pageNum: count,
        pageSize: 20,
        resourceType: '1'
    }
    sevalue = $("input[name='search']").val();
    if (sevalue != '') {
        var key = $(".sea-sele p").text();
        if (key == '关键字') {
            find.resourceKey = sevalue;
        } else if (key == '资源模块') {
            find.resourceModule = sevalue;
        } else if (key == '地点') {
            find.resourcePlace= sevalue;
        } else if (key == '专项名称') {
            find.taskName = sevalue;
        }
        //根据搜索条件调接口
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: 'http://192.168.200.197:8080/rrm/manager/resource/queryResource',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(find),
            success: function(res) {
                $(".imgbox").children().remove();
                var reslen = res.page.list.length;
                console.log(res.page);
                if (reslen > 0) {
                    var imglist = res.page.list;
                    var imgcon = '';
                    for (var i = 0; i < reslen; i++) {
                        imgcon += '<li class="img-item"><a href="../image/upload.jpg" class="test" title="'+imglist[i].resourceName+'" data-fancybox-group="button"><div class="img-item-box"><img src="../image/test.png"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft"><label for="down-item" class=" down-item"></label><input type="checkbox" id="down-item">'+imglist[i].taskYear+'</p><a href="" class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a><a href="" class="img-down fright"><i class="fa fa-trash-o" aria-hidden="true"></i></a><a href="" class="img-down fright"><i class="fa fa-download" aria-hidden="true"></i></a></div></li>';
                    }
                    $(".imgbox").append(imgcon);
                    laypage({
                        cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.page.total / 20), //通过后台拿到的总页数
                        curr: count || 1, //当前页
                        skin: '#428bca',
                        jump: function(obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                found(obj.curr);
                            }
                        }
                    });
                } else {
                    layer.alert("没有搜索到合适");
                }
            },

            error: function() {
                alert("获取驾校列表失败");
            }
        })
    }
}