var localhost = "http://localhost:8080";

function logout() {
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;
  xhr.open("GET", localhost + "/logout", true);
  xhr.onload = function () {
    if (xhr.status == 200)
      window.location.href = "../login.html";
  }
  xhr.send();
}