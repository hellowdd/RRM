var role = localStorage.getItem("role"); //权限设置
$().ready(function() {
    $(".table label").click(function() {
        event.stopPropagation();
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
        } else {
            $(this).addClass("doshow");
            $(this).parent().parent().siblings().find('label').removeClass("doshow");
        }
    });
    search(1);
});
var reval = {};
//解析地址参数
function getQueryString(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
}
hashval = decodeURI(getQueryString('value'));
hashval = JSON.parse(hashval);
//开始获取图片的列表
var str = {

};
str = hashval;
str.pageNum = 1;

function search(curr) {
    str.pageNum = curr;
    reval = str;
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: '/RRM/manager/resource/queryResource',
        dataType: 'json',
        cache: false,
        data: JSON.stringify(str),
        success: function(res) {
            $(".table-img").children().remove();
            var reslen = res.page.list.length;
            //console.log(res.page);
            if (reslen > 0) {
                var imglist = res.page.list;
                var imgcon = '<table class="table"><thead><tr><td class="tleft"><span class="fleft">视频名称</span></td><td>视频类型</td><td>上传者</td><td>上传时间</td><td>详细信息</td><td class="tright">操作</td></tr></thead>';
                if (role == '1') {
                    for (var i = 0; i < reslen; i++) {
                        var rname = imglist[i].resourceName;
                        rname = imglist[i].resourceName.split('.');
                        var rtype = rname[rname.length - 1];
                        rname = rname[0];
                        imgcon += '<tr data-id="' + imglist[i].id + '"><td class="tleft" title="' + rname + '" onclick="checkvideo(this)" data-url="' + imglist[i].storagePath + '" video-type="' + rtype + '"><span class="fleft">' + rname + '</span></td><td>' + rtype + '</td><td>' + imglist[i].uploadPeopleName + '</td><td>' + formatTime(imglist[i].uploadTime) + '</td><td onclick="detail(this)">详细信息</td><td class="tright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></td></tr>';
                    }
                } else {
                    for (var i = 0; i < reslen; i++) {
                        var rname = imglist[i].resourceName;
                        rname = imglist[i].resourceName.split('.');
                        var rtype = rname[rname.length - 1];
                        rname = rname[0];
                        imgcon += '<tr data-id="' + imglist[i].id + '"><td class="tleft" title="' + rname + '" onclick="checkvideo(this)" data-url="' + imglist[i].storagePath + '" video-type="' + rtype + '"><span class="fleft">' + rname + '</span></td><td>' + rtype + '</td><td>' + imglist[i].uploadPeopleName + '</td><td>' + formatTime(imglist[i].uploadTime) + '</td><td onclick="detail(this)">详细信息</td><td class="tcenter"><a class="mr20" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></td></tr>';
                    }
                }
                imgcon += '</tbody></table><div id="pageContainer" class="page-container"></div>'
                $(".table-img").append(imgcon);
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
            }
        },

        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }
    })
}

//上传时间
function formatTime(time) {
    var time = time.substring(0, 10);
    return time;
}
//编辑信息 
var dataid = '';
var id = {};

function edit(obj) {
    dataid = parseInt($(obj).parents('tr').attr("data-id"));
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
/*删除某一个元素*/
function dele(obj) {
    var pid = parseInt($(obj).parents('tr').attr("data-id"));
    var id = {
        id: pid
    }
    $.ajax({
        contentType: 'application/json',
        url: "/RRM/manager/resource/deleteResource?ids=" + pid,
        type: "POST",
        dataType: 'json',
        /*data: JSON.stringify(id),*/
        success: function(res) {
            if (res.success == true) {
                layer.alert("删除成功");
                $(obj).parents('tr').remove();
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

//根据条件进行搜索
var find = {

};
find = {
    pageNum: 1,
    pageSize: 9,
    resourceType: '0'
}

function found(curr) {
    find.pageNum = 1;
    var sevalue = '';
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
        reval = find;
        //console.log(reval);
        //根据搜索条件调接口
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: '/RRM/manager/resource/queryResource',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(find),
            success: function(res) {
                $(".table-img").children().remove();
                var reslen = res.page.list.length;
                //console.log(res.page);
                if (reslen > 0) {
                    var imglist = res.page.list;
                    var imgcon = '<table class="table"><thead><tr><td class="tleft"><span class="fleft">视频名称</span></td><td>视频类型</td><td>上传者</td><td>上传时间</td><td>详细信息</td><td class="tright">操作</td></tr></thead>';
                    if (role == '1') {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            imgcon += '<tr data-id="' + imglist[i].id + '"><td class="tleft" title="' + rname + '" onclick="checkvideo(this)" data-url="' + imglist[i].storagePath + '" video-type="' + rtype + '"><span class="fleft">' + rname + '</span></td><td>' + rtype + '</td><td>' + imglist[i].uploadPeopleName + '</td><td>' + formatTime(imglist[i].uploadTime) + '</td><td onclick="detail(this)">详细信息</td><td class="tright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></td></tr>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            imgcon += '<tr data-id="' + imglist[i].id + '"><td class="tleft" title="' + rname + '" onclick="checkvideo(this)" data-url="' + imglist[i].storagePath + '" video-type="' + rtype + '"><span class="fleft">' + rname + '</span></td><td>' + rtype + '</td><td>' + imglist[i].uploadPeopleName + '</td><td>' + formatTime(imglist[i].uploadTime) + '</td><td onclick="detail(this)">详细信息</td><td class="tcenter "><a class="mr20" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></td></tr>';
                        }
                    }
                    imgcon += '</tbody></table><div id="pageContainer" class="page-container"></div>'
                    $(".table-img").append(imgcon);
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
                }
            },

            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            }
        })
    } else {
        find.pageNum = 1;
        if (curr > 1) {
            find.pageNum = curr;
        }
        delete find["resourceKey"];
        delete find["resourceModule"];
        delete find["resourcePlace"];
        delete find['taskName'];
        reval = find;
        //根据搜索条件调接口
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: '/RRM/manager/resource/queryResource',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(find),
            success: function(res) {
                $(".table-img").children().remove();
                var reslen = res.page.list.length;
                //console.log(res.page);
                if (reslen > 0) {
                    var imglist = res.page.list;
                    var imgcon = '<table class="table"><thead><tr><td class="tleft"><span class="fleft">视频名称</span></td><td>视频类型</td><td>上传者</td><td>上传时间</td><td>详细信息</td><td class="tright">操作</td></tr></thead>';
                    if (role == '1') {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            imgcon += '<tr data-id="' + imglist[i].id + '"><td class="tleft" title="' + rname + '" onclick="checkvideo(this)" data-url="' + imglist[i].storagePath + '" video-type="' + rtype + '"><span class="fleft">' + rname + '</span></td><td>' + rtype + '</td><td>' + imglist[i].uploadPeopleName + '</td><td>' + formatTime(imglist[i].uploadTime) + '</td><td onclick="detail(this)">详细信息</td><td class="tright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></td></tr>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            imgcon += '<tr data-id="' + imglist[i].id + '"><td class="tleft" title="' + rname + '" onclick="checkvideo(this)" data-url="' + imglist[i].storagePath + '" video-type="' + rtype + '"><span class="fleft">' + rname + '</span></td><td>' + rtype + '</td><td>' + imglist[i].uploadPeopleName + '</td><td>' + formatTime(imglist[i].uploadTime) + '</td><td onclick="detail(this)">详细信息</td><td class="tcenter"><a  class="mr20" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></td></tr>';
                        }
                    }
                    imgcon += '</tbody></table><div id="pageContainer" class="page-container"></div>'
                    $(".table-img").append(imgcon);
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
                }
            },

            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            }
        })
    }
}

/*返回到视频*/
function retu() {
    reval.pageNum = 1;
    reval.pageSize = 9;
    reval.resourceType = 0;
    var jval = JSON.stringify(reval);
    jval = encodeURI(encodeURI(jval));
    //console.log(jval);
    window.location.href = 'video.html?value=' + jval + '';
}

//查看视频
var videourl = ''; //视频地址
var videoname = ''; //视频名字
var videotype = ''; //视频格式
var videoid = '';//视频的id
function checkvideo(obj) {
    videourl = $(obj).attr("data-url");
     videoid = $(obj).parents('tr').attr("data-id");//视频的id
    videoname = $(obj).attr("title"); //视频名字
    videotype = $(obj).attr("video-type"); //视频格式
    layer.open({
        title: false,
        type: 2,
        area: ['888px', '500px'],
        content: ['/RRM/video.htm', 'no'],
        success: function(layero, index) {



        }
    });
}

//详细信息
var dataid = '';
var id = {};

function detail(obj) {
    dataid = parseInt($(obj).parents('tr').attr("data-id"));
    id = {
        id: dataid,
        resourceType: '0'
    }
    layer.open({
        title: false,
        type: 2,
        closeBtn: 0,
        area: ['870px', '560px'],
        content: ['c.html', 'no'],
        success: function(layero, index) {
            /*关闭弹窗*/
        }
    });
}