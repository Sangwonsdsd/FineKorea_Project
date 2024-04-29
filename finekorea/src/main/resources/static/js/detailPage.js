    function resize() {
        let textarea = document.getElementById('target-textarea');
 
        textarea.style.height = '0px';
 
        let scrollHeight = textarea.scrollHeight;
        let style = window.getComputedStyle(textarea);
        let borderTop = parseInt(style.borderTop);
        let borderBottom = parseInt(style.borderBottom);
 
        textarea.style.height = (scrollHeight + borderTop + borderBottom)+'px';
    }
    
    window.addEventListener('load', resize);
    window.onresize = resize;


    function questionUpdateView(num){
        document.getElementById("passwordUpdateModal").style.display = "block";
        document.getElementById("passwordUpdatelNum").value = num;
    }
    
    // 패스워드 입력 팝업 닫기
    function closePasswordInput1() {
        document.getElementById("passwordUpdateModal").style.display = "none";
    }
    
    function questionDelete(num){
        document.getElementById("passwordDeleteModal").style.display = "block";
        document.getElementById("passwordDeletelNum").value = num;
    }
    
    // 패스워드 입력 팝업 닫기
    function closePasswordInput2() {
        document.getElementById("passwordDeleteModal").style.display = "none";
    }

    function questionNext(num){
        document.getElementById("passwordNextModal").style.display = "block";
        document.getElementById("passwordNextlNum").value = num;
    }
    
    // 패스워드 입력 팝업 닫기
    function closePasswordInput3() {
        document.getElementById("passwordNextModal").style.display = "none";
    }

    function questionCommentWrite(num){
        const ninkName = $('input#commentQNameInput').val();
        const password = $('input#commentQPasswordInput').val();
        const comment = $('textarea#commentQuestion').val();
        if(ninkName === "관리자"){
            alert("관리자 닉네임은 사용할 수 없습니다.");
        }else{
            if(password === ""){
                alert("비밀번호를 입력해주세요.");
            }else{
                let questionCommentDto = {
                    ninkName: ninkName,
                    password: password,
                    comment: comment
                };

                questionCommentAjax(num, questionCommentDto);
            }
        }
    }
    
    function questionCommentAjax(num, questionCommentDto){
        $.ajax({
            type : 'POST',
            url : "/guest/question/comment/write?num="+ num,
            contentType: 'application/json',
            data : JSON.stringify(questionCommentDto),
            success : function(res){
                drawQuestionComment(res);
            },
            error:function(){
                console.log("통신실패");
            },
        })
    }

    $(document).ready(function(){
        ajaxOnloadCommentView();
        ajaxOnloadNext();

        TextArea();
    });

    function TextArea(){
        $('.detailpageContent').each(function () {
            this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
        }).on('input', function () {
            this.style.height = 'auto';
            this.style.height = (this.scrollHeight) + 'px';
        });
    }

    function ajaxOnloadNext(){
        const questionNum = document.getElementById('questionNumhidden').value;
        $.ajax({
            type : 'POST',
            url : "/guest/question/next?id=" + questionNum,
            success : function(res){
                drawNext(res)
            },
            error:function(){
                console.log("통신실패");
            },
        })
    }
    
    function drawNext(res){
        document.getElementById("nextQuestion").innerHTML = "";
        let nextStr = "";
        const questionNum = document.getElementById('questionNumhidden').value;

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
                if(res[i].num < questionNum){
                    if(!res[i].writingCheck){
                        nextStr += '<a href="/guest/question/view?num=' + res[i].num + '" class="detailNextbtn3">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-up detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M8 10a.5.5 0 0 0 .5-.5V3.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 3.707V9.5a.5.5 0 0 0 .5.5m-7 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">이전글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</a>'
                    }else{
                        nextStr += '<button onclick="questionNext(' + res[i].num + ')" class="detailNextbtn3">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-up detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M8 10a.5.5 0 0 0 .5-.5V3.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 3.707V9.5a.5.5 0 0 0 .5.5m-7 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">이전글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</button>'
                    }
                }else if(res[i].num > questionNum){
                    if(!res[i].writingCheck){
                        nextStr += '<a href="/guest/question/view?num=' + res[i].num + '" class="detailNextbtn3">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-down detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5M8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">다음글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</a>'
                    }else{
                        nextStr += '<button onclick="questionNext(' + res[i].num + ')" class="detailNextbtn3">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-down detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5M8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">다음글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</button>'
                    }

                }
            }else{
                if(res[i].num < questionNum){
                    if(!res[i].writingCheck){
                        nextStr += '<a href="/guest/question/view?num=' + res[i].num + '" class="detailNextbtn1">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-up detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M8 10a.5.5 0 0 0 .5-.5V3.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 3.707V9.5a.5.5 0 0 0 .5.5m-7 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">이전글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</a>'
                    }else{
                        nextStr += '<button onclick="questionNext(' + res[i].num + ')" class="detailNextbtn1">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-up detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M8 10a.5.5 0 0 0 .5-.5V3.707l2.146 2.147a.5.5 0 0 0 .708-.708l-3-3a.5.5 0 0 0-.708 0l-3 3a.5.5 0 1 0 .708.708L7.5 3.707V9.5a.5.5 0 0 0 .5.5m-7 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">이전글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</button>'
                    }

                }else if(res[i].num > questionNum){
                    if(!res[i].writingCheck){
                        nextStr += '<a href="/guest/question/view?num=' + res[i].num + '" class="detailNextbtn2">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-down detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5M8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">다음글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</a>'
                    }else{
                        nextStr += '<button onclick="questionNext(' + res[i].num + ')" class="detailNextbtn2">'
                        nextStr += '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-bar-down detailNextbtnh3" viewBox="0 0 16 16">'
                        nextStr += '<path fill-rule="evenodd" d="M1 3.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5M8 6a.5.5 0 0 1 .5.5v5.793l2.146-2.147a.5.5 0 0 1 .708.708l-3 3a.5.5 0 0 1-.708 0l-3-3a.5.5 0 0 1 .708-.708L7.5 12.293V6.5A.5.5 0 0 1 8 6"/>'
                        nextStr += '</svg>'
                        nextStr += '<h3 class="detailNextbtnh3">다음글</h3>'
                        nextStr += '<div class="detailNextbtndiv">'
                        nextStr += res[i].title
                        nextStr += '</div>'
                        nextStr += '</button>'
                    }

                }
            }
        
        }
        document.getElementById("nextQuestion").innerHTML = nextStr;
        
    }

    function ajaxOnloadCommentView(){
        let num = document.getElementById('questionNumhidden').value
        $.ajax({
            type : 'POST',
            url : "/guest/question/comment/view?num="+ num,
            contentType: 'application/json',
            success : function(res){
                drawQuestionComment(res);
            },
            error:function(){
                console.log("통신실패");
            },
        })
    }

    function questionCommentUpdate(id){
        let num = document.getElementById('questionNumhidden').value;
        const ninkName = document.getElementById('commentUpdateNameInput' + id).value;
        const password = document.getElementById('commentUpdatePasswordInput' + id).value;
        const comment = document.getElementById('commentUpdateWriter' + id).value;

        if(ninkName === "관리자"){
            alert("관리자 닉네임은 사용할 수 없습니다.");
        }else{
            if(password.length > 0){
                let questionCommentDto = {
                    id: id,
                    ninkName: ninkName,
                    password: password,
                    comment: comment
                };
                ajaxQuestionCommentUpdate(num, questionCommentDto);
            }else{
                alert("비밀번호를 입력하세요.")
            }
        }

    }

    function ajaxQuestionCommentUpdate(num, questionCommentDto){
        
        $.ajax({
            url: "/guest/question/comment/update?num="+ num,
            type: "POST",
            contentType: 'application/json',
            data : JSON.stringify(questionCommentDto),
            success: function(res){
                if(res.length > 0){
                    drawQuestionComment(res);
                }else{
                    alert("비밀번호가 틀렸습니다.");
                    ajaxOnloadCommentView();
                }
            },
            error: function(){
                ajaxOnloadCommentView();

            }
        })
    }

    function questionCommentDelete(id){
        let num = document.getElementById('questionNumhidden').value;
        const password = document.getElementById('commentDeletePasswordInput' + id).value;
        if(password.length > 0){
            let questionCommentDto = {
                id: id,
                password: password
            };
    
            ajaxQuestionCommentDelete(num, questionCommentDto);
        }else{
            alert("비밀번호를 입력해주세요");
        }

    }

    function ajaxQuestionCommentDelete(num, questionCommentDto){
        $.ajax({
            url: "/guest/question/comment/delete?num="+ num,
            type: "POST",
            contentType: 'application/json',
            data : JSON.stringify(questionCommentDto),
            success: function(res){
                if(res.length > 0){
                    drawQuestionComment(res);
                }else{
                    alert("내용이 없습니다.");
                    ajaxOnloadCommentView();
                }
            },
            error: function(){
                ajaxOnloadCommentView();
            }
        })
    }

    function drawQuestionComment(res){
        let commentStr = "";
        for(let comment of res){
            commentStr += '<div class = "comment">'
                                + '<div class="commentTextBox">'
                                    + '<div class="commentTextL">'
                                        + '<h3>'
                                            + comment.ninkName
                                        + '</h3>'
                                        + '<div>'
                                            + comment.modifiedDate
                                        + '</div>'
                                    + '</div>'
                                    + '<div class="commentTextR">'
                                        + comment.comment
                                    + '</div>'
                                + '</div>'
                                + '<div  id="commentUpdateContainer'+ comment.id +'"' + 'class="commentUpdateContainer">'
                                    + '<div class="commentUpdateInfo">'
                                        + '<div class="commentUpdateName">'
                                            + '<label for="commentUpdateNameInput'+ comment.id +'" class="commentUpdateLabel">이름</label>'
                                            + ' <input type="text" id= "commentUpdateNameInput'+ comment.id +'" value='+ comment.ninkName + ' class="commentUpdateInput">'
                                        + '</div>'
                                        + '<div class="commentUpdateName">'
                                            + '<label for="commentUpdatePasswordInput'+ comment.id +'" class="commentUpdateLabel">비밀번호</label>'
                                            + ' <input type="password" id = "commentUpdatePasswordInput'+ comment.id +'" name="password" class="commentUpdateInput">'
                                        + '</div>'
                                   + '</div>'
                                   + ' <textarea class="commentUpdateWriter" id = "commentUpdateWriter'+ comment.id +'" name = "comment" rows="5">' + comment.comment + '</textarea>'
                                   + '<div class="commentUpdateWriterbtnbox">'
                                        + '<button onclick = "questionCommentUpdate('+ comment.id + ')"class = "commentUpdateWriterbtn">수정하기</button>'
                                   +'</div>'
                                + '</div>'

                                + '<div  id="commentDeleteContainer'+ comment.id +'"' + 'class="commentDeleteContainer">'
                                + '<div class="commentDeleteInfo">'
                                    + '<div class="commentDeleteName">'
                                        + '<label for="commentDeletePasswordInput'+ comment.id +'" class="commentDeleteLabel">비밀번호</label>'
                                        + ' <input type="password" id = "commentDeletePasswordInput'+ comment.id +'" name="password" class="commentDeleteInput">'
                                    + '</div>'
                               + '</div>'
                               + '<div class="commentDeleteWriterbtnbox">'
                                    + '<button onclick = "questionCommentDelete('+ comment.id + ')"class = "commentDeleteWriterbtn">삭제하기</button>'
                               +'</div>'
                            + '</div>'

                                + '<div class="commentUpdateBtnBox">'
                                    + '<button onclick="questionCommentUpdateform(' + comment.id + ')" class="detailbtn"><svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">'
                                        + '<path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492M5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0"/>'
                                        + '<path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115z"/>'
                                        + '</svg>&nbsp;수정' 
                                    + '</button>'
                                    + '<button onclick="questionCommentDeleteform(' + comment.id + ')" class="detailbtn"><svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">'
                                       + '<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>'
                                       + '<path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>'
                                       + '</svg>&nbsp;삭제'
                                    + '</button>'
                                + '</div>'
                        + '</div>'
        }

        document.getElementById('commentsize').innerHTML = res.length;
        document.getElementById('commentheaderContainer').innerHTML = "";
        document.getElementById('commentheaderContainer').innerHTML = commentStr;
        document.getElementById('commentQNameInput').value = "";
        document.getElementById('commentQPasswordInput').value = "";
        document.getElementById('commentQuestion').value = "";
    }

    
    function questionCommentUpdateform(num){
        if(document.getElementById("commentUpdateContainer" + num).style.display === "flex"){
            document.getElementById("commentUpdateContainer" + num).style.display = "none";
        }else{
            document.getElementById("commentUpdateContainer" + num).style.display = "flex"
        }
    }

        
    function questionCommentDeleteform(num){
        if(document.getElementById("commentDeleteContainer" + num).style.display === "flex"){
            document.getElementById("commentDeleteContainer" + num).style.display = "none";
        }else{
            document.getElementById("commentDeleteContainer" + num).style.display = "flex"
        }
    }