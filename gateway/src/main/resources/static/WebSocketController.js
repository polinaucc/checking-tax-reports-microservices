class WebSocketController {

    constructor() {
        this._onConnected = this._onConnected.bind(this);
    }

    _onConnected(frame) {
       // this.setConnected(true);
        console.log('Connected: ' + frame);
        var inspectorId = document.getElementById('text3').value;
        this.stompClient.subscribe('/topic/mural/'+inspectorId, this.showMessage);
    }

    // setConnected(connected) {
    //     document.getElementById('connect').disabled = connected;
    //     document.getElementById('disconnect').disabled = !connected;
    //     document.getElementById('mural').style.visibility = connected ? 'visible' : 'hidden';
    //     document.getElementById('response').innerHTML = '';
    // }

    connect() {
        console.log('Connected: ');
        var socket = new SockJS('/websocket-app');
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect({}, this._onConnected);
    }

    disconnect() {
        if(this.stompClient != null) {
            this.stompClient.disconnect();
        }
        this.setConnected(false);
        console.log("Disconnected");
    }

    sendMessage() {

        var message = document.getElementById('text').value;
        var message2 = document.getElementById('text2').value;

        this.stompClient.send("/app/message", {},JSON.stringify({
            message: message,
            token: message2
        }));

    }

    showMessage(message) {
       // var inpectorId = sessionStorage.getItem('cl_inspector_id');
        // var response = document.getElementById('response');
        // var p = document.createElement('p');
        // p.style.wordWrap = 'break-word';
        // p.appendChild(document.createTextNode(message.body));
        // response.appendChild(p);
        console.log(message);
        console.log(message.body);
        let s = JSON.parse(message.body);
        let a = document.getElementById('id');

        let p0 = document.createElement('td');
        let p1 = document.createElement('td');
        let p2 = document.createElement('td');
        let p3 = document.createElement('td');
        let p4 = document.createElement('td');

        p0.appendChild(document.createTextNode(s.id));
        p1.appendChild(document.createTextNode(s.clientId));
        p2.appendChild(document.createTextNode(s.date));
        p3.appendChild(document.createTextNode(s.comment));
        p4.appendChild(document.createTextNode(s.status));

        a.appendChild(p0);
        a.appendChild(p1);
        a.appendChild(p2);
        a.appendChild(p3);
        a.appendChild(p4);

        // JSON.parse(message.body).forEach(function(id){
       //      console.log(id.comment)
       //     let a = document.getElementById('id');
       //     let p = document.createElement('p');
       //     p.appendChild(document.createTextNode(id.comment));
       //     a.appendChild(p);
       //  })

    }
}
var webSocket = new WebSocketController()