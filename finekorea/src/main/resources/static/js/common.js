document.addEventListener("DOMContentLoaded", function() {
    console.log("문서가 완전히 로드되고 준비되었습니다!");

    document.getElementById('menu-toggle2').onmouseover = function(){
        document.getElementById('subMenu-toggle2').style.visibility = 'visible';
        document.getElementById('subMenu-toggle2').style.opacity = 10;
        document.getElementById('subMenu-toggle2').style.borderTop = '1px solid #a3894c';
    };
    
    document.getElementById('menu-toggle2').onmouseout = function(){
        setTimeout(function() {
            if (!document.getElementById('menu-toggle2').matches(':hover') && !document.getElementById('subMenu-toggle2').matches(':hover')) {
                document.getElementById('subMenu-toggle2').style.visibility = 'hidden';
                document.getElementById('subMenu-toggle2').style.opacity = 0;
                document.getElementById('subMenu-toggle2').style.borderTop = 'none';
            }
        }, 200); 
    };

    document.getElementById('subMenu-toggle2').onmouseover = function(){
        document.getElementById('subMenu-toggle2').style.visibility = 'visible';
        document.getElementById('subMenu-toggle2').style.opacity = 10;
    };
    
    document.getElementById('subMenu-toggle2').onmouseout = function(){
        setTimeout(function() {
            if (!document.getElementById('menu-toggle2').matches(':hover') && !document.getElementById('subMenu-toggle2').matches(':hover')) {
                document.getElementById('subMenu-toggle2').style.visibility = 'hidden';
                document.getElementById('subMenu-toggle2').style.opacity = 0;
            }
        }, 200); 
    };

    document.getElementById('menu-toggle3').onmouseover = function(){
        document.getElementById('subMenu-toggle3').style.visibility = 'visible';
        document.getElementById('subMenu-toggle3').style.opacity = 10;
        document.getElementById('subMenu-toggle3').style.borderTop = '1px solid #a3894c';
    };
    
    document.getElementById('menu-toggle3').onmouseout = function(){
        setTimeout(function() {
            if (!document.getElementById('menu-toggle3').matches(':hover') && !document.getElementById('subMenu-toggle3').matches(':hover')) {
                document.getElementById('subMenu-toggle3').style.visibility = 'hidden';
                document.getElementById('subMenu-toggle3').style.opacity = 0;
                document.getElementById('subMenu-toggle3').style.borderTop = 'none';
            }
        }, 200); 
    };

    document.getElementById('subMenu-toggle3').onmouseover = function(){
        document.getElementById('subMenu-toggle3').style.visibility = 'visible';
        document.getElementById('subMenu-toggle3').style.opacity = 10;
    };
    
    document.getElementById('subMenu-toggle3').onmouseout = function(){
        setTimeout(function() {
            if (!document.getElementById('menu-toggle3').matches(':hover') && !document.getElementById('subMenu-toggle3').matches(':hover')) {
                document.getElementById('subMenu-toggle3').style.visibility = 'hidden';
                document.getElementById('subMenu-toggle3').style.opacity = 0;
            }
        }, 200); 
    };
    
    document.getElementById('menu-toggle4').onmouseover = function(){
        document.getElementById('subMenu-toggle4').style.visibility = 'visible';
        document.getElementById('subMenu-toggle4').style.opacity = 10;
        document.getElementById('subMenu-toggle4').style.borderTop = '1px solid #a3894c';
    };
    
    document.getElementById('menu-toggle4').onmouseout = function(){
        setTimeout(function() {
            if (!document.getElementById('menu-toggle4').matches(':hover') && !document.getElementById('subMenu-toggle4').matches(':hover')) {
                document.getElementById('subMenu-toggle4').style.visibility = 'hidden';
                document.getElementById('subMenu-toggle4').style.opacity = 0;
                document.getElementById('subMenu-toggle4').style.borderTop = 'none';
            }
        }, 200); 
    };

    document.getElementById('subMenu-toggle4').onmouseover = function(){
        document.getElementById('subMenu-toggle4').style.visibility = 'visible';
        document.getElementById('subMenu-toggle4').style.opacity = 10;
    };
    
    document.getElementById('subMenu-toggle4').onmouseout = function(){
        setTimeout(function() {
            if (!document.getElementById('menu-toggle4').matches(':hover') && !document.getElementById('subMenu-toggle4').matches(':hover')) {
                document.getElementById('subMenu-toggle4').style.visibility = 'hidden';
                document.getElementById('subMenu-toggle4').style.opacity = 0;
            }
        }, 200); 
    };

    document.getElementById('header-container').onmouseover = function(){
        document.getElementById('header-container').style.background = 'white';
        document.getElementById('header-container').style.color = '#222';
        document.getElementById('logo-fk').src =  '/image/logoImg/흰_바탕_-removebg-preview.png';
        document.getElementById('header-container').style.borderBottom = '0.5px solid #a3894c';
    }


    document.getElementById('header-container').onmouseout = function(){
        setTimeout(function() {
            if (!document.getElementById('header-container').matches(':hover') && !document.getElementById('logo-fk').matches(':hover')) {
                document.getElementById('header-container').style.background = 'black';
                document.getElementById('header-container').style.color = 'white';
                document.getElementById('logo-fk').src = '/image/logoImg/검_바탕_-removebg-preview.png';

            }
        }, 200); 

    }
});