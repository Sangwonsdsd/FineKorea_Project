$(function() {
    const send = document.getElementById('okButton');

    // 스팸 방지: 페이지를 연 시각 기록 (서버에서 너무 빠른 제출을 봇으로 판단)
    document.getElementById('quoteFormTs').value = Date.now();

    // 서버에서 거부된 경우 안내
    const err = new URLSearchParams(window.location.search).get('error');
    if (err === 'limit') {
        alert("짧은 시간에 너무 많이 전송했습니다. 잠시 후 다시 시도해주세요.");
    } else if (err === 'invalid') {
        alert("입력 내용을 확인해주세요. (이름, 전화번호 숫자 9~11자리, 이메일 형식, 한글 내용)");
    }

    send.addEventListener('click',function(){
        const form = document.getElementById("postQuote");
        let name = document.getElementById("quoteContactInputName");
        let phone = document.getElementById("quoteContactInputPhone");
        let email = document.getElementById("quoteContactInputEmail");
        let checkBox = $('#securityCheck').is(':checked');
        if(name.value.trim() == "" || phone.value.trim() == "" || email.value.trim() == ""){
            alert("필수 항목은 입력해주세요(이름, 전화번호, 이메일)");
            return false;
        }

        if(!checkBox){
            alert("개인정보취급방침에 동의해주세요.");
            return false;
        }
        form.action = "/guest/quote";
        form.mothod = "POST";
        form.submit();
    });
});