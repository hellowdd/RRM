$().ready(function() {
    $("input[type=file]").change(function() {
        var val = $("input[type=file]").val();
        if (val != '') {
            $("input[type=file]").css("opacity", "1");
        }
    });
    //获取专项开始
    /*获取省*/
    $.ajax({
        type: 'GET',
        url: '/RRM/manager/resource/areaList',
        dataType: 'json',
        data: { code: '0' },
        success: function(res) {
            var prolen = res.data.length;
            if (prolen > 0) {
                var province = '<option value="0"></option>';
                for (var i = 0; i < prolen; i++) {
                    province += '<option value="' + res.data[i].code + '">' + res.data[i].name + '</option>';
                }
                $("select[class='province']").append(province);
               
            }
        },
        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }

    });
    $.ajax({
        type: 'GET',
        url: '/RRM/manager/app/getTaskInfo',
        dataType: 'json',
        data: {},
        success: function(res) {
            var len = res.data.length;
            if (len > 0) {
                var speop = '<option value="0">请选择</option>';
                var spe = res.data; //专项
                for (var i = 0; i < len; i++) {
                    if (spe[i].parentCode != undefined && spe[i].parentCode != null) {
                        speop += '<option value=' + spe[i].id + ',' + spe[i].parentCode + ',' + spe[i].cityCode + '>' + spe[i].name + '</option>';

                    } else {
                        speop += '<option value=' + spe[i].id + ',' + spe[i].cityCode + '>' + spe[i].name + '</option>';
                    }

                }
                $("select[name=taskName]").append(speop);
            }
        },
        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }

    });
    //选择专项
    $("select[class='taskName']").change(function() {
        $("select[class='city']").remove();
        var secode = $("select[class='taskName']").find("option:selected").val(); //获取citycode
        if(secode=='0'){
          $("select[class='province']").val('0')  
        }
        $("select[class='city']").remove();
       
        var pcode = secode.split(',');
        //只有省
        if (pcode.length == 2) {
            pcode = pcode[1];
            $("select[class='province']").val(pcode);
        } else if (pcode.length > 2) {
            var chcode = pcode[pcode.length - 1];
            pcode = pcode[1];
             $("select[class='province']").val(pcode);
            $("select[class='province']").after('<select class="city" disabled="disabled"></select>');
            /*获取市*/
            $.ajax({
                type: 'GET',
                url: '/RRM/manager/resource/areaList',
                dataType: 'json',
                data: { code: pcode },
                success: function(res) {
                    var prolen = res.data.length;
                    if (prolen > 0) {
                        var province = '';
                        for (var i = 0; i < prolen; i++) {
                            province += '<option value="' + res.data[i].code + '">' + res.data[i].name + '</option>';
                        }
                        $("select[class='city']").append(province);
                        $("select[class='city']").val(chcode);
                    }
                },
                error: function() {
                    layer.alert("服务器正忙,请稍后再试");
                }

            });
        }


    })
});
/*上传*/
function upinfo() {
    var proarr = ''; //省市县的字符串
    var labelarr = '';
    var infocond = $("select[class='info-cond']").val(); //资源类型
    var infoyear = $("input[id='info-year']").val(); //任务年份
    var taskName = $("select[class='taskName']").find('option:selected').text(); //资源名称
    taskName = $.trim(taskName);
    var taskAdd = $("input[name=taskAdd]").val(); //地点
    var taskfile = $("input[type=file]").val();
    var taskId = $("select[class='taskName']").find('option:selected').val();
    taskId = taskId.split(',');
    taskId = taskId[0];
    //行政区域
    var provin = $("select[class='province']").find('option:selected').val();
    var cityvin = $("select[class='city']").find('option:selected').val();
    if(cityvin!=undefined&&provin!=undefined){
         proarr = provin + ',' + cityvin + ',0';
    }
    if(cityvin==undefined&&provin==''){
         proarr ='0,0,0';

    }
    else if(cityvin==undefined){
        proarr = provin + ',0,0';
    }
    if (infoyear == '') {
        layer.msg("请上传年份");
        return false;
    }
    if (taskAdd == '') {
        layer.msg("请将资源信息填写完整");
        return false;
    }
    if (taskfile == '') {
        layer.msg("请选择需要上传的文件");
        return false;
    }
    if (taskName == '请选择') {
       taskName='';
       taskId = '';
    }
    //资源类型判断
    //视频判断
    if (infocond == '0') {
        var ftype = $('#fileList')[0].files[0].name;
        ftype = ftype.split(".");
        var fname = ftype[0];
        ftype = ftype[ftype.length - 1];
        if (ftype != 'flv' && ftype != 'avi') {
            layer.alert("请上传符合标准的视频格式文件");
            return false;
        }
    }
    //图片判断
    if (infocond == '1') {
        var ftype = $('#fileList')[0].files[0].name;
        ftype = ftype.split(".");
        ftype = ftype[ftype.length - 1];
        //console.log(ftype);
        ftype = ftype.toLowerCase();
        if (ftype != 'png' && ftype != 'jpg' && ftype != 'jpeg' && ftype != 'bmp' && ftype != 'gif') {
            layer.alert("请上传符合标准的图片格式文件");
            return false;
        }
    }
    //文本判断
    if (infocond == '2') {
        var ftype = $('#fileList')[0].files[0].name;
        ftype = ftype.split(".");
        var fname = ftype[0];
        ftype = ftype[ftype.length - 1];

        if (ftype != 'txt' && ftype != 'docx' && ftype != 'xlsx' && ftype != 'ppt' && ftype != 'pdf' && ftype != 'pptx'&& ftype != 'xls') {
            layer.alert("请上传符合标准的文档格式文件");
            return false;
        }
    }
    if (labellist.length == 0) {
        layer.msg("请至少增加一个关键字标签");
        return false;
    }
    //填充数据
    labelarr = labellist.join(',');
    var formData = new FormData();
    formData.append("fileList", $('#fileList')[0].files[0]);
    formData.append("taskName", taskName);
    formData.append("taskId", taskId);
    formData.append("resourceType", infocond);
    formData.append("resourceKey", labelarr);
    formData.append("resourcePlace", taskAdd);
    formData.append("taskYear", infoyear);
    formData.append("adminDivision", proarr);
    //图片上传
    if (infocond == '1') {
        $.ajax({
            type: "POST",
            url: "/RRM/manager/resource/uploadFile",
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function() {
                // Handle the beforeSend event
                layer.load();
            },
            success: function(res) {
                var data = $.parseJSON(res);
                if (data.success == true) {
                    if (data.data > 0) {
                        layer.alert("该文件已上传,请不要重复上传");
                        return false;
                    }
                    layer.alert("上传成功");
                    $("input").val("");
                    $("input[type=file]").css("opacity", "0");
                    var date = new Date;
                    var year = date.getFullYear();
                    laydate.render({
                        elem: '#info-year',
                        type: 'year',
                        value: year,
                        icon: '1' //指定元素
                    });
                    $("select").val("0");
                    /*$("select[class='province'] option:gt(0)").remove();*/
                    $("select[class='city']").remove();
                   
                    $("select[class='info-cond']").val("1");
                    $(".zclabel").remove();
                    labellist.length = 0;
                    $.ajax({
                        type: 'GET',
                        url: '/RRM/manager/app/getTaskInfo',
                        dataType: 'json',
                        data: {},
                        async: false,
                        success: function(res) {
                            $("select[class='taskName'] option").remove();
                            var len = res.data.length;
                            if (len > 0) {
                                var speop = '<option value="0">请选择</option>';
                                var spe = res.data; //专项
                                for (var i = 0; i < len; i++) {
                                    if (spe[i].parentCode != undefined && spe[i].parentCode != null) {
                                        speop += '<option value=' + spe[i].id + ',' + spe[i].parentCode + ',' + spe[i].cityCode + '>' + spe[i].name + '</option>';

                                    } else {
                                        speop += '<option value=' + spe[i].id + ',' + spe[i].cityCode + '>' + spe[i].name + '</option>';
                                    }

                                }
                                $("select[name=taskName]").append(speop);
                            }
                        },
                        error: function() {
                            layer.alert("服务器正忙,请稍后再试");
                        }

                    });
                } else {
                    layer.alert("上传失败");
                }

            },
            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            },
            complete: function() {
                layer.closeAll('loading');
            }
        });
    }
    //视频上传
    if (infocond == '0') {
        formData.append("resourceName", fname);
        $.ajax({
            type: "POST",
            url: "/RRM//manager/video/uploadVideoFile",
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function() {
                // Handle the beforeSend event
                layer.load();
            },
            success: function(res) {

                var data = $.parseJSON(res);
                if (data.success == true) {
                    if (data.data > 0) {
                        layer.alert("该文件已上传,请不要重复上传");
                        return false;
                    }
                    layer.alert("上传成功");
                    $("input").val("");
                    $("input[type=file]").css("opacity", "0");
                    var date = new Date;
                    var year = date.getFullYear();
                    laydate.render({
                        elem: '#info-year',
                        type: 'year',
                        value: year,
                        icon: '1' //指定元素
                    });
                    $("select").val("0");
                    /*$("select[class='province'] option:gt(0)").remove();*/
                    $("select[class='city']").remove();
                   
                    $("select[class='info-cond']").val("0");
                    $(".zclabel").remove();
                    labellist.length = 0;
                } else {
                    layer.alert("上传失败");
                }

            },
            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            },
            complete: function() {
                layer.closeAll('loading');
            }
        });
    }
    //文本上传
    if (infocond == '2') {
        formData.append("resourceName", fname);
        $.ajax({
            type: "POST",
            url: "/RRM//manager/text/uploadTextFile",
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function() {
                // Handle the beforeSend event
                layer.load();
            },
            success: function(res) {
                var data = $.parseJSON(res);
                if (data.success == true) {
                    if (data.data > 0) {
                        layer.alert("该文件已上传,请不要重复上传");
                        return false;
                    }
                    layer.alert("上传成功");
                    $("input").val("");
                    $("input[type=file]").css("opacity", "0");
                    var date = new Date;
                    var year = date.getFullYear();
                    laydate.render({
                        elem: '#info-year',
                        type: 'year',
                        value: year,
                        icon: '1' //指定元素
                    });
                    $("select").val("0");
                    /*$("select[class='province'] option:gt(0)").remove();*/
                    $("select[class='city']").remove();
                    
                    $("select[class='info-cond']").val("2");
                    $(".zclabel").remove();
                    labellist.length = 0;
                } else {
                    layer.alert("上传失败");
                }

            },
            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            },
            complete: function() {
                layer.closeAll('loading');
            }
        });
    }

}

function special(code) {
    $("select[class='taskName']>option").not(":eq(0)").remove();
    var cityco = $("select[class='city']").find('option:selected').val();
    var proco = $("select[class='province']").find('option:selected').val();
    code = proco;
    if (cityco != 0) {
        code = cityco;
    }
    $.ajax({
        type: 'GET',
        url: '/RRM/manager/app/getTaskInfo',
        dataType: 'json',
        data: { cityCode: code },
        success: function(res) {
            //console.log(res);
            var len = res.data.length;
            if (len > 0) {
                var speop = '';
                var spe = res.data; //专项
                for (var i = 0; i < len; i++) {
                    if (spe[i].parentCode != undefined && spe[i].parentCode != null) {
                        speop += '<option value=' + spe[i].id + ',' + spe[i].parentCode + ',' + spe[i].cityCode + '>' + spe[i].name + '</option>';

                    } else {
                        speop += '<option value=' + spe[i].id + ',' + spe[i].cityCode + '>' + spe[i].name + '</option>';
                    }

                }
                $("select[name=taskName]").append(speop);
            }
        },
        error: function() {
            layer.alert("服务器正忙,请稍后再试");
        }

    });
}