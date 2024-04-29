


$(document).ready(function(){
    $('#fileupload').on('change', function(){
        handleFileUpload()
    })

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

function passCheck(t){
    let password = document.getElementById('writingPageInputPassword').value;
    let okButton = document.getElementById('okButton');

    if(!password.trim()){
        okButton.disabled = true;
        okButton.style.background = "#7e7e7e";
        alert("비밀번호를 입력하세요.");
    }else{
        okButton.disabled = !t.checked;
        okButton.style.background = "#000";
    }
}

function questionName(t){
    let okButton = document.getElementById('okButton');
    if(t.value.length > 6){
        alert("닉네임은 6글자까지 작성 가능합니다.");
        okButton.style.background = "#7e7e7e";
        okButton.disabled = true;
    }else{
        okButton.disabled = false;
        okButton.style.background = "#000";
    }
}
function questionTitle(t){
    if(t.value.length > 50){
        alert("제목은 50글자까지 작성 가능합니다.");
        okButton.style.background = "#7e7e7e";
        okButton.disabled = true;
    }else{
        okButton.disabled = false;
        okButton.style.background = "#000";
    }
}
function questionPass(t){
    if(t.value.length > 4){
        alert("비밀번호는 4글자까지 작성 가능합니다.");
        okButton.style.background = "#7e7e7e";
        okButton.disabled = true;
    }else{
        okButton.disabled = false;
        okButton.style.background = "#000";
    }
}

