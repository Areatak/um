<%--
  Created by IntelliJ IDEA.
  User: Mehrdad
  Date: 14/06/2017
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/resources/js/sockjs.js"></script>
    <script src="/resources/js/stomp.js"></script>
    <title>Title</title>

    <script>

        function paymentRequest(divId, token, coinType, ubit) {

            function guid() {
                function s4() {
                    return Math.floor((1 + Math.random()) * 0x10000)
                        .toString(16)
                        .substring(1);
                }

                return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
                    s4() + '-' + s4() + s4() + s4();
            }

            var xhttp = new XMLHttpRequest();
            var seq = '';
            xhttp.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    document.getElementById(divId).innerHTML = this.responseText;
                    seq = JSON.parse(this.responseText)["seq"];
                }
            };
            var uuid = guid();
            xhttp.open("POST", "http://localhost:8080/buyCreditWithCoin", true);
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send('token=' + token + '&coinType=' + coinType + '&amount=' + ubit + '&stomp=' + uuid);
            var socket = new SockJS('/listen');
            var client = Stomp.over(socket);
            client.connect({}, function (frame) {
                console.log("Connected: " + frame);
                client.subscribe('/user/' + uuid + '/queue/received', function (message) {
                    var map = JSON.parse(message.body);
                    if (map.seq == seq) {
                        if (message.confirmed) {
                            // confirmed
                            alert('C');
                        } else {
                            // not confimed yet
                            alert('NC');
                        }
                    }
                });
            })

        }


        (function () {
            paymentRequest('myDiv', 'token', 'Utabit', 100);

        })();


    </script>


</head>
<body>

<h1>This is Bitcoin Gateway test for web</h1>

<div id="gateway"></div>
</body>
</html>