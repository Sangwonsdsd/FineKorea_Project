$(document).ready(function(){
    $('#fileupload').on('change', function(){
        handleFileUpload()
    });
});


function handleFileUpload(){
    let uploadResult = $(".uploadResult");
    $('.uploadResult').html('');
    let inputFile = $('#fileupload');
    let files = inputFile[0].files;
    let str = "";

    for(let i = 0; i < files.length; i++){
        str += "<div>";
        str +=  "파일 이름 : " + files[i].name
        str += "</div>"
    }
    uploadResult.append(str);

}


function communityTitle(t){
    const okButton = document.getElementById('okButton');
    if(t.value.length > 50){
        alert("제목은 50글자까지 작성 가능합니다.");
        okButton.style.background = "#7e7e7e";
        okButton.disabled = true;
    }else{
        okButton.disabled = false;
        okButton.style.background = "#000";
    }
}



function searchCommunity(event){
    let keyword = document.getElementById('announcementSearchbar').value

    if(!keyword.trim()){
        location.href = "/guest/community";
    }else{
        if(event.keyCode === 13 || !event.keyCode){
            let page = 0;
            ajaxSearchCommunity(keyword, page);
            document.getElementById('announcementSearchbar').value = "";
        }
    }
}

function ajaxSearchCommunity(keyword, page){
    $.ajax({
        url : "/guest/community/keyword",
        data: {
            keyword: keyword,
            page : page
        },
        success: function(res){
            if(res.content.length > 0){
                drawSearchCommunity(res);
                drawPagination(res, keyword);
            }else{
                alert("검색 결과가 없습니다.");
            }

        },
        error: function(){
            console.log("검색 실패");
        },
    })
}

function drawSearchCommunity(res){
    let resultArray = res.content;
    let communityStr = "";
    let tbodyQuestion = document.getElementById("tbodyCommunity");

    for(let i=0; i < resultArray.length; i++){
        communityStr += '<tr align="center" height="40">'
            communityStr += '<td>'
                communityStr += resultArray[i].communityId
            communityStr += '</td>'

            communityStr += '<td align="start">'
                communityStr += '<a class="writingTableA" href ="/guest/community/view?id=' + resultArray[i].communityId + '">' 
                communityStr += resultArray[i].title
                communityStr += '</a>'
            communityStr += '</td>'
            communityStr += '<td>'
                communityStr += resultArray[i].writer
            communityStr += '</td>'
            communityStr += '<td>'
            communityStr += formatDate(resultArray[i].createdDate)
            communityStr += '</td>'
            communityStr += '<td>'
                communityStr += resultArray[i].view
            communityStr += '</td>'
        communityStr += '</tr>'
    }

    tbodyQuestion.innerHTML = communityStr;

}

function drawPagination(res, keyword){
    let totalPages = res.totalPages;
    let pageable = res.pageable;
    let number = res.number;
    let paginationStr = "";

    if(totalPages > 0){
        paginationStr += '<ul class="pageing">'
            if(number !== 0){
                paginationStr += "<li>" + '<a onclick="ajaxSearchCommunity('+ "'"+ keyword + "'," + (number-1) + ')">'
                    paginationStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">'
                    paginationStr += '<path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0"/>'
                    paginationStr += '</svg>'
                paginationStr += '</a></li>'
            }
            for(let num = 1; num <= totalPages; num++){
                paginationStr += '<li>'
                    paginationStr += '<a onclick="ajaxSearchCommunity('+ "'"+ keyword + "'," + number + ')">'
                    paginationStr += num
                    paginationStr += '</a>'
                paginationStr += '</li>'
            }

            if(totalPages != (number+1)){
                paginationStr += "<li>" + '<a onclick="ajaxSearchCommunity('+ "'"+ keyword + "'," + (number+1) + ')">'
                    paginationStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">'
                    paginationStr += '<path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0"/>'
                    paginationStr += '</svg>'
                paginationStr += '</a></li>'
            }
            
         paginationStr += "</ul>"
    }

    document.getElementById('pageing-box').innerHTML = paginationStr;
}

function formatDate(dateString){
    let date = new Date(dateString);
    let year = date.getFullYear();
    let month = ('0' + (date.getMonth() + 1)).slice(-2);
    let day = ('0' + date.getDate()).slice(-2);
    let hour = ('0' + date.getHours()).slice(-2);
    let minute = ('0' + date.getMinutes()).slice(-2);

    return year + '-' + month + '-' + day + ' ' + hour + ':' + minute;
}