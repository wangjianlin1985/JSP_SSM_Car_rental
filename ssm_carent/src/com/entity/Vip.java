// 
// 
// 

package com.entity;

public class Vip
{
    private int id;
    private String username;
    private int points;
    private int level;
    private String enddate;
    private Double consumed;
    private int kehuid;
    private double discount;
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public int getPoints() {
        return this.points;
    }
    
    public void setPoints(final int points) {
        this.points = points;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setLevel(final int level) {
        this.level = level;
    }
    
    public String getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(final String enddate) {
        this.enddate = enddate;
    }
    
    public Double getConsumed() {
        return this.consumed;
    }
    
    public void setConsumed(final Double consumed) {
        this.consumed = consumed;
    }
    
    public int getKehuid() {
        return this.kehuid;
    }
    
    public void setKehuid(final int kehuid) {
        this.kehuid = kehuid;
    }
    
    public double getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(final double discount) {
        this.discount = discount;
    }
}
