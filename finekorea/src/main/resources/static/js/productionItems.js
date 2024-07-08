let current = 0;
let number = 1;


const slideWidth3 = -150;
const slideWidth2 = -200;
const slideWidth = -325; // 각 슬라이드의 너비

function prev(num) {
    const carousel_Length = document.querySelectorAll('.carousel-cell' + num).length - 1;
    const carousel = document.querySelector("#carousel" + num);
    if(window.innerWidth >= 1024){
        if (current !== 0 && number == num) {
            current--;
            carousel.style.transform = `translateX(${current * slideWidth}px)`;
        } else {
            current = carousel_Length;
            carousel.style.transform = `translateX(${carousel_Length * slideWidth}px)`;
        }
        number = num
    }else if(window.innerWidth > 540){
        if (current !== 0 && number == num) {
            current--;
            carousel.style.transform = `translateX(${current * slideWidth2}px)`;
        } else {
            current = carousel_Length;
            carousel.style.transform = `translateX(${carousel_Length * slideWidth2}px)`;
        }
        number = num
    }else{
        if (current !== 0 && number == num) {
            current--;
            carousel.style.transform = `translateX(${current * slideWidth3}px)`;
        } else {
            current = carousel_Length;
            carousel.style.transform = `translateX(${carousel_Length * slideWidth3}px)`;
        }
        number = num
    }
}

function next(num) {
    const carousel_Length = document.querySelectorAll('.carousel-cell'+num).length - 1;
    const carousel = document.querySelector("#carousel" + num);

    if(window.innerWidth >= 1024){
        if(current !== carousel_Length && number == num){
            carousel.style.transform = `translateX(${(current + 1) * slideWidth}px)`;
            current++;
        }else{
            current = 0;
            carousel.style.transform = `translateX(0px)`;
        }
        number = num
    }else if(window.innerWidth > 540){
        if(current !== carousel_Length && number == num){
            carousel.style.transform = `translateX(${(current + 1) * slideWidth2}px)`;
            current++;
        }else{
            current = 0;
            carousel.style.transform = `translateX(0px)`;
        }
        number = num
    }else{
        if(current !== carousel_Length && number == num){
            carousel.style.transform = `translateX(${(current + 1) * slideWidth3}px)`;
            current++;
        }else{
            current = 0;
            carousel.style.transform = `translateX(0px)`;
        }
        number = num
    }
}

$(function() {
    $(window).scroll(function(){
        if($(this).scrollTop() > 500){
            $('#upbutton').fadeIn();
        }else{
            $('#upbutton').fadeOut();
        }
    });

    $("#upbutton").click(function(){
        $('html, body').animate({
            scrollTop : 0
        }, 100);
        return false;
    });    
});


function prevWrite(num) {
    const carousel_Length = document.querySelectorAll('.carousel-cellNew' + num).length - 1;
    const carousel = document.querySelector("#carouselId" + num);
    if(window.innerWidth >= 1024){
        if (current !== 0 && number == num) {
            current--;
            carousel.style.transform = `translateX(${current * slideWidth}px)`;
        } else {
            current = carousel_Length;
            carousel.style.transform = `translateX(${carousel_Length * slideWidth}px)`;
        }
        number = num
    }else if(window.innerWidth > 540){
        if (current !== 0 && number == num) {
            current--;
            carousel.style.transform = `translateX(${current * slideWidth2}px)`;
        } else {
            current = carousel_Length;
            carousel.style.transform = `translateX(${carousel_Length * slideWidth2}px)`;
        }
        number = num
    }else{
        if (current !== 0 && number == num) {
            current--;
            carousel.style.transform = `translateX(${current * slideWidth3}px)`;
        } else {
            current = carousel_Length;
            carousel.style.transform = `translateX(${carousel_Length * slideWidth3}px)`;
        }
        number = num
    }
    
}

function nextWrite(num) {
    const carousel_Length = document.querySelectorAll('.carousel-cellNew'+num).length - 1;
    const carousel = document.querySelector("#carouselId" + num);
    if(window.innerWidth >= 1024){
        if(current !== carousel_Length && number == num){
            carousel.style.transform = `translateX(${(current + 1) * slideWidth}px)`;
            current++;
        }else{
            current = 0;
            carousel.style.transform = `translateX(0px)`;
        }
        number = num
    }else if(window.innerWidth > 540){
        if(current !== carousel_Length && number == num){
            carousel.style.transform = `translateX(${(current + 1) * slideWidth2}px)`;
            current++;
        }else{
            current = 0;
            carousel.style.transform = `translateX(0px)`;
        }
        number = num
    }else {
        if(current !== carousel_Length && number == num){
            carousel.style.transform = `translateX(${(current + 1) * slideWidth3}px)`;
            current++;
        }else{
            current = 0;
            carousel.style.transform = `translateX(0px)`;
        }
        number = num
    }
    
}


$(document).ready(function(){
    $('#fileupload').on('change', function(){
        handleFileUpload();
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



