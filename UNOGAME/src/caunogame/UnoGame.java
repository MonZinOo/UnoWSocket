/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caunogame;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UnoGame {
    String gameId;
    String gStatus;    
    String gName;
    List<Player> lstPlayer = new LinkedList<>();
    Player p;    
    UNOCard c= new UNOCard();     
    Deck d=new Deck();
    UNOCard discardCard;
    List<UNOCard> drawPile = new LinkedList<>();

    public UnoGame(String gameId, String gStatus) {
        this.gameId = gameId;
        this.gStatus = gStatus;
    }
    
    public UnoGame(String gameId, String gStatus, List<Player> lstPlayer){
        this.gameId = gameId;
        this.gStatus = gStatus;
        this.lstPlayer = lstPlayer;
    }
    
    public UnoGame(String gameId, String gStatus, List<Player> lstPlayer, List<UNOCard> drawPile){
        this.gameId = gameId;
        this.gStatus = gStatus;
        this.lstPlayer = lstPlayer;
        this.drawPile = drawPile;
    }
    
    public UnoGame(String gameId, String gName, String gStatus){
        this.gameId = gameId;
        this.gStatus = gStatus;
        this.gName = gName;
    }
    
    public UnoGame(String gameId, String gStatus, Player p){
        this.gameId = gameId;
        this.gStatus = gStatus;
        this.p = p;
    }
    
    
    public UnoGame(){
        
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getgStatus() {
        return gStatus;
    }

    public void setgStatus(String gStatus) {
        this.gStatus = gStatus;
    }

    public UNOCard getDiscardCard() {
        return discardCard;
    }

    public void setDiscardCard(UNOCard discardCard) {
        this.discardCard = discardCard;
    }   
    
    public void addPlayer(Player p){        
        lstPlayer.add(p);
    }

    public List<Player> getLstPlayer() {
        return lstPlayer;
    }

    public void setLstPlayer(List<Player> lstPlayer) {
        this.lstPlayer = lstPlayer;
    }  

    public List<UNOCard> getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(List<UNOCard> drawPile) {
        this.drawPile = drawPile;
    }    
    
    public UNOCard createDiscardPile(){
       return d.createDiscardPile();        
    }
    
//    public void createDrawPile(){
//      d.getLstDrawPile();
//    }
    
    public void startGame(){        
        gStatus="Game is Starting";
        d.createCard();        
        d.cardShuffle();
        
        for(int i=0; i<7; i++){
            for (Iterator<Player> iterator = lstPlayer.iterator(); iterator.hasNext();) {
                Player p = iterator.next();
                p.lstholdInHand.add(d.takeCardFromDeck());                
            }
        }
        
        //d.createDiscardPile();
    }
    
   
    public void createDeck(){
        Deck d =new Deck();
    } 
    
    @Override
    public String toString() {
        return gStatus+"\n"+"Discard Pile "+d.createDiscardPile()+"\n"+"Deck left "+d.getTotalDrawCard()+"\n"+d.getLstDrawPile()+"\n"+lstPlayer+ '}'+"\n";
    }  
}

