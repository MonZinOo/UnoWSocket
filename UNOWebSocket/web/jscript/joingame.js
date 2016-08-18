$(function(){
   var cGameId=null;
   var pId=null;
   var displayGameStatus = function (msg){
                    $("#wsStatus").prepend($("<div>").text(msg));       
   }
    
    var promise = $.getJSON("http://localhost:8080/UNOWebSocket/api/game/gamelist");
    promise.done(function (result) {           
            
        for (var i = 0; i < result.length; i++) {
            var g = result[i];
            cGameId = g.gameId;
            $("#gamelist").append("<li><h3 class='gname' id='" + cGameId + "'>" + result[i].name + "</h3></li>");
            //console.info(">>> Create New Game" + result[i].gameId + result[i].name);
        }
    });

    $("#gamelist").on("singletap", "li", function () {
        gid = $(this).find("h3").attr("id");
        console.info(">> Game id: " + gid);
        cGameId=gid;
        $("#gameid").text(cGameId);        
        $.UIGoToArticle("#joingame");
    }); 
    
    $("#joingameBtn").on("singletap", function () {
        $.post("http://localhost:8080/UNOWebSocket/api/game/addplayer", {
           gameid: cGameId,
           playername: $("#playername").val()           
        }).done(function (result) { 
            var json = $.parseJSON(result);
            
            $("#pid").val(json.playerid);
            $("#gid").val(json.gid);
           
        //console.info(">>> Result Value " + result.playerid + ":" + result.gid)
            $.UIGoToArticle("#waiting");           
            
            connection = new WebSocket("ws://localhost:8080/UNOWebSocket/wsUno/" + json.gid);               
            connection.onopen= function(){ 
                displayGameStatus("WebSocket is connected");  
                
                var message={
                    gid: json.gid,
                    playerid: json.playerid,
                    pname: json.playername                    
                }
                connection.send(JSON.stringify(message));
            };
            
            connection.onclose = function(){
                displayGameStatus("WebSocket is closed");
            }
            
            connection.onmessage=function(msg){
                console.info(">>> incoming", msg.data);
                var newMessage = JSON.parse(msg.data);
                displayGameStatus("Player ID : " + newMessage.playerid +'/n'+ "Player Name : " + newMessage.pname);
            }
        });
        
        var promise = $.getJSON("http://localhost:8080/UNOWebSocket/api/game/wait");
            promise.done(function (result) {           
            console.info(">>> Create New Game" + result);
            
            for (var i = 0; i < result.length; i++) {
                $("#playerlist").append("<li><h3 class='gname' id='" 
                        + result[i].GameId + "'>"+ result[i].name + "</h3></li>");
            }
        });        
    });
    
    /////Check Game Start
    var reload = setInterval(myTimer, 1000);
    function myTimer() {
        //var d = new Date();
        var status = "a";
        connection.onmessage = function(msg){
            console.info("incoming: ", msg.data);
            var newMessage = JSON.parse(msg.data);
            status = newMessage.status;   
            
            if(status == "start"){
                var promise = $.getJSON("http://localhost:8080/UNOWebSocket/api/game/deal/" + $("#gid").val() +"/"+ $("#pid").val()); 
                console.info("I am here");
                promise.done(function (result){            
                
                    for (var i=0; i<result.length; i++){
                    $('#inhand').append('<img src="images/'+result[i].image+'.png" title="'+result[i].image+'"/>');                                        		
		}
                pId=$("#pid").val();
            $.UIGoToArticle("#startdeal");
            });    
            }            
        }
    }
    
    $("#inhand").on("singletap", "img", function(){
        var throwCardId =$(this).attr("title");
        var throwCardImg = $(this).attr("title");
        console.info("cid:"+throwCardId+":img"+throwCardImg);
        console.info("Current Game ID here" + cGameId);
            var message ={
                gid: cGameId,
                cid:throwCardId,
                image: throwCardImg,
                flag: true
            }
            connection.send(JSON.stringify(message));
    })
    
    $.post("http://localhost:8080/UNOWebServer/api/game/throwToDiscardPile",{
            gid: cGameId,
            pid: pId,
            cid: throwCardId
    });
});


