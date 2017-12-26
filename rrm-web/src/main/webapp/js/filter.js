 var role = localStorage.getItem("role");
 var cas = localStorage.getItem("cas");
 console.log(cas);
 $().ready(function() {
     //初始化年份
     initYear();
     $(".table-list").hide();
 })

 function allcheck() {

     $("input[type='checkbox']").prop("checked", true);
 }

 function clearall() {
     $("input[type='checkbox']").prop("checked", false);
 }
 //增加搜索项
 function tradd() {
     var trlen = $("table tbody tr").length;
      $(".filter-sub").attr("onclick","trsub()");
     if (trlen > 3) {
        $(".filter-add").removeAttr("onclick");
        $(".filter-cond").css("height","383px");
     } else {
        $(".filter-add").attr("onclick","tradd()");
     }
     var add = '<tr id="s2"><td><select name="selLogic"><option value="and">与</option><option value="or">或</option></select><i class="fa fa-caret-down l1" aria-hidden="true"></i></td><td><select name="sel" class="td2"><option value="resource_key">关键字</option><option value="resource_place">地点</option><option value="resource_module">资源模块</option><option value="task_name">专项名称</option></select><i class="fa fa-caret-down l1" aria-hidden="true"></i></td><td><input name="exp" type="text" class="input_1"></td><td><select name="selMode"><option value="0">模糊</option><option value="1">精确</option></select><i class="fa fa-caret-down l1" aria-hidden="true"></i></td></tr>';
     $("table tbody").append(add);
 }
 //减少搜索项
 function trsub() {

     $(".filter-add").attr("onclick","tradd()");
     var trlen = $("table tbody tr").length;
     if (trlen == 2) {
        $(".filter-sub").removeAttr("onclick");
     } else {
         $(".filter-sub").attr("onclick","trsub()"); 
     }
     $("table tbody tr:last").remove();
      $(".filter-cond").css("height","346px");
 }
 //综合搜索
 var str = {};
 str.pageNum = 1;
 str.pageSize = 10;

 function mixsearch(curr) {
     str.pageNum = curr;
     $(".table-con").children("li").remove(); //清除数据
     var searchinfo = []; //搜索的信息
     //选择的类型
     var chk_val = [];
     var typecheck = $("input[type='checkbox']:checked").each(function() {
         chk_val.push($(this).val());
     });
     if (chk_val.length == 0) {
         layer.alert("请至少选择一种资源类型");
         return false;
     }
     str.types = chk_val; //选中的资源类型
     var sea = $("input[name=exp]").val();
     if (sea == '') {
         layer.alert("请输入检索词");
         return false;
     }
     //获取检索词不为空的input
     var seaindex = $("input[name=exp]").length;
     console.log(seaindex);
     $("input[name=exp]").each(function() {
         var exval = $(this).val();
         exval = $.trim(exval);
         if (exval != "") {
             var info = $(this).val();
             var isDim = $(this).parent().siblings("td").find("select[name=selMode]").val();
             var andOr = $(this).parent().siblings("td").find("select[name=selLogic]").val();
             if(andOr==undefined){
                andOr = 'and';
             }
             var resourceQueryType = $(this).parent().siblings("td").find("select[name=sel] option:selected").val();
             searchinfo.push({
                 resourceQueryType: resourceQueryType,
                 isDim: isDim,
                 info: info,
                 andOr: andOr
             })
         }
     });
     str.searchInfo = searchinfo;
     var start = $("#startYear").val().substring(0, 4);
     var end = $('#endYear').val().substring(0, 4);
     if (start > end) {
         layer.alert("结束时间不能小于起始时间");
         return false;
     }
     str.startTime = start; //开始时间
     str.endTime = end; //结束时间
     //高级搜索的接口
     if (role == '1') {
         $.ajax({
             contentType: 'application/json',
             type: 'POST',
             url: '/RRM/api/app/advancedSearch',
             dataType: 'json',
             headers:{'_const_cas_assertion_':cas},
             cache: false,
             data: JSON.stringify(str),
             success: function(res) {
                 var data = res.page.list;
                 console.log(data);
                 var datalen = data.length;
                 if (datalen > 0) {
                     var advan = '';
                     for (var i = 0; i < datalen; i++) {
                         var uptime = data[i].uploadTime;
                         uptime = uptime.substring(0, 10);
                         var mode = data[i].resourceName; //资源类型
                         mode = mode.split('.');
                         var modename = mode[0];
                         mode = mode[mode.length - 1];
                         if (data[i].resourceType == '1') {
                             var kind = 'c6b'; //图片
                             var text = '图片';
                             var alabel = '<a href="' + data[i].storagePath + '" class="test" title="' + data[i].resourceName + '" data-fancybox-group="button">';
                         }
                         if (data[i].resourceType == '2') {
                             var kind = 'cef'; //文本
                             var text = '文本';
                             var alabel = '<a href="javascript:void(0)" data-id ="' + data[i].id + '" data-url="' + data[i].thumbnailPath + '" data-name="' + data[i].resourceName + '" data-down="' + data[i].storagePath + '" onclick="txtcheck(this)">';
                         }
                         if (data[i].resourceType == '0') {
                             var kind = 'c2a' //视频
                             var text = '视频';
                             var alabel = '<a href="javascript:void(0)" data-url="' + data[i].storagePath + '" title="' + modename + '" data-id="' + data[i].id + '" video-type="' + mode + '"  onclick="checkvideo(this)">';
                         }
                         advan += ' <li><ul class="table-item" data-id="' + data[i].id + '" data-type="'+data[i].resourceType+'"><li>' + alabel + '<span class="fleft">' + data[i].resourceName + '</span></a></li><li><span class=' + kind + '>【' + text + '】</span></li><li><span>' + mode + '</span></li><li><span>' + data[i].taskName + '</span></li><li><span>' + data[i].uploadPeopleName + '</span></li><li><span>' + uptime + '</span></li><li><i class="fa fa-pencil-square-o" onclick="edit(this)"></i><i class="fa fa-trash-o" aria-hidden="true" onclick="dele(this)"></i><a class="fright" style="width:39px;" href="/RRM/manager/resource/downUploadRecord?path=' + data[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i></a></li></ul></li>';
                     }
                     $(".table-con").append(advan);
                      $("#pageContainer").show();
                       $(".table-list").show();
                     laypage({
                         cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                         pages: Math.ceil(res.page.total / 10), //通过后台拿到的总页数
                         curr: curr || 1, //当前页
                         skin: '#428bca',
                         jump: function(obj, first) { //触发分页后的回调
                             if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                 mixsearch(obj.curr);
                             }
                         }
                     });
                 } else {
                     layer.alert("没有搜索到合适的数据");
                      $("#pageContainer").hide();
                       $(".table-list").hide();
                 }
             },
             error: function(res) {

             }
         });
     } else {
         $.ajax({
             contentType: 'application/json',
             type: 'POST',
             url: '/RRM/api/app/advancedSearch',
             dataType: 'json',
             cache: false,
             data: JSON.stringify(str),
             success: function(res) {

                 var data = res.page.list;
                 console.log(data);
                 var datalen = data.length;
                 if (datalen > 0) {
                     var advan = '';
                     for (var i = 0; i < datalen; i++) {
                         var uptime = data[i].uploadTime;
                         uptime = uptime.substring(0, 10);
                         var mode = data[i].resourceName; //资源类型
                         mode = mode.split('.');
                         var modename = mode[0];
                         mode = mode[mode.length - 1];
                         if (data[i].resourceType == '1') {
                             var kind = 'c6b'; //图片
                             var text = '图片';
                             var alabel = '<a href="' + data[i].storagePath + '" class="test" title="' + data[i].resourceName + '" data-fancybox-group="button">';
                         }
                         if (data[i].resourceType == '2') {
                             var kind = 'cef'; //文本
                             var text = '文本';
                             var alabel = '<a href="javascript:void" data-id ="' + data[i].id + '" data-url="' + data[i].thumbnailPath + '" data-name="' + data[i].resourceName + '" data-down="' + data[i].storagePath + '" onclick="txtcheck(this)">';
                         }
                         if (data[i].resourceType == '0') {
                             var kind = 'c2a' //视频
                             var text = '视频';
                             var alabel = '<a href="javascript:void(0)" data-url="' + data[i].storagePath + '" title="' + modename + '" data-id="' + data[i].id + '" video-type="' + mode + '"  onclick="checkvideo(this)">';
                         }
                         advan += ' <li><ul class="table-item" data-id="' + data[i].id + '" data-type="'+data[i].resourceType+'"><li>' + alabel + '<span class="fleft">' + data[i].resourceName + '</span></a></li><li><span class=' + kind + '>【' + text + '】</span></li><li><span>' + mode + '</span></li><li><span>' + data[i].taskName + '</span></li><li><span>' + data[i].uploadPeopleName + '</span></li><li><span>' + uptime + '</span></li><li><a href="/RRM/manager/resource/downUploadRecord?path=' + data[i].storagePath + '"><i class="fa fa-download" aria-hidden="true"></i>下载</a></li></ul></li>';
                     }
                     $(".table-con").append(advan);
                      $("#pageContainer").show();
                       $(".table-list").show();
                     laypage({
                         cont: 'pageContainer', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                         pages: Math.ceil(res.page.total / 10), //通过后台拿到的总页数
                         curr: curr || 1, //当前页
                         skin: '#428bca',
                         jump: function(obj, first) { //触发分页后的回调
                             if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                 mixsearch(obj.curr);
                             }
                         }
                     });
                 } else {
                     layer.alert("没有搜索到合适的数据");
                     $("#pageContainer").hide();
                      $(".table-list").hide();
                 }
             },
             error: function(res) {

             }
         });
     }
 }
 //自动加载年
 function initYear() {
     var startSel = $('#startYear').get(0);
     var endSel = $('#endYear').get(0);
     var currentYear = new Date().getFullYear();
     var minYear = 2010;
     startSel.options.add(new Option("不限", "2000"));
     for (var i = minYear; i <= currentYear; i++) {
         startSel.options.add(new Option(i + "年", i));
         endSel.options.add(new Option(i + "年", i));
     }
     endSel.value = currentYear;
 }

 //查看视频
 var videourl = ''; //视频地址
 var videoname = ''; //视频名字
 var videotype = ''; //视频格式
 var videoid = ''; //视频的id
 function checkvideo(obj) {
     videourl = $(obj).attr("data-url");
     videoid = $(obj).attr("data-id"); //视频的id
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


 //查看文本信息
 var dataurl = '';
 var dataname = '';
 var datadown = '';
 var downid = '';

 function txtcheck(obj) {
     dataurl = $(obj).attr("data-url");
     dataname = $(obj).attr("data-name");
     datadown = $(obj).attr("data-down");
     downid = $(obj).attr("data-id");
     var chtxt = {
         dataurl: dataurl,
         dataname: dataname,
         datadown: datadown,
         downid: downid

     };
     var chtxt = JSON.stringify(chtxt);
     chtxt = encodeURI(encodeURI(chtxt));
     window.open('/RRM/manager/page/pdfjs.html?url=' + chtxt + '');
    
    

 }

 //编辑信息
var dataid = '';
var id = {};
function edit(obj) {
    labellist = [];
    dataid = $(obj).parents('.table-item').attr("data-id");
    var resrouceType = $(obj).parents('.table-item').attr("data-type");
     id = {
        id: dataid,
        resourceType: resrouceType
    }
    layer.open({
        title: false,
        type: 2,
        closeBtn: 0,
        area: ['870px', '490px'],
        content: ['ft.html', 'no'],
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
        beforeSend: function() {
                // Handle the beforeSend event
                layer.load();
            },
        /*data: JSON.stringify(id),*/
        success: function(res) {
            if (res.success == true) {
                layer.alert("删除成功");
                $(obj).parents('.table-item').parent("li").remove();
                mixsearch(1);
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