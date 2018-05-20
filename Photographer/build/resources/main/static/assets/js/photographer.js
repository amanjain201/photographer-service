$(document).ready(function () {
    if (getCookie("user") === "admin@gmail.com") {
        $(".card-action").hide();
    } else {
        $(".card-action").show();
    }

});


$('.datepicker').pickadate({
    min: new Date(),
    max: 30,
//    selectMonths: false, // Creates a dropdown to control month
//    selectYears: 1, // Creates a dropdown of 15 years to control year,
    format: 'dd/mm/yyyy',
    today: 'Today',
    clear: 'Clear',
    close: 'Ok',
    closeOnSelect: false // Close upon selecting a date,
});



function doBooking(pid) {
    var date;
    if (pid === 'p1') {
        date = $("#date1").val();
    } else if (pid === 'p2') {
        date = $("#date2").val();
    } else if (pid === 'p3') {
        date = $("#date3").val();
    }

    if (date === "") {
        swal({
            icon: "error",
            title: "Please enter date"
        });
        return;
    }
    console.log(getCookie("user"));
    var request = {};
    request.pid = pid;
    request.email = getCookie("user");
    request.date = date;
    request.status = 'PENDING';

    var bookUrl = "http://" + host + "/book";
    $.ajax({
        async: false,
        url: bookUrl,
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

}
    