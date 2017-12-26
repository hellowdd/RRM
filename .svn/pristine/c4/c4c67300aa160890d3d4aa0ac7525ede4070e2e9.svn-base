var role = localStorage.getItem("role");
$().ready(function() {
    $(".downall").hide();
    $(".more-down").hide();
    $(".listall-down label").click(function() {
        /*清除全选*/
        var len = $(".table .table-item").length;
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
            $(".table label").removeClass('doshow');
            bak.splice(0, len);
        }
        /*全选*/
        else {
            $(this).addClass("doshow");
            $(".table label").addClass('doshow');
            bak.splice(0, len);
            for (var j = 0; j < len; j++) {
                bak.push($(".table .table-item").eq(j).attr("data-id"));
            }
        }

    });
    search(1);
});

//解析地址参数
function getQueryString(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result ? decodeURIComponent(result[2]) : null;
}
hashval = decodeURI(getQueryString('value'));
hashval = JSON.parse(hashval);
/*编辑信息*/
var dataid = '';
var id = {};

function edit(obj) {
    labellist = [];
    dataid = $(obj).parents('.table-item').attr("data-id");
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
}
var reval = {};
/*关闭弹窗*/
function closelayer() {
    layer.closeAll();
}

//详细信息
var dataid = '';
var id = {};

function detail(obj) {
     dataid = $(obj).parents('.table-item').attr("data-id");
    id = {
        id: dataid,
        resourceType: '1'
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

/*删除某一个元素*/
function dele(obj) {
    $(obj).parent().parent().parent().remove();
    var pid = parseInt($(obj).parents('.table-item').attr("data-id"));
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

/*下载信息*/
function down(obj) {
    alert("success");
}


//开始获取图片的列表
var str = {

};
str = hashval;
str.pageNum = 1;

function search(curr) {
    str.pageNum = curr;

    reval = str;

    /*reval.pageNum = 1;
    reval.pageSize = 20;
    reval.resourceType = 1;*/
    //console.log(reval);

    $.ajax({
        contentType: 'application/json',
        type: 'POST',
        url: '/RRM/manager/resource/queryResource',
        dataType: 'json',
        cache: false,
        data: JSON.stringify(str),
        success: function(res) {
            $(".table").children().remove();
            var reslen = res.page.list.length;
            //console.log(res.page);
            if (reslen > 0) {
                var imglist = res.page.list;
                var imgcon = '';
                if (role == '1') {
                    for (var i = 0; i < reslen; i++) {
                        var rato = imglist[i].resourceName.split('.');
                        rato = rato[rato.length - 1];

                        imgcon += ' <li><ul class="table-item" data-id="' + imglist[i].id + '"><li><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><span class="fleft imgName">' + imglist[i].resourceName + '</span></a></li><li><span>' + rato + '</span></li><li><span>' + imglist[i].resolutionRatio + '</span></li><li><span>' + imglist[i].uploadPeopleName + '</span></a></li><li><span>' + formatDateTime(imglist[i].uploadTime) + '</span></li><li><a href="javascript:void(0);" onclick="detail(this)"><span>详细信息</span></a></li><li><i class="fa fa-pencil-square-o" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a class="fright" style="width:39px;" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></li></ul></li>';
                    }
                } else {
                    for (var i = 0; i < reslen; i++) {
                        var rato = imglist[i].resourceName.split('.');
                        rato = rato[rato.length - 1];
                        imgcon += ' <li><ul class="table-item" data-id="' + imglist[i].id + '"><li><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><span class="fleft imgName">' + imglist[i].resourceName + '</span></a></li><li><span>' + rato + '</span></li><li><span>' + imglist[i].resolutionRatio + '</span></li><li><span>' + imglist[i].uploadPeopleName + '</span></li><li><span>' + formatDateTime(imglist[i].uploadTime) + '</span></li><li><a href="javascript:void(0);" onclick="detail(this)"><span>详细信息</span></a></li><li><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></li></ul></li>';
                    }
                }

                $(".table").append(imgcon);
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
    $(".list-downall").show();
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
                $(".table").children().remove();
                var reslen = res.page.list.length;
                // console.log(res.page);
                if (reslen > 0) {
                    var imglist = res.page.list;
                    var imgcon = '';
                    if (role == '1') {
                        for (var i = 0; i < reslen; i++) {
                            var rato = imglist[i].resourceName.split('.');
                            rato = rato[rato.length - 1];
                            imgcon += ' <li><ul class="table-item" data-id="' + imglist[i].id + '"><li><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><span class="fleft imgName">' + imglist[i].resourceName + '</span></a></li><li><span>' + rato + '</span></li><li><span>' + imglist[i].resolutionRatio + '</span></li><li><span>' + imglist[i].uploadPeopleName + '</span></a></li><li><span>' + formatDateTime(imglist[i].uploadTime) + '</span></li><li><a href="javascript:void(0);" onclick="detail(this)"><span>详细信息</span></a></li><li><i class="fa fa-pencil-square-o" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a class="fright" style="width:39px;" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></li></ul></li>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rato = imglist[i].resourceName.split('.');
                            rato = rato[rato.length - 1];
                            imgcon += ' <li><ul class="table-item" data-id="' + imglist[i].id + '"><li><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><span class="fleft imgName">' + imglist[i].resourceName + '</span></a></li><li><span>' + rato + '</span></li><li><span>' + imglist[i].resolutionRatio + '</span></li><li><span>' + imglist[i].uploadPeopleName + '</span></li><li><span>' + formatDateTime(imglist[i].uploadTime) + '</span></li><li><a href="javascript:void(0);" onclick="detail(this)"><span>详细信息</span></a></li><li><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></li></ul></li>';
                        }
                    }

                    $(".table").append(imgcon);
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
                $(".table").children().remove();
                var reslen = res.page.list.length;
                //console.log(res.page);
                if (reslen > 0) {
                    var imglist = res.page.list;
                    var imgcon = '';
                    if (role == '1') {
                        for (var i = 0; i < reslen; i++) {
                            var rato = imglist[i].resourceName.split('.');
                            rato = rato[rato.length - 1];
                            imgcon += ' <li><ul class="table-item" data-id="' + imglist[i].id + '"><li><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><span class="fleft imgName">' + imglist[i].resourceName + '</span></a></li><li><span>' + rato + '</span></li><li><span>' + imglist[i].resolutionRatio + '</span></li><li><span>' + imglist[i].uploadPeopleName + '</span></a></li><li><span>' + formatDateTime(imglist[i].uploadTime) + '</span></li><li><a href="javascript:void(0);" onclick="detail(this)"><span>详细信息</span></a></li><li><i class="fa fa-pencil-square-o" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a class="fright" style="width:39px;" href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></li></ul></li>';
                        }
                    } else {
                        for (var i = 0; i < reslen; i++) {
                            var rato = imglist[i].resourceName.split('.');
                            rato = rato[rato.length - 1];
                            imgcon += ' <li><ul class="table-item" data-id="' + imglist[i].id + '"><li><a href="' + imglist[i].storagePath + '" class="test" title="' + imglist[i].resourceName + '" data-fancybox-group="button"><span class="fleft imgName">' + imglist[i].resourceName + '</span></a></li><li><span>' + rato + '</span></li><li><span>' + imglist[i].resolutionRatio + '</span></li><li><span>' + imglist[i].uploadPeopleName + '</span></li><li><span>' + formatDateTime(imglist[i].uploadTime) + '</span></li><li><a href="javascript:void(0);" onclick="detail(this)"><span>详细信息</span></a></li><li><a href="/RRM/manager/resource/downUploadRecord?path=' + imglist[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></li></ul></li>';
                        }
                    }

                    $(".table").append(imgcon);
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
                }
            },

            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            }
        })
    }
}

function formatDateTime(str) {
    //首先将日期分隔 ，获取到日期部分 和 时间部分
    var day = str.split(' ');
    //获取日期部分的年月日
    var days = day[0].split('-');
    //获取时间部分的 时分秒
    var mi = day[day.length - 1].split(':');
    //获取当前date类型日期
    var date = new Date();
    //给date赋值  年月日
    date.setUTCFullYear(days[0], days[1] - 1, days[2]);
    //给date赋值 时分秒  首先转换utc时区 ：+8      
    date.setUTCHours(mi[0] - 8, mi[1], mi[2]);
   var y = 1900 + date.getYear();
    var m = "0" + (date.getMonth() + 1);
    var d = "0" + date.getDate();
    return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length);
}
/*function formatDateTime(obj) {
    var date = new Date(obj);
    var y = 1900 + date.getYear();
    var m = "0" + (date.getMonth() + 1);
    var d = "0" + date.getDate();
    return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length);
}*/
//console.log(formatDateTime(1495157126));

/*点击保存*/
function upinfo() {
    //console.log(dataid);
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

/*返回到图片*/
function retu() {
    reval.pageNum = 1;
    reval.pageSize = 20;
    reval.resourceType = 1;
    var jval = JSON.stringify(reval);
    jval = encodeURI(encodeURI(jval));
    //console.log(jval);
    window.location.href = 'img.html?value=' + jval + '';
}

var bak = [];
/*批量下载*/
function batchimg() {
    $(".downall").show();
    $(".more-down").show();
    $(".all-down").hide();
    $(".list-downall").hide();
    $("li .test").before('<label for="down-item" class="down-item" onclick="checked(this)"></label> <input type="checkbox" id="down-item">');
}
/*单文件下载*/
function single() {
    $(".downall").hide();
    $(".more-down").hide();
    $(".all-down").show();
    $(".list-downall").show();
    $(".table-item li").find('label').remove();
    $(".table-item li").find('input').remove();
}

function checked(obj) {
    var bakid = $(obj).parents('.table-item').attr("data-id");
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
    if(bak.length==0){
        layer.alert("请选择需要下载的图片");
        return false;
    }
    files = bak.join(',');
    var filetype = '1'; //图片
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