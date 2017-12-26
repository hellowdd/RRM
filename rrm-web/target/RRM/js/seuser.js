 var role = localStorage.getItem("role");
 $().ready(function() {
     history(1);

 })
 //下载历史
 var hisstr = {};
 function history(curr) {
     hisstr.pageNum = curr;
     hisstr.pageSize = 14;
     hisstr.operationType = "1"; //下载历史
     $.ajax({
         contentType: 'application/json',
         type: 'get',
         url: '/RRM/manager/operation/queryOperation',
         dataType: 'json',
         cache: false,
         data: hisstr,
         success: function(res) {
             $(".table-con").children().remove();
             var data = res.page.list;
             console.log(res);
             if(data==null){
                $(".his-con .tit-num").html('0条');
             }
             var reslen = data.length;

             if (reslen > 0) {
                $(".his-con .tit-num").html('' + res.page.total + '条');
                 var hiscon = '';
                 for (var i = 0; i < reslen; i++) {
                     var mode = data[i].resourceName; //资源类型
                     mode = mode.split('.');
                     var modename = mode[0];
                     mode = mode[mode.length - 1];
                     //判断文件类型
                     if (data[i].resourceType == '0') {
                         //视频
                         var txt = '<p class="his-item-kind c2a fleft">【视频】</p>';
                     }
                     if (data[i].resourceType == '1') {
                         //图片
                         var txt = '<p class="his-item-kind c6b fleft">【图片】</p>';
                        
                     }
                     if (data[i].resourceType == '2') {
                         //文本
                         var txt = '<p class="his-item-kind cef fleft">【文本】</p>';
                        
                     }
                     hiscon += ' <li><ul class="table-item" data-id="' + data[i].id + '" data-type="'+data[i].resourceType+'"><li>'+txt+'</li><li><span>' + mode + '</span></li><li><span>' + data[i].resourceName + '</span></li><li><span>' + data[i].operationPeopleName + '</span></li><li><span>' + data[i].operationTime + '</span></li><li><span onclick="detail(this)" data-id = "' + data[i].operationResource + '" data-type="' + data[i].resourceType + '" >查看</span></li></ul></li>';
                 }
                 $(".table-con").append(hiscon);
                 laypage({
                     cont: 'pageContainers', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                     pages: Math.ceil(res.page.total / 14), //通过后台拿到的总页数
                     curr: curr || 1, //当前页
                     skin: '#428bca',
                     jump: function(obj, first) { //触发分页后的回调
                         if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                             history(obj.curr);
                         }
                     }
                 });
             }
             else{
                $(".his-con .tit-num").html('0条');
             }
         },
         error: function(res) {

         }
     });
 }

//查看详细信息
var dataid = '';
var id = {};
var dtype = '';
function detail(obj) {
     dataid = $(obj).attr("data-id");
     dtype = $(obj).attr("data-type");
    id = {
        id: dataid,
        resourceType: dtype
    }
    parent.id = id;
    parent.layer.open({
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
 
 
 