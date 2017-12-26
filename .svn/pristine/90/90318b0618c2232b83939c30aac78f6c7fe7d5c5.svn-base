//权限判断
var role = localStorage.getItem("role");
$().ready(function() {
    $(".downall").hide();
    $(".more-down").hide();
    $(".listall-down label").click(function() {
        /*清除全选*/
        var len = $(".text-con .text-tit").length;
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
            $(".text-tit label").removeClass('doshow');
            bak.splice(0, len);
        }
        /*全选*/
        else {
            $(this).addClass("doshow");
            $(".text-tit label").addClass('doshow');
            bak.splice(0, len);
            for (var j = 0; j < len; j++) {
                bak.push($(".text-item").eq(j).attr("data-id"));
            }
        }

    });
    search(1);
});
//查看文本列表
var str = {};
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

var txtval = decodeURI(getQueryString('value'));
txtval = JSON.parse(txtval);


if (txtval != null) {
    str = txtval;
}

function search(curr) {
    //设置参数
    str.pageNum = curr; //页数
    str.resourceType = 2; //视频
    str.pageSize = 10; //每页显示的个数
    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: '/RRM/manager/resource/queryResource',
        dataType: 'json',
        cache: false,
        data: JSON.stringify(str),
        success: function(res) {
            $(".text-con").children().remove();
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
                        if (rtype == 'docx') {
                            var ico = '<i class="fa fa-file-word-o" aria-hidden="true"></i>';
                        }
                        if (rtype == 'xlsx' || rtype == 'xls') {
                            var ico = '<i class="fa fa-file-excel-o" aria-hidden="true"></i>'
                        }
                        if (rtype == 'pdf') {
                            var ico = '<i class="fa fa-file-pdf-o" aria-hidden="true"></i>';
                        }
                        if (rtype == 'txt') {
                            var ico = '<i class="fa fa-file-text" aria-hidden="true"></i>';
                        }
                        if (rtype == 'ppt' || rtype == 'pptx') {
                            var ico = '<i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>';
                        }
                        imgcon += '<div class="text-item" data-id="' + imglist[i].id + '" data-url="' + imglist[i].thumbnailPath + '" data-name="' + rname + '" data-down="' + imglist[i].storagePath + '"><div class="text-tit clearfix" ><div class="text-tit-left fleft" onclick="checktxt(this)"><span>' + ico + '</span><p class="fleft">' + rname + '</p></div><div class="text-tit-right fright"><p class="text-user fleft">' + imglist[i].uploadPeopleName + '</p><p class="text-date fleft">' + formatDateTime(imglist[i].uploadTime) + '</p><p class="text-down fleft" onclick="edit(this)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>编辑</p><p class="text-down fleft" onclick="dele(this)"><i class="fa fa-trash-o" aria-hidden="true"></i>删除</p><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><p class="text-down fleft"><i class="fa fa-download" aria-hidden="true"></i>下载</p></a></div></div><div class="text-p clearfix"><p>' + imglist[i].videoKeys + '</p></div></div>';

                    }
                } else {
                    for (var i = 0; i < reslen; i++) {
                        var rname = imglist[i].resourceName;
                        rname = imglist[i].resourceName.split('.');
                        var rtype = rname[rname.length - 1];
                        rname = rname[0];
                        if (rtype == 'docx') {
                            var ico = '<i class="fa fa-file-word-o" aria-hidden="true"></i>';
                        }
                        if (rtype == 'xlsx' || rtype == 'xls') {
                            var ico = '<i class="fa fa-file-excel-o" aria-hidden="true"></i>'
                        }
                        if (rtype == 'pdf') {
                            var ico = '<i class="fa fa-file-pdf-o" aria-hidden="true"></i>';
                        }
                        if (rtype == 'txt') {
                            var ico = '<i class="fa fa-file-text" aria-hidden="true"></i>';
                        }
                        if (rtype == 'ppt' || rtype == 'pptx') {
                            var ico = '<i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>';
                        }
                        imgcon += '<div class="text-item" data-id="' + imglist[i].id + '" data-url="' + imglist[i].thumbnailPath + '" data-name="' + rname + '" data-down="' + imglist[i].storagePath + '"><div class="text-tit clearfix"><div class="text-tit-left fleft" onclick="checktxt(this)"><span>' + ico + '</span><p class="fleft">' + rname + '</p></div><div class="text-tit-right fright"><p class="text-user fleft">' + imglist[i].uploadPeopleName + '</p><p class="text-date fleft">' + formatDateTime(imglist[i].uploadTime) + '</p><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><p class="text-down fleft"><i class="fa fa-download" aria-hidden="true"></i>下载</p></a></div></div><div class="text-p clearfix"><p>' + imglist[i].videoKeys + '</p></div></div>';
                    }
                }
                $(".text-con").append(imgcon);
                laypage({
                    cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.page.total / 10), //通过后台拿到的总页数
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
            location.reload();
            //layer.alert("服务器正忙,请稍后再试");
        }
    })
}

//编辑信息
var dataid = '';
var id = {};

function edit(obj) {
    //test 
    dataid = parseInt($(obj).parents('.text-item').attr("data-id"));
    id = {
        id: dataid,
        resourceType: '2'
    }
    layer.open({
        title: false,
        type: 2,
        closeBtn: 0,
        area: ['870px', '490px'],
        content: ['/RRM/manager/page/t.html', 'no'],
        success: function(layero, index) {
            /*关闭弹窗*/
        }
    });
}
//查看文本信息
var dataurl = '';
var dataname = '';
var datadown = '';
var downid = '';

function checktxt(obj) {
    dataurl = $(obj).parents('.text-item').attr("data-url");
    dataname = $(obj).parents('.text-item').attr("data-name");
    datadown = $(obj).parents('.text-item').attr("data-down");
    downid = $(obj).parents('.text-item').attr("data-id");
    var chtxt = {
        dataurl: dataurl,
        dataname: dataname,
        datadown: datadown,
        downid: downid

    };
    var chtxt = JSON.stringify(chtxt);
    chtxt = encodeURI(encodeURI(chtxt));
    window.location.href = 'pdfjs.html?url=' + chtxt + '';

}
/*删除某一个元素*/
function dele(obj) {
    var pid = parseInt($(obj).parents('.text-item').attr("data-id"));
    var ids = {
        ids: pid
    }
    $.ajax({
        contentType: 'application/json',
        url: "/RRM/manager/resource/deleteResource?ids=" + pid,
        type: "POST",
        dataType: 'json',
        /*data: JSON.stringify(ids),*/
        success: function(res) {
            if (res.success == true) {
                layer.alert("删除成功");
                $(obj).parents('.text-item').remove();
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
    pageSize: 10,
    resourceType: '2'
}

function found(curr) {
    var sevalue = '';
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
        //console.log(hrefval);
        //根据搜索条件调接口
        $.ajax({
            contentType: 'application/json',
            type: 'POST',
            url: '/RRM/manager/resource/queryResource',
            dataType: 'json',
            cache: false,
            data: JSON.stringify(find),
            success: function(res) {
                $(".text-con").children().remove();
                var reslen = res.page.list.length;
                // console.log(res.page);
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
                            if (rtype == 'docx') {
                                var ico = '<i class="fa fa-file-word-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'xlsx' || rtype == 'xls') {
                                var ico = '<i class="fa fa-file-excel-o" aria-hidden="true"></i>'
                            }
                            if (rtype == 'pdf') {
                                var ico = '<i class="fa fa-file-pdf-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'txt') {
                                var ico = '<i class="fa fa-file-text" aria-hidden="true"></i>';
                            }
                            if (rtype == 'ppt' || rtype == 'pptx') {
                                var ico = '<i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>';
                            }
                            imgcon += '<div class="text-item" data-id="' + imglist[i].id + '" data-url="' + imglist[i].thumbnailPath + '" data-name="' + rname + '" data-down="' + imglist[i].storagePath + '"><div class="text-tit clearfix" ><div class="text-tit-left fleft" onclick="checktxt(this)"><span>' + ico + '</span><p class="fleft">' + rname + '</p></div><div class="text-tit-right fright"><p class="text-user fleft">' + imglist[i].uploadPeopleName + '</p><p class="text-date fleft">' + formatDateTime(imglist[i].uploadTime) + '</p><p class="text-down fleft" onclick="edit(this)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>编辑</p><p class="text-down fleft" onclick="dele(this)"><i class="fa fa-trash-o" aria-hidden="true"></i>删除</p><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><p class="text-down fleft"><i class="fa fa-download" aria-hidden="true"></i>下载</p></a></div></div><div class="text-p clearfix"><p>' + imglist[i].videoKeys + '</p></div></div>';

                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            if (rtype == 'docx') {
                                var ico = '<i class="fa fa-file-word-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'xlsx' || rtype == 'xls') {
                                var ico = '<i class="fa fa-file-excel-o" aria-hidden="true"></i>'
                            }
                            if (rtype == 'pdf') {
                                var ico = '<i class="fa fa-file-pdf-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'txt') {
                                var ico = '<i class="fa fa-file-text" aria-hidden="true"></i>';
                            }
                            if (rtype == 'ppt' || rtype == 'pptx') {
                                var ico = '<i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>';
                            }
                            imgcon += '<div class="text-item" data-id="' + imglist[i].id + '" data-url="' + imglist[i].thumbnailPath + '" data-name="' + rname + '" data-down="' + imglist[i].storagePath + '"><div class="text-tit clearfix"><div class="text-tit-left fleft" onclick="checktxt(this)"><span>' + ico + '</span><p class="fleft">' + rname + '</p></div><div class="text-tit-right fright"><p class="text-user fleft">' + imglist[i].uploadPeopleName + '</p><p class="text-date fleft">' + formatDateTime(imglist[i].uploadTime) + '</p><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><p class="text-down fleft"><i class="fa fa-download" aria-hidden="true"></i>下载</p></a></div></div><div class="text-p clearfix"><p>' + imglist[i].videoKeys + '</p></div></div>';
                        }
                    }

                    $(".text-con").append(imgcon);
                    laypage({
                        cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.page.total / 20), //通过后台拿到的总页数
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
                $(".text-con").children().remove();
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
                            if (rtype == 'docx') {
                                var ico = '<i class="fa fa-file-word-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'xlsx' || rtype == 'xls') {
                                var ico = '<i class="fa fa-file-excel-o" aria-hidden="true"></i>'
                            }
                            if (rtype == 'pdf') {
                                var ico = '<i class="fa fa-file-pdf-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'txt') {
                                var ico = '<i class="fa fa-file-text" aria-hidden="true"></i>';
                            }
                            if (rtype == 'ppt' || rtype == 'pptx') {
                                var ico = '<i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>';
                            }
                            imgcon += '<div class="text-item" data-id="' + imglist[i].id + '" data-url="' + imglist[i].thumbnailPath + '" data-name="' + rname + '" data-down="' + imglist[i].storagePath + '"><div class="text-tit clearfix" ><div class="text-tit-left fleft" onclick="checktxt(this)"><span>' + ico + '</span><p class="fleft">' + rname + '</p></div><div class="text-tit-right fright"><p class="text-user fleft">' + imglist[i].uploadPeopleName + '</p><p class="text-date fleft">' + formatDateTime(imglist[i].uploadTime) + '</p><p class="text-down fleft" onclick="edit(this)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i>编辑</p><p class="text-down fleft" onclick="dele(this)"><i class="fa fa-trash-o" aria-hidden="true"></i>删除</p><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><p class="text-down fleft"><i class="fa fa-download" aria-hidden="true"></i>下载</p></a></div></div><div class="text-p clearfix"><p>' + imglist[i].videoKeys + '</p></div></div>';

                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rname = imglist[i].resourceName;
                            rname = imglist[i].resourceName.split('.');
                            var rtype = rname[rname.length - 1];
                            rname = rname[0];
                            if (rtype == 'docx') {
                                var ico = '<i class="fa fa-file-word-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'xlsx' || rtype == 'xls') {
                                var ico = '<i class="fa fa-file-excel-o" aria-hidden="true"></i>'
                            }
                            if (rtype == 'pdf') {
                                var ico = '<i class="fa fa-file-pdf-o" aria-hidden="true"></i>';
                            }
                            if (rtype == 'txt') {
                                var ico = '<i class="fa fa-file-text" aria-hidden="true"></i>';
                            }
                            if (rtype == 'ppt' || rtype == 'pptx') {
                                var ico = '<i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>';
                            }
                            imgcon += '<div class="text-item" data-id="' + imglist[i].id + '" data-url="' + imglist[i].thumbnailPath + '" data-name="' + rname + '" data-down="' + imglist[i].storagePath + '"><div class="text-tit clearfix"><div class="text-tit-left fleft" onclick="checktxt(this)"><span>' + ico + '</span><p class="fleft">' + rname + '</p></div><div class="text-tit-right fright"><p class="text-user fleft">' + imglist[i].uploadPeopleName + '</p><p class="text-date fleft">' + formatDateTime(imglist[i].uploadTime) + '</p><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><p class="text-down fleft"><i class="fa fa-download" aria-hidden="true"></i>下载</p></a></div></div><div class="text-p clearfix"><p>' + imglist[i].videoKeys + '</p></div></div>';
                        }
                    }

                    $(".text-con").append(imgcon);
                    laypage({
                        cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.page.total / 10), //通过后台拿到的总页数
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

var bak = [];
/*批量下载*/
function batchtxt() {
    $(".downall").show();
    $(".more-down").show();
    $(".all-down").hide();
    $(".list-downall").hide();
    $(".text-tit-left").before('<label for="down-item" class="down-item" onclick="checked(this)"></label> <input type="checkbox" id="down-item">');
}
/*单文件下载*/
function single() {
    $(".downall").hide();
    $(".more-down").hide();
    $(".all-down").show();
    $(".list-downall").show();
    $(".text-tit").find('label').remove();
    $(".text-tit").find('input').remove();
}

function checked(obj) {
    var bakid = $(obj).parents('.text-item').attr("data-id");
    if ($(obj).hasClass("doshow")) {
        $(obj).removeClass("doshow");
        for (var i = 0; i < bak.length; i++) {
            if (bak[i] == bakid) {
                bak.splice(i, 1);
            }
        }
    } else {
        $(obj).addClass("doshow");
        for (var m = 0; m < bak.length; m++) {
            if (bak[m] == bakid) {
                break;
            }
        }
        bak.push(bakid);
    }
    event.stopPropagation();
}

var files = ''; //批量下载文件的id
//批量下载文件

function batchfile(obj) {
    if (bak.length == 0) {
        layer.alert("请选择需要下载的文本");
        return false;
    }
    //console.log(bak);
    files = bak.join(',');
    var filetype = '2'; //文档
    $("obj").attr("href", '/RRM/manager/resource/dowloadFile?ids=' + files + '&type=' + filetype + '');
    var imghref = '/RRM/manager/resource/dowloadFile?ids=' + files + '&type=' + filetype + '';
    window.location.href = imghref;
}

/*删除很多元素*/
function deles(obj) {
    if (bak.length == 0) {
        layer.alert("请选择需要删除的文本");
        return false;
    }
    files = bak.join(',');
    var ids = {
        ids: files
    }
    $.ajax({
        contentType: 'application/json',
        url: "/RRM/manager/resource/deleteResource?ids=" + files,
        type: "get",
        dataType: 'json',
        beforeSend: function() {
            // Handle the beforeSend event
            layer.load();
        },
        // data: JSON.stringify(ids),
        success: function(res) {
            if (res.success == true) {
                layer.alert("删除成功");
                location.reload();
            } else {
                layer.alert("删除失败");
            }
        },
        error: function(res) {
            layer.alert("删除失败");
        },
        complete: function() {
            layer.closeAll('loading');
        }
    })
}