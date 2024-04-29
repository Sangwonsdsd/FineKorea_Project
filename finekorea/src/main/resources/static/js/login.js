$(document).ready(function(){
    loginIDsave();
});

function loginIDsave(){
    let userLoginId = getCookie("userLoginId");
    $("input[name='adminId']").val(userLoginId);

    if($("input[name='adminId']").val() != ""){
        $("#saveID").attr("checked", true);
    }

    $('#saveID').change(function(){
        if($('#saveID').is(":checked")){
            let userLoginId = $("input[name='adminId']").val();
            setCookie("userLoginId", userLoginId, 30);
        }else{
            deleteCookie("userLoginId");
        }
    });

    $("input[name='adminId']").keyup(function(){
        if($("#saveID").is(":checked")){
            let userLoginId = $("input[name='ID']").val();
            setCookie("userLoginId", userLoginId, 30);
        }
    });
}

function setCookie(cookieName, value, exdays){
    var exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}
 
function deleteCookie(cookieName){
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName){
    cookieName = cookieName + '=';
    let cookieData = document.cookie;
    let start =cookieData.indexOf(cookieName);
    let cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }

    return unescape(cookieValue);
}