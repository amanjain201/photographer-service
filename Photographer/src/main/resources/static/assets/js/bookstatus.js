$(document).ready(function () {
    if (getCookie("user") === "admin@gmail.com") {
        getAdminCards();
    } else {
        getUserCards();
    }

});

function getAdminCards() {
    $("#addcards").html('');
//    var email = localStorage.useremail;
    var getbookingurl = "http://" + host + "/getbookings?user=admin@gmail.com";
    $.ajax({
        async: false,
        url: getbookingurl,
        type: "GET",
//        contentType: "application/json; charset=utf-8",
        accepts: "application/json",
//        data: JSON.stringify(request),
        dataType: "json",
        crossDomain: true,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
//            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (jsonData) {
            if (jsonData.code === 200) {
                appendAdminCards(jsonData.allbookings);
            } else {
                swal({
                    icon: "error",
                    title: "Some error occured"
                });
            }
        },
        error: function (request, textStatus, errorThrown) {
            swal({
                icon: "error",
                title: "Some error occured"
            });
        }
    });

}

function appendAdminCards(arr) {
    for (var i = 0; i < arr.length; i++) {
        $("#addcards").append(getText(arr[i].id, arr[i].pid, arr[i].email, arr[i].date));
    }
}

function getUserCards() {
    $("#addcards").html('');
    var email = getCookie("user");
    var getbookingurl = "http://" + host + "/getbookings?user=" + email;
    $.ajax({
        async: false,
        url: getbookingurl,
        type: "GET",
//        contentType: "application/json; charset=utf-8",
        accepts: "application/json",
//        data: JSON.stringify(request),
        dataType: "json",
        crossDomain: true,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
//            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (jsonData) {
            if (jsonData.code === 200) {
                appendUserCards(jsonData.allbookings);
            } else {
                swal({
                    icon: "error",
                    title: "Some error occured"
                });
            }
        },
        error: function (request, textStatus, errorThrown) {
            swal({
                icon: "error",
                title: "Some error occured"
            });
        }
    });
}

function appendUserCards(arr) {
    var name;
    for (var i = 0; i < arr.length; i++) {
        switch (arr[i].pid) {
            case "p1":
                name = "Ramesh";
                break;
            case "p2":
                name = "Mahesh";
                break;
            case "p3":
                name = "Suresh";
                break;
        }
        $("#addcards").append(getUserText(arr[i].id, name, arr[i].status,arr[i].date));
    }
}
function getText(id, pid, user, date) {
    var text = '<div class="row photo" style="height:200px;">' +
            '<div class="col s12 m6">' +
            '<div class="card blue-grey darken-1">' +
            '<div class="card-content white-text">' +
            '<span class="card-title">Booking Request</span>' +
            '<p>Booking ID: ' + id + '</p>' +
            '<p style="padding-top:4px;">PID: ' + pid + '</p>' +
            '<p style="padding-top:4px;">USER: ' + user + '</p>' +
            '<p style="padding-top:4px;">DATE: ' + date + '</p>' +
            '</div>' +
            '<div class="card-action">' +
            '<a href="#" onclick="accept(' + id + ');">Accept</a>' +
            '<a href="#" onclick="reject(' + id + ');">Reject</a>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    return text;
}

function getUserText(id, pname, status,date) {
    var color = "gold";
    if (status === "PENDING") {
        color = "color:gold;";
    } else if (status === "APPROVED") {
        color = "color:greenyellow;";
    } else {
        color = "color:orangered;";
    }
    var text = '<div class="row photo" style="height:200px;">' +
            '<div class="col s12 m6">' +
            '<div class="card blue-grey darken-1">' +
            '<div class="card-content white-text">' +
            '<span class="card-title">Booking Request</span>' +
            '<p>Booking ID: ' + id + '</p>' +
            '<p style="padding-top:4px;">PHOTOGRAPHER: ' + pname + '</p>' +
            '<p style="padding-top:4px;">Booking Date: ' + date + '</p>' +
            '</div>' +
            '<div class="card-action">' +
            '<p style=' + color + '">STATUS: <strong>' + status + '</strong></p>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>';
    return text;
}


function accept(id) {
    var url = "http://" + host + "/updatestatus?id="+id+"&status=APPROVED";
    $.ajax({
        async: false,
        url: url,
        type: "PUT",
//        contentType: "application/json; charset=utf-8",
        accepts: "application/json",
//        data: JSON.stringify(request),
        dataType: "json",
        crossDomain: true,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
//            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (jsonData) {
            if (jsonData.code === 200) {
                swal({
                    icon: "success",
                    title: "success",
                    text: jsonData.message
                });
                getAdminCards();
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

function reject(id) {
    var url = "http://" + host + "/updatestatus?id="+id+"&status=REJECTED";
    $.ajax({
        async: false,
        url: url,
        type: "PUT",
//        contentType: "application/json; charset=utf-8",
        accepts: "application/json",
//        data: JSON.stringify(request),
        dataType: "json",
        crossDomain: true,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
//            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (jsonData) {
            if (jsonData.code === 200) {
                swal({
                    icon: "success",
                    title: "success",
                    text: jsonData.message
                });
                getAdminCards();
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

