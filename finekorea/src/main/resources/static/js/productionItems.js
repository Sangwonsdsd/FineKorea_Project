// 각 슬라이드의 이동 폭(px): 화면 너비에 따라 다름
const slideWidth3 = -150;
const slideWidth2 = -200;
const slideWidth = -325;

// 캐러셀별 현재 위치(인덱스). 캐러셀 id를 키로 사용해 서로 영향을 주지 않도록 분리
// (예전에는 전역 변수 current/number 하나를 모든 캐러셀이 공유해서,
//  다른 상품으로 넘어가면 첫 클릭이 "초기화"로만 동작해 두 번씩 눌러야 했음)
const slidePositions = {};

function getSlideWidth() {
    if (window.innerWidth >= 1024) return slideWidth;
    if (window.innerWidth > 540) return slideWidth2;
    return slideWidth3;
}

/**
 * dir: -1(이전) / 1(다음)
 * carouselId: 움직일 .carousel 요소의 id
 * cellClass: 그 캐러셀 안 슬라이드 하나하나의 class 이름
 */
function moveSlide(carouselId, cellClass, dir) {
    const carousel = document.getElementById(carouselId);
    if (!carousel) return;
    const last = document.querySelectorAll('.' + cellClass).length - 1;
    if (last <= 0) return; // 이미지가 1장이면 이동할 것이 없음

    let index = (slidePositions[carouselId] || 0) + dir;
    if (index > last) index = 0;      // 마지막에서 다음 -> 처음
    if (index < 0) index = last;      // 처음에서 이전 -> 마지막

    slidePositions[carouselId] = index;
    carousel.style.transform = `translateX(${index * getSlideWidth()}px)`;
}

// 정적(하드코딩) 상품: #carousel{num}, .carousel-cell{num}
function prev(num) { moveSlide('carousel' + num, 'carousel-cell' + num, -1); }
function next(num) { moveSlide('carousel' + num, 'carousel-cell' + num, 1); }

// DB에서 불러온 상품: #carouselId{num}, .carousel-cellNew{num}
function prevWrite(num) { moveSlide('carouselId' + num, 'carousel-cellNew' + num, -1); }
function nextWrite(num) { moveSlide('carouselId' + num, 'carousel-cellNew' + num, 1); }

// 화면 크기가 바뀌면 현재 위치를 새 폭으로 다시 맞춤
window.addEventListener('resize', function () {
    Object.keys(slidePositions).forEach(function (id) {
        const carousel = document.getElementById(id);
        if (carousel) {
            carousel.style.transform = `translateX(${slidePositions[id] * getSlideWidth()}px)`;
        }
    });
});

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
