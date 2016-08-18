/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caunogame;
/**
 *
 * @author Tun Lwin Aung
 */
public class UNOCard implements java.io.Serializable{
    private String color;
    private String type;
    private Integer score;
    private String imgurl;   
    
    public UNOCard(){
    }

    public UNOCard(String color, String type, Integer score, String imgurl) {
        this.color = color;
        this.type = type;
        this.score = score;
        this.imgurl = imgurl;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }



    @Override
    public String toString() {
        return "UNOCard{" + "color=" + color + ", type=" + type + ", score=" + score + ", imgurl=" + imgurl + '}'+"\n";
    }
}
