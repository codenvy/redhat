// Custom scripts to put in the header of page
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(window.location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
}

var getUrl = window.location;
var baseUrl = getUrl.protocol + "//" + getUrl.host;

if (window.location.href.indexOf(baseUrl + "/site/login") === 0 && document.referrer.indexOf(baseUrl + "/dashboard") !== 0) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (xhttp.status !== 200) {
            var redirectAfterLogin = getUrlParameter("redirect_url");
            window.location.href = "https://developers.redhat.com/auth/realms/rhd/protocol/openid-connect/auth?client_id=codenvy&redirect_uri=" + baseUrl + "/api/oauth/callback&response_type=code&scope=user&state=oauth_provider%3Dredhat%26mode%3Dfederated_login%26scope%3Duser%26redirect_after_login%3D" + baseUrl + "%252Fapi%252Foauth%253Fredirect_url%253D" + redirectAfterLogin;
        }
    };

    xhttp.open("GET", "/api/user", true);
    xhttp.send();
}


