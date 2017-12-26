//权限判断
var role = localStorage.getItem("role");
$().ready(function() {
    $(".more-down label").click(function() {
        var len = $(".imgbox .img-item").length;
        /*清除全选*/
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
            $(".imgbox label").removeClass('doshow');
            bak.splice(0, len);
        }
        /*全选*/
        else {
            $(this).addClass("doshow");
            $(".imgbox label").addClass('doshow');
            bak.splice(0, len);
            for (var j = 0; j < len; j++) {
                bak.push($(".img-item").eq(j).attr("data-id"));
            }
        }

    });
    //批量下载选择
    $(".downall").hide();
    $(".more-down").hide();


    search(1);
});
var hrefval = {};
//开始获取图片的列表
var str = {

};
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

//console.log(hashcond);
//console.log(hashcond);
imgval = decodeURI(getQueryString('value'));
imgval = JSON.parse(imgval);

if (imgval != null) {
    str = imgval;
}

function search(curr) {
    str.pageNum = curr;
    str.pageSize = 20;
    str.resourceType = 1;
    hrefval = str;
    //console.log(hrefval);
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
                        imgcon += '<li class="img-item" data-id = "' + imglist[i].id + '"><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><div class="img-item-box"><img src="' + imglist[i].thumbnailPath + '"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)" data-type="' + imglist[i].resourceType + '" data-year="' + imglist[i].taskYear + '" data-area = "' + imglist[i].adminDivision + '" data-name="' + imglist[i].resourceName + '" data-place="' + imglist[i].resourcePlace + '" data-key= "' + imglist[i].resourceKey + '"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a class="fright" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></p>';
                    }
                } else {
                    for (var i = 0; i < reslen; i++) {
                        imgcon += '<li class="img-item" data-id = "' + imglist[i].id + '"><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><div class="img-item-box"><img src="' + imglist[i].thumbnailPath + '"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></p>';
                    }
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
                $(".imgList").hide();
                $("#pageContainer").hide();
            }
        },

        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }
    })
}

//根据条件进行搜索
var find = {

};
find = {
    pageNum: 1,
    pageSize: 20,
    resourceType: '1'
}

function found(curr) {
    $(".downall").hide();
    $(".more-down").hide();
    $(".all-down").show();
    $(".th-list").show();
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
                $(".imgbox").children().remove();
                var reslen = res.page.list.length;
                // console.log(res.page);
                if (reslen > 0) {
                    $(".imgList").show();
                    $("#pageContainer").show();
                    var imglist = res.page.list;
                    var imgcon = '';
                    if (role == '1') {
                        for (var i = 0; i < reslen; i++) {
                            imgcon += '<li class="img-item" data-id = "' + imglist[i].id + '"><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><div class="img-item-box"><img src="' + imglist[i].thumbnailPath + '"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)" data-type="' + imglist[i].resourceType + '" data-year="' + imglist[i].taskYear + '" data-area = "' + imglist[i].adminDivision + '" data-name="' + imglist[i].resourceName + '" data-place="' + imglist[i].resourcePlace + '" data-key= "' + imglist[i].resourceKey + '"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a  class="fright" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></p>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            imgcon += '<li class="img-item" data-id = "' + imglist[i].id + '"><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><div class="img-item-box"><img src="' + imglist[i].thumbnailPath + '"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><a  href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></p>';
                        }
                    }

                    $(".imgbox").append(imgcon);
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
                            imgcon += '<li class="img-item" data-id = "' + imglist[i].id + '"><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><div class="img-item-box"><img src="' + imglist[i].thumbnailPath + '"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><i class="fa fa-pencil-square-o" aria-hidden="true" onclick="edit(this)" data-type="' + imglist[i].resourceType + '" data-year="' + imglist[i].taskYear + '" data-area = "' + imglist[i].adminDivision + '" data-name="' + imglist[i].resourceName + '" data-place="' + imglist[i].resourcePlace + '" data-key= "' + imglist[i].resourceKey + '"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a  class="fright" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></p>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            imgcon += '<li class="img-item" data-id = "' + imglist[i].id + '"><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><div class="img-item-box"><img src="' + imglist[i].thumbnailPath + '"></div></a><div class="img-text admin-text clearfix"><p class="img-name fleft">' + imglist[i].taskName + '</p><p class="img-down fright"><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></p>';
                        }
                    }

                    $(".imgbox").append(imgcon);
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
    }
}
/*跳转到列表*/
function imglist() {
    var jval = JSON.stringify(hrefval);
    jval = encodeURI(encodeURI(jval));
    window.location.href = 'imglist.html?value=' + jval + '';
}

/*删除某一个元素*/
function dele(obj) {
    var pid = parseInt($(obj).parents('.img-item').attr("data-id"));
    var id = {
        id: pid
    }
    $.ajax({
        contentType: 'application/json',
        url: "/RRM/manager/resource/deleteResource?ids=" + pid,
        type: "POST",
        dataType: 'json',
        /* data: JSON.stringify(id),*/
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
/*编辑信息*/
var dataid = '';
var padata = ''; //专项名字
var paindex = '';

function edit(obj) {
    labellist = [];
    dataid = parseInt($(obj).parents('.img-item').attr("data-id"));
    paindex = parseInt($(obj).parents('.img-item').index());
    id = {
        id: dataid,
        resourceType: '1'
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
    $(obj).parent().siblings('.img-name').html(padata);
}

/*点击保存*/
function upinfo() {
    var proarr = ''; //省市县的字符串
    var labelarr = '';
    var infocond = $("select[class='info-cond']").val(); //资源类型
    var infoyear = $("input[id='info-year']").val(); //任务年份
    var taskName = $("input[name=taskName]").val(); //资源名称
    var taskAdd = $("input[name=taskAdd]").val(); //地点
    //资源类型的判断
    //console.log(infocond);
    //行政区域
    var provin = $("select[class='province']").find('option:selected').val();
    var cityvin = $("select[class='city']").find('option:selected').val();
    var countyvin = $("select[class='county']").find('option:selected').val();
    if (provin == '0') {
        layer.msg("请选择行政区域");
        return false;
    } else {
        proarr = provin + ',' + cityvin + ',' + countyvin; //逗号分割字符串
        //console.log(proarr);
    }

    if (taskName == '' || taskAdd == '') {
        layer.msg("请将资源信息填写完整");
        return false;
    }
    if (labellist.length == 0) {
        layer.msg("请至少增加一个关键字标签");
        return false;
    }
    labelarr = labellist.join(',');
    var resourceInfo = {
        id: dataid,
        taskYear: infoyear,
        taskName: taskName,
        resourceType: infocond,
        resourceKey: labelarr,
        resourcePlace: taskAdd,
        adminDivision: proarr

    }
    $.ajax({
        contentType: 'application/json',
        type: "POST",
        url: "/RRM/manager/resource/updateResource",
        data: JSON.stringify(resourceInfo),
        success: function(res) {
            if (res.success == true) {
                layer.alert("更新成功");
                layer.closeAll();
            } else {
                layer.alert("上传失败");
            }

        },
        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }
    });
}
/*关闭弹窗*/
function closelayer() {
    layer.closeAll();
}

/*批量下载*/
function batchimg() {
    $(".downall").show();
    $(".more-down").show();
    $(".all-down").hide();
    $(".th-list").hide();
    $(".img-name").before('<label for="down-item" class="down-item" onclick="checked(this)"></label> <input type="checkbox" id="down-item">');
}
/*单文件下载*/
function single() {
    $(".downall").hide();
    $(".more-down").hide();
    $(".all-down").show();
    $(".th-list").show();
    $(".img-text").find('label').remove();
    $(".img-text").find('input').remove();
}
var bak = [];

function checked(obj) {
    var bakid = $(obj).parents('.img-item').attr("data-id");
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
    //console.log(bak);
    if (bak.length == 0) {
        layer.alert("请选择需要下载的图片");
        return false;
    }
    files = bak.join(',');
    var filetype = '1';
    $("obj").attr("href", '/RRM/manager/resource/dowloadFile?ids=' + files + '&type=' + filetype + '');
    var imghref = '/RRM/manager/resource/dowloadFile?ids=' + files + '&type=' + filetype + '';
    window.location.href = imghref;
}

/*删除很多元素*/
function deles(obj) {
    if (bak.length == 0) {
        layer.alert("请选择需要删除的图片");
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