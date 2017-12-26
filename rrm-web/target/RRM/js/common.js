    var role = localStorage.getItem("role");
     var cas = localStorage.getItem("cas");
     //console.log(JSON.stringify(cas));
    /*if(role==null||role==undefined){
         $.ajax({
            contentType: "application/json",
            type: 'POST',
            url: '/RRM/manager/loginAction/loginCasOut',
            dataType: 'json',
            async: false,
            cache: false,
            data: {},
            success: function(res) {
                if(res.success==true){
                   localStorage.removeItem('role');
                    localStorage.removeItem("user");
                    var cas = res.data.casServerUrlPrefix;
                    var scheme = res.data.scheme;
                    var serverName = res.data.serverName;
                    var serverPort = res.data.serverPort;
                    var contextPath = res.data.contextPath;
                   // console.log(''+cas+'/logout?service='+''+scheme+'://'+serverName+':'+serverPort+''+contextPath+'/manager/loginAction/loginCas');
                    window.location.href=cas+'/logout?service='+''+scheme+'://'+serverName+':'+serverPort+''+contextPath+'/manager/loginAction/loginCas?ran=' + Math.random();
                }
            },

            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            }
        });
    }*/

    $().ready(function() {
            $(".userupload").hide();
    if(role==1){
        $(".userupload").show();
    }
    else{
        $(".userupload").hide();
    }
       $(".hed-right p").html(localStorage.getItem("user"));
        //导航检索
        $(".retrie").mouseover(function() {
            $(".retrie-ul").show();
            $(".retrie").addClass("retrie-span");
        });
        $(".retrie-ul").mouseleave(function() {
            $(".retrie-ul").hide();
            $(".retrie").removeClass("retrie-span");
        })
        //条件搜索
        $(".sea-sele").click(function() {
            $(".sea-option").show();
        });
        $(".sea-option").mouseleave(function() {
            $(".sea-option").hide();
        });
        //退出登录
        $(".userlogo").mouseover(function() {
            $(".exit").show();
        })
        $(".exit").mouseleave(function() {
            $(".exit").hide();
        })
        /*搜索*/
        $(".sea-option li").click(function() {
            var val = $(this).text();
            $(".sea-sele p").html(val+'<i class="fa fa-angle-down" aria-hidden="true"></i>');
            $(".sea-option").hide();
        });
        //服务
        $(".service").click(function(){
            window.location.href="/RRM/manager/page/service.html";
        })
    });
    function upload(){
        window.location.href="/RRM/manager/page/upload.html";
    }
//增加关键字
var labellist = [];
function addlabel() {
    layer.open({
        content: '<input type="text" name="label" placeholder="请输入关键字内容"',
        btn: ['确定', '取消'],
        yes: function(index, layero) {
            var value = $("input[name=label]").val();
            if (value != '') {
                $(".up-label-box").append('<p class="zclabel"><i class="fa fa-times" aria-hidden="true" onclick="closelabel(this)"></i>' + value + '</p>');
                labellist.push(value);
                //颜色的随机
                var labelindex = $(".zclabel").length;
                var colorList = ["#9dc6eb", "#f8c471", "#b9a3ef", "#fdb1ca", "#9dc6eb", "#f8c471", "#b9a3ef", "#fdb1ca"];
                for (var i = 0; i < labelindex; i++) {
                    var bgColor = getColorByRandom(colorList);
                    $(".zclabel").eq(i).css("background", bgColor);
                }
                layer.close(index);
            } else {
                layer.msg("请输入关键字内容");
                return false;
            }
        },
        btn2: function(index, layero) {
            layer.close(index);
        },
        cancel: function() {
            //右上角关闭回调
            layer.close(index);
        }
    })
}
//颜色随机
function getColorByRandom(colorList) {
    var colorIndex = Math.floor(Math.random() * colorList.length);
    var color = colorList[colorIndex];
    colorList.splice(colorIndex, 1);
    return color;
}
//关键字删除
function closelabel(obj) {
    var index = parseInt($(obj).parent("p").index())-1;
   $(obj).parent().remove();
   // console.log($(obj).find('span').html());
    labellist.splice(index,1)


}
    //退出登录
    function exituser(){
        $.ajax({
            contentType: "application/json",
            type: 'POST',
            url: '/RRM/manager/loginAction/loginCasOut',
            dataType: 'json',
            async: false,
            cache: false,
            data: {},
            success: function(res) {
                if(res.success==true){
                   localStorage.removeItem('role');
                    localStorage.removeItem("user");
                    var cas = res.data.casServerUrlPrefix;
                    var scheme = res.data.scheme;
                    var serverName = res.data.serverName;
                    var serverPort = res.data.serverPort;
                    var contextPath = res.data.contextPath;
                   // console.log(''+cas+'/logout?service='+''+scheme+'://'+serverName+':'+serverPort+''+contextPath+'/manager/loginAction/loginCas');
                    window.location.href=cas+'/logout?service='+''+scheme+'://'+serverName+':'+serverPort+''+contextPath+'/manager/loginAction/loginCas?ran=' + Math.random();
                }
            },

            error: function() {
                layer.alert("服务器正忙,请稍后再试");
            }
        });
    }

//格式化日期
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

//高级筛选
function filters(){
    window.location.href = '/RRM/manager/page/filter.html';
}