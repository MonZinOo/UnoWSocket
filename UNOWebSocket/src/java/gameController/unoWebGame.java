package gameController;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import caunogame.Deck;
import java.util.HashMap;
import java.util.Map;
import caunogame.UnoGame;
import caunogame.Player;
import caunogame.UNOCard;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/game")
public class unoWebGame {
 
    public static Map<String, UnoGame> gm = new HashMap<>();
    public static String waitingGId = "";
    public static String  playerId;
    public static Player  player;
    //public static Deck deck;
    
    @POST
    @Path("/addgame")
    @Produces("text/plain")
    public Response addGame(@FormParam("title") String name){        
        String strgname= name;
        String strgStatus="waiting";
        String strgid = UUID.randomUUID().toString().substring(0,8);
        
        System.out.println(strgname + strgid);
        waitingGId = strgid;
        UnoGame g = new UnoGame(strgid, strgname, strgStatus); 
        System.out.println(waitingGId);
        gm.put(strgid, g); 
        
        JsonObject job;
        job = Json.createObjectBuilder()
                .add("gid", strgid)
                .add("name", strgname)
                .add("status", g.getgStatus())
                .build();
        System.out.println(">>>>> Game Created" + job.toString());
        
        Response resp = Response.ok(job.toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
        return resp;       
    }

    @GET
    @Path("/waitPartner")
    @Produces("text/plain")
    public Response newGame(){
        JsonObject job ;
        //JsonArrayBuilder jsa = Json.createArrayBuilder();

        UnoGame g = gm.get(waitingGId);
        
        job = Json.createObjectBuilder()
            .add("gid",g.getGameId())
            .add("name", g.getgName())
            .add("status", g.getgStatus())
            .build();

        Response resp = Response.ok(job.toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
        return resp;
    }
    
    @GET
    @Path("/gamelist")
    @Produces("text/plain")
    public Response listGame(){
        JsonObject job ;
        JsonArrayBuilder jab = Json.createArrayBuilder();

        Iterator entries = gm.entrySet().iterator();
        while (entries.hasNext()) {
          Map.Entry gentry = (Map.Entry) entries.next();
            job = Json.createObjectBuilder()
            .add("gameId",gentry.getKey().toString())
            .add("name", ((UnoGame)gentry.getValue()).getgName())
            .add("gstatus", ((UnoGame)gentry.getValue()).getgStatus())
            .build();
           
           jab.add(job);
        }

        Response resp = Response.ok(jab.build().toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
        return resp;   
    }
    
//    @POST
//    @Path("/ownerStart")
//    @Produces("text/plain")
//    public Response startTablePlay(@FormParam("gid") String gid){   
//        
//        waitingGId = gid;     
//        UnoGame g = gm.get(waitingGId); 
//        g.startGame();
//        
//        JsonObject job;
//        job = Json.createObjectBuilder()
//            .add("gid",waitingGId)
//            .add("playername", g.getLstPlayer())
//            .add("", ))
//            //.add("status", g.getgStatus())
//            .build();
//
//        Response resp = Response.ok(job.toString())
//                .header("Access-Control-Allow-Origin", "*")
//                .build();
//        return resp;
//    }    
    
    @POST
    @Path("/addplayer")
    @Produces("text/plain")
    public Response addPlayer(@FormParam("gameid") String gid, @FormParam("playername") String pname){         
        
        waitingGId = gid;
        String strpname = pname;
        String strpid = UUID.randomUUID().toString().substring(0,8);
        
        Player p = new Player(strpname, strpid);
        playerId = strpid;
        
        gm.get(waitingGId).addPlayer(p);    
        
        JsonObject job;
        job = Json.createObjectBuilder()
            .add("gid",waitingGId)
            .add("playername", strpname)
            .add("playerid", playerId)
            .build();
        //System.out.println(waitingGId+" : "+ playerId);
        Response resp = Response.ok(job.toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
        System.out.println(job);
        return resp;
    }
   
    @GET
    @Path("/playerlist")
    @Produces("text/plain")
    public Response listPlayer(){
        JsonObject job ;
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        UnoGame g = gm.get(waitingGId); 
                
        List<Player> lstPlayer = g.getLstPlayer();
        
        for (int i = 0; i < lstPlayer.size(); i++) {
            player = lstPlayer.get(i);            
            job = Json.createObjectBuilder()
            .add("GameId", g.getGameId())
            .add("pid", player.getpId())
            .add("name", player.getpName())
            .add("GameTitle",g.getgStatus())
            .build();           
           jab.add(job);            
        }

        Response resp = Response.ok(jab.build().toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
        return resp;
    }
    
    @GET
    @Path("/wait")
    @Produces("text/plain")
    public Response showGamePlayer(){
        JsonObject job ;
    
        UnoGame g = gm.get(waitingGId);        
        job = Json.createObjectBuilder()
            .add("GameId", g.getGameId())
            .add("pid", player.getpId())
            .add("name", player.getpName())
            .add("GameTitle",g.getgStatus())
            //.add("pname", p.)
            .build();

        Response resp = Response.ok(job.toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
        return resp;
    }
    
    @GET
    @Path("/discardPile/{gid}")
    @Produces("text/plain")
    public Response showDiscardPile(@PathParam("gid") String gid){
        JsonObject job;
        //System.out.println(">>> Hello Are You There");
        UnoGame g = gm.get(gid); 
        //System.out.println(">>> Hello Are You There" + g.getGameId());
        g.startGame(); 
        //System.out.println(">>> Hello Are You There" + g.toString());
        UNOCard card = g.createDiscardPile();
        //UNOCard drawcard=g.
               
        //System.out.println(">>> Discard Card" + card);
        job = Json.createObjectBuilder()
            //.add("gid", g.getGameId())
            //.add("gname",g.getgName())
            .add("color",card.getColor())
            .add("type", card.getType())
            .add("value",card.getScore())
            .add("image",card.getImgurl())
            .build();
        //System.out.println(">>> Discard Card" + card.toString());
        Response resp = Response.ok(job.toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();
        return resp;
    }
    
    @POST
    @Path("/playerStart")
    @Produces("text/plain")
    public Response startPlay(@FormParam("gid") String gid, @FormParam("pid") String pid){
        
        waitingGId = gid;
        playerId = pid;

        try {
            URI location = new java.net.URI("http://localhost:8383/PlayUno/UnoStartDeal.html");            
            return Response.seeOther(location).build();
        } catch (URISyntaxException ex) {
            return Response.status(404).entity("Redirect Fail").build();
        }
    }
        
    @GET
    @Path("/deal/{gid}/{pid}")
    @Produces("text/plain")
    public Response showPlayerCards(@PathParam("gid") String gid,@PathParam("pid") String pid){
        System.out.println("Hello There!!!");
        JsonObject job ;
        JsonArrayBuilder jab = Json.createArrayBuilder();
        Player gplayer=null; 
        UnoGame g = gm.get(waitingGId);
        playerId = pid;
        List<Player> lstPlayer = g.getLstPlayer();
        System.out.println(lstPlayer);
        for (int i = 0; i < lstPlayer.size(); i++) {
            System.out.println("Player ID id" +pid);
            gplayer = lstPlayer.get(i);
            if(gplayer.getpId().equals(pid)){                
                break;
            }
            else{
                gplayer=null;
            }            
        }        
        List<UNOCard> cardinhand = gplayer.getLstholdInHand();
        
        for (int i = 0; i < cardinhand.size(); i++) {
            UNOCard card = cardinhand.get(i);
            
            job = Json.createObjectBuilder()
            .add("color",card.getColor())
            .add("type", card.getType())
            .add("value",card.getScore())
            .add("image",card.getImgurl())
            .build();
           jab.add(job);            
        }

        Response resp = Response.ok(jab.build().toString())
                .header("Access-Control-Allow-Origin", "*")
                .build();        
        return resp;
    }
    
    @POST
    @Path("/throwToDiscardPile")
    @Produces("text/plain")
    //public void discardCardPlayerToTable(@PathParam("gid") String gid, @PathParam("pid") String pid, @PathParam("cid") String cid){
    public void discardCardPlayerToTable(@FormParam("gid") String gid, @FormParam("pid") String pid, @FormParam("cid") String cid){
//        JsonObject jso ;
//        JsonArrayBuilder jsa = Json.createArrayBuilder();
//        
        System.out.println("I am in disccar UNOGame");
        waitingGId = gid;
        playerId = pid;
        String strdiscardcid = cid;
        
        System.out.println(">>>>>IN discardcard Card " + gid +": " + pid+": " + cid);
        //Player player=null;
        UnoGame ug = gm.get(waitingGId);
        
        System.out.println(">>>game: "+waitingGId);
        //System.out.println(">>>"+g);
        
        List<Player> lstPlayer = ug.getLstPlayer();
        for (int i = 0; i < lstPlayer.size(); i++) {
            
            player = lstPlayer.get(i);
            if(player.getpId().equals(lstPlayer)){
                break;
            }
            else{
                player =null;
            }
            
        }
        
        List <UNOCard> holdinhand = player.getLstholdInHand();
        
        for (int i = 0; i < holdinhand.size(); i++) {
            UNOCard card = holdinhand.get(i);
            
            if(card.getImgurl().equals(strdiscardcid)){                            
                ug.setDiscardCard(player.throwCard(i));             
            }
        }
        System.out.println(player.getLstholdInHand().size());
        System.err.println("Discard:"+ug.getDiscardCard()+": hand"+player.getLstholdInHand().size());
    }
    
    @GET
    @Path("/drawPile/{gid}")
    @Produces("text/plain")
    public Response showDrawCards(@PathParam("gid") String gid){
        
        JsonObject job ;
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        waitingGId=gid;        
        UnoGame g = gm.get(waitingGId);
        Deck deck = new Deck();
        //System.out.println("Game DATA"+ g);
        
        //List<UNOCard> dcard= g.getDrawPile();
        //List<UNOCard> d = g.getDrawPile();   
        List<UNOCard> d=deck.createCard();
        System.out.println(" Check"+d.size());
       
        for (UNOCard card : d) {
            System.out.println("Data Check"+d.size());
            job = Json.createObjectBuilder()
            .add("gid",g.getGameId())
                    //.add("gid",g.getDrawPile().getGameId())
            .add("gname",g.getgName())
            .add("cid",card.getImgurl())            
            .add("color",card.getColor())
            .add("type", card.getType())
            .add("value",card.getScore())
            .add("image",card.getImgurl())
            .build();            
             jab.add(job);            
             
        }
        
        Response resp = Response.ok(jab.build().toString())
                //.header("Access-Control-Allow-Origin", "*")
                .build();
        return resp;
    }
    
    @POST
    @Path("/drawCard")
    @Produces("text/plain")
   // public void discardCardPlayerToTable(@PathParam("gid") String gid, @PathParam("pid") String pid, @PathParam("cid") String cid){
    public void tableDeckToPlayer(@FormParam("gid") String gid, @FormParam("pid") String pid, @FormParam("cid") String cid){

        System.out.println("I am in disccar UNOGame");
        waitingGId = gid;
        playerId = pid;
        String inhandCid = cid;
        
        System.out.println("> table deck Card " + gid +": " + pid);
        Player player=null;
        UnoGame g = gm.get(waitingGId);
        
        System.out.println(">>>game: "+waitingGId);
        //System.out.println(">>>"+g);
        
        List<Player> lstPlayer = g.getLstPlayer();
        for (int i = 0; i < lstPlayer.size(); i++) {
            
            player = lstPlayer.get(i);
            if(player.getpId().equals(playerId)){
                break;
            }
            else{
                player =null;
            }
            
        }
        
//        List<UNOCard> deckCard = g.setDrawPile(drawPile);
//        
//        for (int i=0; i< deckCard.size(); i++) {
//            UNOCard card = deckCard.get(i);
//            if(card.getImgurl().equals(inhandCid)){
//                player.setLstholdInHand(UNOCard);
//            }
//        }      
        System.err.println("Discard:"+g.createDiscardPile()+"\n: hand"+player.getLstholdInHand());       
    }
}
