/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caunogame;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Mon Zin Oo
 */
public class Player {
    String pName;
    String pId;
    List<UNOCard> lstholdInHand= new LinkedList<>();  

    public Player() {
    }

    public Player(String pName, String uId){
        this.pName = pName;
        this.pId=uId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
    
    
    public UNOCard throwCard(int i){
        return lstholdInHand.remove(i);
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<UNOCard> getLstholdInHand() {
        return lstholdInHand;
    }

    public void setLstholdInHand(List<UNOCard> lstholdInHand) {
        this.lstholdInHand = lstholdInHand;
    }

    @Override
    public String toString() {
        return  "PlayerId=" + pId +", "+ "pName=" + pName+ "\n"+ lstholdInHand + "\n"+'}';
    }  
      
}
