var host = "192.168.1.4:8081";












function setCookie(cname, cvalue) {
    var d = new Date();
    cvalue = cvalue || "";

    document.cookie = cname + "=" + cvalue + ";";
    return cname + "=" + cvalue + ";";
}
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ')
            c = c.substring(1);
        if (c.indexOf(name) === 0)
            return c.substring(name.length, c.length);
    }
    return "";
}
function deleteCookie(name) {
    setCookie(name, "", -1000)
//    document.cookie = name + '=; path=/portal; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    return getCookie(name);
}





