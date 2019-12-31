$(function(){
  $(".error-pass, .error-account").hide();
  $(".overlay").hide();
  $(".confirmation").hide();

})


function validateForm() {
  var countErrors = 0;
  var accountInput = $("#account");
  var passInput = $("#password");
  if(passInput.val().length < 6 || passInput.val().length > 18) {
    $(".error-pass").fadeIn();
    $(".pass-msg").html("密码长度为6-18位");
    $(passInput).addClass("warning");
    countErrors++;
  } else {
    $(passInput).removeClass("warning");
  }

  setTimeout(function showErrorMsg() {
    $(".error-account, .error-pass").fadeOut();
  }, 2000);



    if(countErrors === 0) {
        $.ajax({
            url:"/login",
            type:"post",
            data:{"account":accountInput.val(), "password":passInput.val()},
            success:function (result) {
                if (result == "1") {
                    $(".overlay").show();
                    $(".confirmation").show();
                    setTimeout(function() {
                        $(".overlay").hide();
                        $(".confirmation").hide();
                    }, 1500);
                    setTimeout(function() {
                        location.href="/index";
                    }, 1500);

                } else if(result == "0"){
                    $(".error-account").fadeIn();
                    $(".account-msg").html("该用户未被启用");
                    $(accountInput).addClass("warning");
                    setTimeout(function showErrorMsg() {
                        $(accountInput).removeClass("warning");
                        $(".error-account").fadeOut();
                    }, 2000);
                }else {
                    $(".error-account").fadeIn();
                    $(".account-msg").html("用户名或密码错误");
                    $(accountInput).addClass("warning");
                    setTimeout(function showErrorMsg() {
                        $(accountInput).removeClass("warning");
                        $(".error-account").fadeOut();
                    }, 2000);
                }
            }
        });

    }
}

$(document).keyup(function(event){
    if(event.keyCode ==13){
        validateForm();
    }
});
