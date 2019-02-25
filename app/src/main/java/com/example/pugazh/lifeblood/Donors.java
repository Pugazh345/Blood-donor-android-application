/**
 * Created by Pugazh on 27-03-2018.
 */
package com.example.pugazh.lifeblood;
public class Donors {

    String name;
    String address;
    String bloodGroup;
public Donors(){

}
    public Donors(String name,String address,String bloodGroup){
        this.name=name;
        this.address=address;
        this.bloodGroup=bloodGroup;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getAddress(){
        return  address;
    }
    public void setAddress(){
        this.address=address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }
    public void setBloodGroup(){
        this.bloodGroup=bloodGroup;
    }
}
