package gameController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;



@ServerEndpoint("/wsUno/{gameId}")
public class UnoEndPoint {
    
    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") String gid){
        //System.out.println(">>> new connection: "+session.getId());
        Map<String, Object> sessObject = session.getUserProperties();
        System.out.println("\tgameId: "+ gid);
        sessObject.put("gameId", gid);
        
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason){
                System.out.println(">> close connection: "+session.getId());
                System.out.println("\t close reason: "+reason.getReasonPhrase());
    }
    
    @OnMessage
    public void onMessage(Session session, String msg){
        System.out.println(">>> no of connection: "+
                session.getOpenSessions().size());
        //String name =(String) session.getUserProperties().get("name");
        InputStream is = new ByteArrayInputStream(msg.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject data = reader.readObject();
        String game = data.getString("gid");
        
        System.out.println("\tincoming: "+msg); 
                
        session.getOpenSessions().stream()
                .filter(s->{
                    System.out.println(">>filter");
                    return (game.equals(
                   (String)s.getUserProperties().get("gameId")));
                }).forEach(s->{
                    try{
                        System.out.println(">>try");
                        s.getBasicRemote().sendText(msg);
                        System.out.println(">>>"+msg);
                    }
                    catch(IOException e){
                        e.printStackTrace();
                        //System.out.println(">>filter");
                    }
                });
    }
}
