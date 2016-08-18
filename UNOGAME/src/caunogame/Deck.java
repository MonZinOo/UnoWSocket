/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caunogame;

import java.util.*;

/**
 *
 * @author Mon Zin Oo
 */
public class Deck {

    List<UNOCard> lstCard = new LinkedList<>();
    List<UNOCard> lstDiscardPile = new LinkedList<>();
    List<UNOCard> lstDrawPile = new LinkedList<>();
    UNOCard ucard;
    int c;//for color
    int v;//for value
    int t;//for type
    int dC;//for Draw
    //private int j;

    

    public List<UNOCard> createCard() {
        String[] ctype = {"Normal", "Skip", "Reverse", "DrawTwo", "DrawFour", "ColorChange"};
        String[] color = {"Blue", "Green", "Red", "Yellow"};
        int[] value = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (t = 0; t < ctype.length; ++t) {
            if (ctype[t].equals("Normal")) {
                //Normal value Included ZERO_0
                for (v = 0; v < value.length; ++v) {
                    for (c = 0; c < color.length; c++) {
                        ucard = new UNOCard(color[c], ctype[t], value[v], color[c] + ctype[t] + value[v]);
                        lstCard.add(ucard);
                    }
                }
                //Normal value Not Included ZERO_0
                for (v = 1; v < value.length; ++v) {
                    for (c = 0; c < color.length; c++) {
                        ucard = new UNOCard(color[c], ctype[t], value[v], color[c] + ctype[t] + value[v]);
                        lstCard.add(ucard);
                    }
                }
            } else if (ctype[t].equals("Skip")) {
                for (dC = 0; dC < 2; dC++) {
                    for (c = 0; c < color.length; c++) {
                        //score= value[0];
                        ucard = new UNOCard(color[c], ctype[t], 20, color[c] + ctype[t] + "_20");
                        lstCard.add(ucard);
                    }
                }
            } else if (ctype[t].equals("Reverse")) {
                for (dC = 0; dC < 2; dC++) {
                    for (c = 0; c < color.length; c++) {
                        //score= value[0];
                        ucard = new UNOCard(color[c], ctype[t], 20, color[c] + ctype[t] + "_20");
                        lstCard.add(ucard);
                    }
                }
            } else if (ctype[t].equals("DrawTwo")) {
                for (dC = 0; dC < 2; dC++) {
                    for (c = 0; c < color.length; c++) {
                        //score= value[0];
                        ucard = new UNOCard(color[c], ctype[t], 20, color[c] + ctype[t] + "_20");
                        lstCard.add(ucard);
                    }
                }
            } else if (ctype[t].equals("DrawFour")) {
                for (c = 0; c < color.length; c++) {
                    //score= value[0];
                    ucard = new UNOCard(color[c], ctype[t], 50, ctype[t] + "_50");
                    lstCard.add(ucard);
                }
            } else if (ctype[t].equals("ColorChange")) {

                for (c = 0; c < color.length; c++) {
                    //score= value[0];
                    ucard = new UNOCard(color[c], ctype[t], 50, ctype[t] + "_50");
                    lstCard.add(ucard);
                }
            }
        }
        return lstCard;
    }

    public void cardShuffle() {
        Collections.shuffle(lstCard);
    }

    public UNOCard takeCardFromDeck() {
        return lstCard.remove(0);
    }

    /**
     * @return the totalDrawCard
     */
    public Integer getTotalDrawCard() {
        return lstCard.size();
    }

   

    public UNOCard createDiscardPile() {
        takeCardFromDeck();
        return lstCard.get(0);
    }

//    public void createDrawPile(){
//       
//           for (Iterator<UNOCard> iterator = lstCard.iterator(); iterator.hasNext();) {
//                lstCard.get(0);                
//           }    
//    }
    
    public List<UNOCard> getLstDrawPile() {
        return lstDrawPile;
    }

    public void setLstDrawPile(List<UNOCard> lstDrawPile) {
        this.lstDrawPile = lstDrawPile;
    }

    /**
     * @param totalDrawCard the totalDrawCard to set
     */
    public void setTotalDrawCard(Integer totalDrawCard) {
        //this.totalDrawCard = totalDrawCard;
    }

//    public UNOCard createDrawPile(){
//        for(int i=0; )
//    }
    @Override
    public String toString() {
        return "Deck{" + lstCard + '}';
    }

}
