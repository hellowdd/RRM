$().ready(function() {

    $(".listall-down label").click(function() {
        /*清除全选*/
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
            $(".table label").removeClass('doshow');
        }
        /*全选*/
        else {
            $(this).addClass("doshow");
            $(".table label").addClass('doshow');
        }

    });
    $(".table label").click(function() {
        event.stopPropagation();
        if ($(this).hasClass("doshow")) {
            $(this).removeClass("doshow");
        } else {
            $(this).addClass("doshow");
        }
    });
    /*图片旋转*/
    $(".rotate").click(function() {
        event.stopPropagation();
        alert("rotate");
    });
});


/*编辑信息*/
function edit(obj) {
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        title: false, //不显示标题
        closeBtn: 0, //不显示关闭按钮
        area: ['870px', '490px'], //宽高
        content: '<div class="edit"><div class="edit-tit clearfix"><p>资源信息</p><div class="edit-close"><p onclick="savedata(this)">保存</p><p onclick="closelayer(this)"><i class="fa fa-times" aria-hidden="true"></i>关闭</p></div></div><div class="edit-con"><div class="info-box"><div class="info-item-box clearfix"><div class="info-item  mr20 fleft"><select style="text-align: center;"><option style="text-align: center;">图片</option><option>文本</option><option>视频</option></select></div><div class="info-item fleft"><select><option>2017</option><option>2016</option><option>2015</option></select></div></div><div class="info-item clearfix"><input type="text" name="taskName" placeholder="请填写任务名称"></div><div class="info-item-box clearfix"><div class="info-item wid50 fleft"><input type="text" name="taskAdd" placeholder="请填写任务地点"></input></div><div class="info-item wid50 fright"><input type="text" name="taskArea" placeholder="请填写任务所属行政区域"></input></div></div></div><div class="up-label-con"><p class="up-label-slogn">任务关键字</p><div class="up-label-box clearfix"><span class="label-add" onclick="addlabel();"></span><p>是九大</p><p>新闻</p><p>一带一路</p></div></div></div></div>'
    })
}

/*点击保存*/
function savedata() {
    alert("1352");
    layer.closeAll();
}
/*添加标签*/
/*function addlabel() {
    layer.open({
        content: '<input type="text" name="label" placeholder="请输入关键字内容"',
        btn: ['确定', '取消'],
        yes: function(index, layero) {
            alert("success");
            layer.close(index);
        },
        btn2: function(index, layero) {
            layer.close(index);
        },
        cancel: function() {
            //右上角关闭回调
            layer.close(index);
        }
    })
}*/
/*关闭弹窗*/
function closelayer() {
   layer.closeAll();
}

/*详细信息*/
function detail(obj) {
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        title: false, //不显示标题
        closeBtn: 0, //不显示关闭按钮
        area: ['870px', '490px'], //宽高
        content: '<div class="edit"><div class="edit-tit clearfix"><p>资源信息</p><div class="edit-close"><p onclick="savedata(this)">保存</p><p onclick="closelayer(this)"><i class="fa fa-times" aria-hidden="true"></i>关闭</p></div></div><div class="edit-con"><div class="info-box"><div class="info-item-box clearfix"><div class="info-item  mr20 fleft"><select style="text-align: center;" readonly><option style="text-align: center;">图片</option><option>文本</option><option>视频</option></select></div><div class="info-item fleft"><select><option>2017</option><option>2016</option><option>2015</option></select></div></div><div class="info-item clearfix"><input type="text" name="taskName" placeholder="请填写任务名称"></div><div class="info-item-box clearfix"><div class="info-item wid50 fleft"><input type="text" name="taskAdd" placeholder="请填写任务地点"></input></div><div class="info-item wid50 fright"><input type="text" name="taskArea" placeholder="请填写任务所属行政区域"></input></div></div></div><div class="up-label-con"><p class="up-label-slogn">任务关键字</p><div class="up-label-box clearfix"><span class="label-add" onclick="addlabel();"></span><p>是九大</p><p>新闻</p><p>一带一路</p></div></div></div></div>'
    })
}

/*删除信息*/
function dele(obj) {
$(obj).parent().parent().parent().remove();
}

/*下载信息*/
function down(obj) {
    alert("success");
}