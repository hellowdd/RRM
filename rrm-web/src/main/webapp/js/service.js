 var role = localStorage.getItem("role");
 var id = {};
 $().ready(function() {
     if (role == '1') {
        $("iframe").attr("src", '/RRM/manager/page/seadimin.html');
        
        
     }
     else{
         $("iframe").attr("src", '/RRM/manager/page/seuser.html');
     }

 })