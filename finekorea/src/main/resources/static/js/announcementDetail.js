
$(document).ready(function(){
    ajaxOnloadNext();
    TextArea();
});

function TextArea(){
    $('#target-textarea').each(function () {
        this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    }).on('input', function () {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    });
}

function ajaxOnloadNext(){
    const communityNum = document.getElementById('communityNum').value;
    $.ajax({
        type : 'POST',
        url : "/guest/community/next?id=" + communityNum,
        success : function(res){
            drawNext(res)
        },
        error:function(){
            console.log("통신실패");
        },
    })
}

function drawNext(res){
    document.getElementById("nextCommunity").innerHTML = "";
    let nextStr = "";
    const communityNum = document.getElementById('communityNum').value;

    if(res.length == 0){
        nextStr += '<a class="detailNextbtn1">'
                nextStr += '<h3 class="detailNextbtnh3"></h3>'
                nextStr += '<div class="detailNextbtndiv">'
                nextStr += '이전글, 다음글이 없습니다.'
                nextStr += '</div>'
                nextStr += '</a>'
    }

    for(let i = 0; i < res.length; i++){
        if(res.length == 1){
            if(res[i].communityId < communityNum){
                nextStr += '<a href="/guest/community/view?id=' + res[i].communityId + '" class="detailNextbtn3">'
                nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-up detailNextbtnh3" viewBox="0 0 16 16">'
                nextStr += '<path fill-rule="evenodd" d="M8 10a.5.5 0 0 0 .5-.5V3.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 3.707V9.5a.5.5 0 0 0 .5.5m-7 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5"/>'
                nextStr += '</svg>'
                nextStr += '<h3 class="detailNextbtnh3">이전글</h3>'
                nextStr += '<div class="detailNextbtndiv">'
                nextStr += res[i].title
                nextStr += '</div>'
                nextStr += '</a>'
            }else if(res[i].communityId > communityNum){
                nextStr += '<a href="/guest/community/view?id=' + res[i].communityId + '" class="detailNextbtn3">'
                nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-down detailNextbtnh3" viewBox="0 0 16 16">'
                nextStr += '<path fill-rule="evenodd" d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5M8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6"/>'
                nextStr += '</svg>'
                nextStr += '<h3 class="detailNextbtnh3">다음글</h3>'
                nextStr += '<div class="detailNextbtndiv">'
                nextStr += res[i].title
                nextStr += '</div>'
                nextStr += '</a>'
            }
        }else{
            if(res[i].communityId < communityNum){
                nextStr += '<a href="/guest/community/view?id=' + res[i].communityId + '" class="detailNextbtn1">'
                nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-up detailNextbtnh3" viewBox="0 0 16 16">'
                nextStr += '<path fill-rule="evenodd" d="M8 10a.5.5 0 0 0 .5-.5V3.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 3.707V9.5a.5.5 0 0 0 .5.5m-7 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5"/>'
                nextStr += '</svg>'
                nextStr += '<h3 class="detailNextbtnh3">이전글</h3>'
                nextStr += '<div class="detailNextbtndiv">'
                nextStr += res[i].title
                nextStr += '</div>'
                nextStr += '</a>'
            }else if(res[i].communityId > communityNum){
                nextStr += '<a href="/guest/community/view?id=' + res[i].communityId + '" class="detailNextbtn2">'
                nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-down detailNextbtnh3" viewBox="0 0 16 16">'
                nextStr += '<path fill-rule="evenodd" d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5M8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6"/>'
                nextStr += '</svg>'
                nextStr += '<h3 class="detailNextbtnh3">다음글</h3>'
                nextStr += '<div class="detailNextbtndiv">'
                nextStr += res[i].title
                nextStr += '</div>'
                nextStr += '</a>'
            }
        }
    }

    document.getElementById("nextCommunity").innerHTML = nextStr;
    
}

