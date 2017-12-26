$().ready(function() {
    //角色
    var flag = false;
    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/RRM/manager/loginAction/getUserInffo',
        cache: false,
        dataType: 'json',
        async: false,
        data: {},
        success: function(res) {
            //console.log(res);
            var role = res.data.userRoles;
            for (var i = 0; i < role.length; i++) {
                if (role[i].roleCode == '1') {
                    localStorage.setItem("role", '1');
                    break;
                } else {
                    localStorage.setItem("role", role[i].roleCode);
                }
            }
            localStorage.setItem("user", res.data.userName);
            $(".username").html(localStorage.getItem("user"));
        },
        error: function() {
            alert("服务器正忙,请稍后再试");
        }
    })
    //检索
    $(".tit-sea").mouseover(function() {
        $(".tit-sea").addClass("tit-sea-hover");
        $(".retrieval").show();
    });
    $(".retrieval").mouseleave(function() {
        $(".tit-sea").removeClass("tit-sea-hover");
        $(".retrieval").hide();
    })
    //退出登录
    $(".userlogo").mouseover(function() {
        $(".exit").show();
    })
    $(".exit").mouseleave(function() {
        $(".exit").hide();
    })
    //搜索选择
    $(".search-but").click(function() {
        $(".search-item").show();
    });
    $(".search-item").mouseleave(function() {
        $(".search-item").hide();
    })
    //条件搜索
    $("input[name='search']").focus(function(event) {

        $(".search-cond").show();
        event.stopPropagation();
    });
    $("input[name='search']").blur(function() {
        /*$(".search-cond").hide();*/
    });
    //高级筛选按钮的切换
    $(".sea-filter").mouseover(function() {
        $(this).attr("src", "/RRM/image/ffilter.png");
    });
    $(".sea-filter").mouseleave(function() {
        $(this).attr("src", "/RRM/image/filter.png");
    });
    var imglist = []; //轮播合计
    //视频合集
    var resourceType = {
        resourceType: '0'
    }
    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/RRM/manager/resource/queryResourceSize',
        dataType: 'json',
        async: false,
        cache: false,
        data: JSON.stringify(resourceType),
        success: function(res) {
            imglist.push({
                resourceType: resourceType,
                resourceSize: res.data.resourceSize,
                updateSize: res.data.updateSize
            });
             
            localStorage.setItem('cas',res.data.sessionid);
        },

        error: function() {
            layer.alert("服务器正忙,请稍后重试");
        }
    })
    //图片合集
    var resourceType = {
        resourceType: '1'
    }
    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/RRM/manager/resource/queryResourceSize',
        dataType: 'json',
        async: false,
        cache: false,
        data: JSON.stringify(resourceType),
        success: function(res) {
            imglist.push({
                resourceType: resourceType,
                resourceSize: res.data.resourceSize,
                updateSize: res.data.updateSize
            });
        },

        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }
    })
    //文本合集
    var resourceType = {
        resourceType: '2'
    }
    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/RRM/manager/resource/queryResourceSize',
        dataType: 'json',
        async: false,
        cache: false,
        data: JSON.stringify(resourceType),
        success: function(res) {
            imglist.push({
                resourceType: resourceType,
                resourceSize: res.data.resourceSize,
                updateSize: res.data.updateSize
            });
        },

        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }
    });
    //图片轮播
    var imgcon = '';
    for (var i = 0; i < imglist.length; i++) {
        var retype = '';
        if (imglist[i].resourceType.resourceType == '0') {
            retype = '视频'
            var tip = '个';
        } else if (imglist[i].resourceType.resourceType == '1') {
            retype = '图片'
            var tip = '张';
        }
        if (imglist[i].resourceType.resourceType == '2') {
            retype = '文本'
            var tip = '篇';
        }
        imgcon += '<div class="swiper-slide"><p>' + retype + '总计</p><h4>' + imglist[i].resourceSize + '</h4><span>昨日更新' + imglist[i].updateSize + '' + tip + '</span></div>';
    }
    $(".swiper-wrapper").append(imgcon);
    //调用图片轮播
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        paginationClickable: true,
        spaceBetween: 30,
        centeredSlides: true,
        autoplay: 2500,
        autoplayDisableOnInteraction: false
    });

    //console.log(imglist);
});

/*类型的搜索选择确定*/
function kindchose(obj) {
    var kind = $(obj).text();
    $(".search-but span").html(kind);
    $(".search-item").hide();
}

/*条件筛选*/
function condition(obj) {
    var cond = $(obj).text();
    $("input[name='search']").val(cond + ' :');
    $(".search-cond").hide();
}

function upload() {
    window.location.href = "/RRM/manager/page/upload.html";
}

//搜索
function check(obj) {
    var seacond = '';
    var seacondval = '';
    var restype = $(".search-but span").html();
    var rescond = $('input[name=search]').val();
    if (rescond == '') {
        window.location.href = "../img.html";
    }
    if (rescond.indexOf("关键字 :") >= 0) {
        seacond = 'a';
        seacondval = rescond.substr(5);
        if (seacondval == '') {
            layer.alert("请输入搜索条件");
        }
    } else if (rescond.indexOf("地点 :") >= 0) {
        seacond = 'b';
        seacondval = rescond.substr(4);
        if (seacondval == '') {
            layer.alert("请输入搜索条件");
        }
    } else if (rescond.indexOf("专项名称 :") >= 0) {
        seacond = 'c';
        seacondval = rescond.substr(6);
        if (seacondval == '') {
            layer.alert("请输入搜索条件");
        }
    } else if (rescond.indexOf("资源模块 :") >= 0) {
        seacond = 'd';
        seacondval = rescond.substr(6);
        if (seacondval == '') {
            layer.alert("请输入搜索条件");
        }
    } else {
        seacond = 'e';
        seacondval = rescond;
    }
    if (restype == '视频') {
        var teval = {
            seacond: seacond,
            seaval: seacondval
        };
        var teval = JSON.stringify(teval);
        teval = encodeURI(encodeURI(teval));
        window.location.href = '/RRM/manager/page/video.html?cond=' + teval + '';
    } else if (restype == '图片') {
        var teval = {
            seacond: seacond,
            seaval: seacondval
        };
        var teval = JSON.stringify(teval);
        teval = encodeURI(encodeURI(teval));
        window.location.href = '/RRM/manager/page/img.html?cond=' + teval + '';

    } else if (restype == '文本') {

        var teval = {
            seacond: seacond,
            seaval: seacondval
        };
        var teval = JSON.stringify(teval);
        teval = encodeURI(encodeURI(teval));
        window.location.href = '/RRM/manager/page/text.html?cond=' + teval + '';
    }


}

//退出登录
function exituser() {
    $.ajax({
        contentType: "application/json",
        type: 'POST',
        url: '/RRM/manager/loginAction/loginCasOut',
        dataType: 'json',
        async: false,
        cache: false,
        data: {},
        success: function(res) {
            if (res.success == true) {
                localStorage.removeItem('role');
                localStorage.removeItem("user");
                var cas = res.data.casServerUrlPrefix;
                var scheme = res.data.scheme;
                var serverName = res.data.serverName;
                var serverPort = res.data.serverPort;
                var contextPath = res.data.contextPath;
                //console.log(''+cas+'/logout?service='+''+scheme+'://'+serverName+':'+serverPort+''+contextPath+'/manager/loginAction/loginCas');
                window.location.href = cas + '/logout?service=' + '' + scheme + '://' + serverName + ':' + serverPort + '' + contextPath + '/manager/loginAction/loginCas?ran=' + Math.random();
            }
        },

        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }
    });
}

//高级筛选
function filters(){
    window.location.href = '/RRM/manager/page/filter.html';
}