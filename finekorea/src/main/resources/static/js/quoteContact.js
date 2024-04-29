$(function() {
    const send = document.getElementById('okButton');

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