//权限判断
var role = localStorage.getItem("role");
$().ready(function() {
    $(".all-down").click(function() {
        $(".imgtab").children().remove();


    });
    $(".imgList label").click(function() {
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
        } else {
            $(this).addClass("doshow");
        }
    })
    //最开始获取视频
    search(1)
});
//查看视频列表
var str = {};

var hrefval = {};
//解析地址参数
function getQueryString(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
}
var condval = decodeURI(getQueryString('cond'));
condval = JSON.parse(condval);
if (condval != null) {
    hashcond = condval.seacond; //搜索条件
    hashval = condval.seaval; //搜索的值
    if (hashcond != null && hashval != null) {
        //关键字
        if (hashcond == 'a') {
            str.resourceKey = hashval
        }
        //地点
        else if (hashcond == 'b') {
            str.resourcePlace = hashval
        }
        //专项名称
        else if (hashcond == 'c') {
            str.taskName = hashval
        }
        //资源模块
        else if (hashcond == 'd') {
            str.resourceModule = hashval
        }
        //资源名称
        else if (hashcond == 'e') {
            str.resourceName = hashval
        }
    }
}
var videoval = decodeURI(getQueryString('value'));
videoval = JSON.parse(videoval);

if (videoval != null) {
    str = videoval;
}
var videourl = ''; //视频地址
var videoname = ''; //视频名字
var videotype = ''; //视频格式
var videoid = '';//视频的id
var videoimg = '';
var videoindex = '';
function checkvideo(obj) {
    videoid = $(obj).parent('li').attr("data-id");//视频的id
    //alert(videoid);
    videoindex = parseInt($(obj).parents('li').index());
    videourl = $(obj).attr("vurl");
    videoname = $(obj).attr("data-name");; //视频名字
    videotype = $(obj).attr("data-type");; //视频格式

    layer.open({
        title: false,
        type: 2,
        area: ['888px', '500px'],
        content: ['/RRM/video.htm', 'no'],
        success: function(layero, index) {



        }
    });
}
//编辑信息 
var dataid = '';
var id = {};
var paindex = '';
function edit(obj) {
    dataid = parseInt($(obj).parents('.img-item').attr("data-id"));
     paindex = parseInt($(obj).parents('.img-item').index());
    //console.log(dataid);
    id = {
        id: dataid,
        resourceType: '0'
    }

    layer.open({
        title: false,
        type: 2,
        closeBtn: 0,
        area: ['870px', '490px'],
        content: ['t.html', 'no'],
        success: function(layero, index) {
            /*关闭弹窗*/
        }
    });
}
//查看视频


function search(curr) {
    //设置参数
    hrefval = str;
    str.pageNum = curr; //页数
    str.resourceType = 0; //视频
    str.pageSize = 9; //每页显示的个数
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: '/RRM/manager/resource/queryResource',
        dataType: 'json',
        cache: false,
        data: JSON.stringify(str),
        success: function(res) {
            $(".imgbox").children().remove();
            var reslen = res.page.list.length;
            //console.log(res.page);
            if (reslen > 0) {
                $(".imgList").show();
                $("#pageContainer").show();
                var imglist = res.page.list;
                var imgcon = '';
                if (role == '1') {
                    for (var i = 0; i < reslen; i++) {
                        var rname = imglist[i].resourceName;
                        rname = imglist[i].resourceName.split('.');
                        var rtype = rname[rname.length - 1];
                        rname = rname[0];
                        imgcon += '<li class="img-item video-item" data-id="' + imglist[i].id +'"><div class="img-item-box" vurl="' + imglist[i].storagePath + '" data-name="' + imglist[i].resourceName + '" data-type="' + rtype + '" onclick="checkvideo(this)"><div class="video-slogn"><img src="../image/video-slogn.png"></div><img class="img-item-box-img" src="' + imglist[i].videoCover + '"></div><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a href="/RRM/manager/resource/downUploadRecord?path='+imglist[i].storagePath+'"><i class="fa fa-download" aria-hidden="true"></i><a/></p></div></li>';
                    }
                } else {
                    for (var i = 0; i < reslen; i++) {
                        var rname = imglist[i].resourceName;
                        rname = imglist[i].resourceName.split('.');
                        var rtype = rname[rname.length - 1];
                        rname = rname[0];
                        imgcon += '<li class="img-item video-item" data-id="'+imglist[i].id+'"><div class="img-item-box" vurl="' + imglist[i].storagePath + '" data-name="' + imglist[i].resourceName + '" data-type="' + rtype + '"  onclick="checkvideo(this)"><div class="video-slogn"><img src="../image/video-slogn.png"></div><img class="img-item-box-img" src="' + imglist[i].videoCover + '"></div><div class="img-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><a href="/RRM/manager/resource/downUploadRecord?path='+imglist[i].storagePath+'" class="img-down fright"><i class="fa fa-download" aria-hidden="true"></i>下载</a></div></li>';
                    }
                }
                $(".imgbox").append(imgcon);
                laypage({
                    cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.page.total / 9), //通过后台拿到的总页数
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
                $(".imgList").hide();
                $("#pageContainer").hide();
            }
        },

        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }
    })
}

/*删除某一个元素*/
function dele(obj) {
    var pid = parseInt($(obj).parents('.img-item').attr("data-id"));
    var id = {
        id: pid
    }
    $.ajax({
        contentType: 'application/json',
        url: "/RRM/manager/resource/deleteResource?ids="+pid,
        type: "POST",
        dataType: 'json',
        /*data: JSON.stringify(id),*/
        success: function(res) {
            if (res.success == true) {
                layer.alert("删除成功");
                $(obj).parents('.img-item').remove();
                location.reload();
            } else {
                layer.alert("删除失败");
            }
        },
        error: function(res) {
            layer.alert("删除失败");
        }
    })
}

//搜索视频
var find = {

};

find = {
    pageNum: 1,
    pageSize: 9,
    resourceType: 0
}

function found(curr) {
    var sevalue = ''; //搜索条件的val
    find.pageNum = 1;
    if (curr > 1) {
        find.pageNum = curr;
    }
    sevalue = $("input[name='search']").val();
    if (sevalue != '') {
        var key = $(".sea-sele p").text();
        if (key == '关键字') {
            find.resourceKey = sevalue;
            delete find["resourceModule"];
            delete find["resourcePlace"];
            delete find["taskName"];
        } else if (key == '资源模块') {
            find.resourceModule = sevalue;
            delete find["resourceKey"];
            delete find["resourcePlace"];
            delete find["taskName"];
        } else if (key == '地点') {
            find.resourcePlace = sevalue;
            delete find["resourceModule"];
            delete find["resourceKey"];
            delete find["taskName"];
        } else if (key == '专项名称') {
            find.taskName = sevalue;
            delete find["resourceModule"];
            delete find["resourcePlace"];
            delete find["resourceKey"];
        }
        hrefval = find;
        //调取接口
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: '/RRM/manager/resource/queryResource',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(find),
            success: function(res) {
                $(".imgbox").children().remove();
                var reslen = res.page.list.length;
                //console.log(res.page);
                if (reslen > 0) {
                    $(".imgList").show();
                    $("#pageContainer").show();
                    var imglist = res.page.list;
                    var imgcon = '';
                    if (role == '1') {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            imgcon += '<li class="img-item video-item" data-id="' + imglist[i].id +'"><div class="img-item-box" vurl="' + imglist[i].storagePath + '" data-name="' + imglist[i].resourceName + '" data-type="' + rtype + '" onclick="checkvideo(this)"><div class="video-slogn"><img src="../image/video-slogn.png"></div><img class="img-item-box-img" src="' + imglist[i].videoCover + '"></div><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a href="/RRM/manager/resource/downUploadRecord?path='+imglist[i].storagePath+'"><i class="fa fa-download" aria-hidden="true"></i><a/></p></div></li>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            imgcon += '<li class="img-item video-item" data-id="'+imglist[i].id+'"><div class="img-item-box" vurl="' + imglist[i].storagePath + '" data-name="' + imglist[i].resourceName + '" data-type="' + rtype + '"  onclick="checkvideo(this)"><div class="video-slogn"><img src="../image/video-slogn.png"></div><img class="img-item-box-img" src="' + imglist[i].videoCover + '"></div><div class="img-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><a href="/RRM/manager/resource/downUploadRecord?path='+imglist[i].storagePath+'" class="img-down fright"><i class="fa fa-download" aria-hidden="true"></i>下载</a></div></li>';
                        }
                    }
                    $(".imgbox").append(imgcon);
                    laypage({
                        cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.page.total / 9), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        skin: '#428bca',
                        jump: function(obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                found(obj.curr);
                            }
                        }
                    });
                } else {
                    layer.alert("没有搜索到合适");
                    $(".imgList").hide();
                    $("#pageContainer").hide();
                }
            },

            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            }
        })
    } else {
        hrefval = find;
        find.pageNum = 1;
        if (curr > 1) {
            find.pageNum = curr;
        }
        delete find["resourceKey"];
        delete find["resourceModule"];
        delete find["resourcePlace"]; //根据搜索条件调接口
        delete find["taskName"];
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: '/RRM/manager/resource/queryResource',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(find),
            success: function(res) {
                $(".imgbox").children().remove();
                var reslen = res.page.list.length;
                //console.log(res.page);
                if (reslen > 0) {
                    $(".imgList").show();
                    $("#pageContainer").show();
                    var imglist = res.page.list;
                    var imgcon = '';
                    if (role == '1') {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            imgcon += '<li class="img-item video-item" data-id="' + imglist[i].id +'"><div class="img-item-box" vurl="' + imglist[i].storagePath + '" data-name="' + imglist[i].resourceName + '" data-type="' + rtype + '" onclick="checkvideo(this)"><div class="video-slogn"><img src="../image/video-slogn.png"></div><img class="img-item-box-img" src="' + imglist[i].videoCover + '"></div><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a href="/RRM/manager/resource/downUploadRecord?path='+imglist[i].storagePath+'"><i class="fa fa-download" aria-hidden="true"></i><a/></p></div></li>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                           imgcon += '<li class="img-item video-item" data-id="'+imglist[i].id+'"><div class="img-item-box" vurl="' + imglist[i].storagePath + '" data-name="' + imglist[i].resourceName + '" data-type="' + rtype + '"  onclick="checkvideo(this)"><div class="video-slogn"><img src="../image/video-slogn.png"></div><img class="img-item-box-img" src="' + imglist[i].videoCover + '"></div><div class="img-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><a href="/RRM/manager/resource/downUploadRecord?path='+imglist[i].storagePath+'" class="img-down fright"><i class="fa fa-download" aria-hidden="true"></i>下载</a></div></li>';
                        }
                    }
                    $(".imgbox").append(imgcon);
                    laypage({
                        cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.page.total / 9), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        skin: '#428bca',
                        jump: function(obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                found(obj.curr);
                            }
                        }
                    });
                } else {
                    layer.alert("没有搜索到合适");
                    $(".imgList").hide();
                    $("#pageContainer").hide();
                }
            },

            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            }
        })
    }
}

/*跳转到视频列表*/
function videolist() {
    hrefval.pageNum = 1;
    var jval = JSON.stringify(hrefval);
    jval = encodeURI(encodeURI(jval));
    window.location.href = 'videolist.html?value=' + jval + '';
}