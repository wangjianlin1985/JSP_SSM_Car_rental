// 
// 
// 

package com.entity;

public class User
{
    private int id;
    private String username;
    private String password;
    private int role;
    private String name;
    private String tej;
    private String createtime;
    private String sfz;
    
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
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(final String createtime) {
        this.createtime = createtime;
    }
    
    public int getRole() {
        return this.role;
    }
    
    public void setRole(final int role) {
        this.role = role;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getTej() {
        return this.tej;
    }
    
    public void setTej(final String tej) {
        this.tej = tej;
    }
    
    public String getSfz() {
        return this.sfz;
    }
    
    public void setSfz(final String sfz) {
        this.sfz = sfz;
    }
}
