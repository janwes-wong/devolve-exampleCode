var websocket = null;

function connectWebSocket() {
    //判断当前浏览器是否支持WebSocket
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        var nickName = document.getElementById('nickName').value;
        console.log("ws://localhost:8888/websocket/" + nickName)
        websocket = new WebSocket("ws://localhost:8888/websocket/" + nickName);
    } else {
        alert('当前浏览器不支持websocket');
    }

    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("error");
    };

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("Loc MSG:关闭连接");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    }
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    document.getElementById('message').innerHTML += innerHTML + '<br/>';
}

//关闭连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;
    websocket.send(message);
}