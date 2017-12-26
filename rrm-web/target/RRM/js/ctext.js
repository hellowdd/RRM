//根据条件进行搜索
var find = {};
find = {
    pageNum: 1,
    pageSize: 10,
    resourceType: '2'
}
var sevalue = ''; //条件
function found() {


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
    } else {
        delete find["resourceKey"];
        delete find["resourceModule"];
        delete find["resourcePlace"];
        delete find['taskName'];
    }
     var jval = JSON.stringify(find);
    jval = encodeURI(encodeURI(jval));
     console.log(jval);
     console.log(find);
    window.location.href = 'text.html?value=' + jval + ''; 
}