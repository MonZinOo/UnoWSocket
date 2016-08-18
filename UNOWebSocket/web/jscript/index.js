$(function () {
    var connection=null;  
    var cGameId=null;
    var playerId=null;
    var displayGameStatus = function (msg){
                    $("#wsStatus").prepend($("<div>").text(msg));       
    }
   /*--------------- Create New Game ---------------*/
    $("#createBtn").on("singletap", function () {

        $.post("http://localhost:8080/UNOWebSocket/api/game/addgame", {
            title: $("#gamename").val()

        }).done(function (result) {
            var json = $.parseJSON(result);
            //console.info(">>> Create New Game" +result);
            //alert(json.gid + json.name);
             cGameId=json.gid;
            $("#gameid").text(json.gid);
            $("#gname").text(json.name);            
            $.UIGoToArticle("#waitplayer");  
        
        //WebSocket
            //gameId=$("#gameid").text();
            connection = new WebSocket("ws://localhost:8080/UNOWebSocket/wsUno/" + cGameId);               
            connection.onopen= function(){ 
                displayGameStatus("WebSocket is connected");                               
            };
            
            connection.onclose = function(){
                displayGameStatus("WebSocket is closed");
            }
            
            connection.onmessage=function(msg){
                console.info(">>> incoming", msg.data);
                var newMessage = JSON.parse(msg.data);
                displayGameStatus("Player ID : " + newMessage.playerid +"/n" + "Player Name : " + newMessage.pname);
            }
        });  
    });
    
    /*--------------- Start Game ---------------*/
    $("#ownerstartBtn").on("singletap", function(){
        var promise = $.getJSON("http://localhost:8080/UNOWebSocket/api/game/playerlist");
        promise.done(function (result) {           
            console.info(">>> Create Game Start" + result);            
            for (var i = 0; i < result.length; i++) {
                $("#playerlist").append("<li><h3 class='gname' id='" + result[i].GameId + "'>"+ result[i].name + "</h3><img src=images/unoplayer.jpg </li> ");
                $("#pid").append(result.pid);
            }         
        });
        
        var promise = $.getJSON("http://localhost:8080/UNOWebSocket/api/game/discardPile/" + $("#gameid").text());
        promise.done(function(result){            
            console.info(">>> discardPile " + result);
            
            $('#discardPile').append('<img src="images/' + result.image + '.png" />');
            $('#drawPile').append('<img src="images/back.png" />');  
            
        });
        
        var promise=$.getJSON("http://localhost:8080/UNOWebSocket/api/game/drawPile/" + cGameId);
                promise.done(function (result) {
                    //var json = $.parseJSON(result);
                    alert("Great!" + cGameId);
                    for (var i = 0; i < result.length; i++)
                    {
                        $('#drawPile').append('<img src="images/back.png" title="' + result[i].cid + '"/>');
                    }   
                });
        
        var message={
            gid: cGameId,
            status: "start"
        }
        
        connection.send(JSON.stringify(message));
        $.UIGoToArticle("#deck");  
    });
    
    //discard card to table
    var reload = setInterval(myTimer, 3000);
    function myTimer() {        
        var d = new Date();
        var status = "a";
        var flag = false;
        
        connection.onmessage = function(msg){
          console.info("incoming: Discard message", msg.data);
            var newMessage = JSON.parse(msg.data);
            status = newMessage.status;   
            flag = newMessage.flag;
            //alert("outside"+newMessage.toString());
            if(flag){
                $('#discardPile').append('<img src="images/' + newMessage.image + '.png" alt="'+newMessage.image+'" title="' + newMessage.cid + '"/>');
                alert("inside"+newMessage.toString());
            }

        }
 
    }    
    
    $("#drawPile").on("singletap", "img", function(){
        var cThrowID =$(this).attr("title");
        var cThrowImg = $(this).attr("title");
        var playerId = 
        console.info("cid:"+cThrowID+":img"+cThrowImg);
        //send message
            var message ={
                gid: cGameId,
                pid: playerId,
                cid:cThrowID,
                image: cThrowImg,
                serverflag: true
            }
            connection.send(JSON.stringify(message));
            
        $.post("http://localhost:8080/UNOWebServer/api/game/drawCard",{
            gid: cGameId,
            pid: currentPid,
            cid: cThrowID
        });

    });
    // player wait and start
////    $("#start").on("click", function () {
////
////        $.UIGoToArticle("#tablestartgame");
////    });
    /*######################*/
    /*######################*/
    /*#### Player.html  ####*/
    /*######################*/
    /*######################*/
    
//    var gid;
//
//    var promise = $.getJSON("http://localhost:8080/UNOWebSocket/api/game/gamelist");
//    promise.done(function (result) {
//
//        for (var i = 0; i < result.length; i++) {
//            $("#gamelist").append("<li><h3 class='gname' id='" + result[i].gid + "'>" + result[i].gname + "</h3></li>");
//        }
//    });
//
//    $("#gamelist").on("singletap", "li", function () {
//
//        gid = $(this).find("h3").attr("id");
//        console.info(">> Game id: " + gid);
//        $("#gameid").text(gid);
//        
//        $.UIGoToArticle("#addplayer");
//    });
    
});