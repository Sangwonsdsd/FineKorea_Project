function questionView(num){
    document.getElementById("passwordModal").style.display = "block";
    document.getElementById("passwordModelNum").value = num;
}

// 패스워드 입력 팝업 닫기
function closePasswordInput() {
    document.getElementById("passwordModal").style.display = "none";
}


function searchQuestion(event){
    let keyword = document.getElementById('questionsearchbar').value

    if(!keyword.trim()){
        location.href = "/guest/questionList";
    }else{
        if(event.keyCode === 13 || !event.keyCode){
            let page = 0;
            ajaxSearchQuestion(keyword, page);
            document.getElementById('questionsearchbar').value = "";
        }
    }
}

function ajaxSearchQuestion(keyword, page){
    $.ajax({
        url : "/guest/question/keyword",
        data: {
            keyword: keyword,
            page : page
        },
        success: function(res){
            if(res.content.length > 0){
                drawSearchQuestion(res);
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

function drawSearchQuestion(res){
    let resultArray = res.content;
    let questionStr = "";
    let tbodyQuestion = document.getElementById("tbodyQuestion");

    for(let i=0; i < resultArray.length; i++){
        questionStr += "<tr align = 'center' height = '40'>"
            questionStr += "<td>"
                questionStr += "<span>"
                    questionStr += resultArray[i].num
                questionStr += "</span>"
            questionStr += "</td>"

            questionStr += "<td align='start'>"
                    if(resultArray[i].writingCheck){
                        questionStr += "<a onclick =" + "questionView('" + resultArray[i].num +"')"+">" + resultArray[i].title
                    }else{
                        questionStr += "<a href=" + '/guest/question/view?num='+ resultArray[i].num +">" + resultArray[i].title
                    }
                questionStr += "</a>"
            questionStr += "</td>"

            questionStr += "<td>"
                questionStr += "<span>"
                    questionStr += resultArray[i].ninkName
                questionStr += "</span>"
            questionStr += "</td>"

            questionStr += "<td>"
                questionStr += "<span>"
                    questionStr += formatDate(resultArray[i].createdDate);
                questionStr += "</span>"
            questionStr += "</td>"

            questionStr += "<td>"
                questionStr += "<span>"
                    questionStr += resultArray[i].view
                questionStr += "</span>"
            questionStr += "</td>"

        questionStr += "</tr>"

    }

    tbodyQuestion.innerHTML = questionStr;

}

function drawPagination(res, keyword){
    let totalPages = res.totalPages;
    let pageable = res.pageable;
    let number = res.number;
    let paginationStr = "";

    if(totalPages > 0){
        paginationStr += "<ul>"
            if(number !== 0){
                paginationStr += "<li>" + '<a onclick="ajaxSearchQuestion('+ "'"+ keyword + "'," + (number-1) + ')">'
                    paginationStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chevron-left" viewBox="0 0 16 16">'
                    paginationStr += '<path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0"/>'
                    paginationStr += '</svg>'
                paginationStr += '</a></li>'
            }
            for(let num = 1; num <= totalPages; num++){
                paginationStr += '<li>'
                    paginationStr += '<a onclick="ajaxSearchQuestion('+ "'"+ keyword + "'," + number + ')">'
                    paginationStr += num
                    paginationStr += '</a>'
                paginationStr += '</li>'
            }

            if(totalPages != (number+1)){
                paginationStr += "<li>" + '<a onclick="ajaxSearchQuestion('+ "'"+ keyword + "'," + (number+1) + ')">'
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