function scrollToSignUp() {
    $('html,body').animate({
        scrollTop: $(".second").offset().top},
            'slow');
}

$("#login").click(function (event) {
    event.preventDefault();
    var loginEmail = $("#emailLogin").val();
    var password = $("#password").val();
    
    var request = {};
    request.email = loginEmail;
    request.password = password;

    var loginUrl = "http://" + host + "/checkLogin";
    $.ajax({
        async: false,
        url: loginUrl,
        type: "POST",
        contentType: "application/json; charset=utf-8",
        accepts: "application/json",
        data: JSON.stringify(request),
        dataType: "json",
        crossDomain: true,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (jsonData) {
            if (jsonData.code === 200) {
                setCookie("user",loginEmail);
                window.location = "photographer";
            } else {
                swal({
                    icon: "error",
                    title: "Some error occured",
                    text: jsonData.message
                });
            }
        },
        error: function (request, textStatus, errorThrown) {
            var responseJson = JSON.parse(request.responseText);
            swal({
                icon: "error",
                title: "Some error occured",
                text: responseJson.message
            });
        }
    });

});

$("#signup").click(function (event) {
    event.preventDefault();
    var userName = $("#signupName").val();
    var email = $("#signupEmail").val();
    var password = $("#signUppassword").val();
    var phone = $("#phone").val();
    
    if(userName === "" || email === "" || password === "" || phone === ""){
        swal({
                icon: "error",
                title: "Please fill the form"
            });
        return;    
    }
    
    var patt = new RegExp("^[A-Za-z0-9]*[A-Za-z0-9][A-Za-z0-9_]*$");
    if(!patt.test(userName)){
        swal({
                icon: "error",
                title: "Username can contain only alphanumerics"
            });
        return;
    }
    
    if(!patt.test(password)){
        swal({
                icon: "error",
                title: "Password can contain only alphanumerics"
            });
        return;
    }
    var validEmailChars = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!validEmailChars.test(email)){
        swal({
                icon: "error",
                title: "Email format incorrect"
            });
        return;
    }
    if(!/^\d{10}$/.test(phone)){
        swal({
                icon: "error",
                title: "Incorrect phone number"
            });
        return;
    }
    //Front end validations required
    
    var request = {};
    request.userName = userName;
    request.email = email;
    request.password = password;
    request.phone = phone;

    var signUpUrl = "http://" + host + "/register";
    $.ajax({
        async: false,
        url: signUpUrl,
        type: "POST",
        contentType: "application/json; charset=utf-8",
        accepts: "application/json",
        data: JSON.stringify(request),
        dataType: "json",
        crossDomain: true,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (jsonData) {
            if (jsonData.code === 200) {
                swal({
                    icon: "success",
                    title: "Success",
                    text: jsonData.message
                });
                document.getElementById("signUpForm").reset();
            } else {
                swal({
                    icon: "error",
                    title: "Some error occured",
                    text: jsonData.message
                });
            }
        },
        error: function (request, textStatus, errorThrown) {
            var responseJson = JSON.parse(request.responseText);
            swal({
                icon: "error",
                title: "Some error occured",
                text: responseJson.message
            });
        }
    });

});


function showPopup() {

    MaterialDialog.alert(
            'Check your registered email for the reset link', // Alert Body (Acepts html tags)
            {
                title: 'Reset Link Sent', // Modal title
                modalType: "modal-small",
                buttons: {// Receive buttons (Alert only use close buttons)
                    close: {
                        text: 'close', //Text of close button
                        className: 'red' // Class of the close button

                    }
                }
            }
    );
}