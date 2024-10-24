package com.citymart.app.ChefFoodPanel;

public class TypeC {
    String cpntxt;
    String cpntxt1;
    String cpntxt2;
    String cpntxt3;
    String randomuid;

    public TypeC(){

    }

    public TypeC(String cpntxt,String cpntxt1,String cpntxt2,String cpntxt3, String randomuid) {
        this.cpntxt = cpntxt;
        this.cpntxt1 = cpntxt1;
        this.cpntxt2 = cpntxt2;
        this.cpntxt3 = cpntxt3;
        this.randomuid = randomuid;
    }





    public String getcpntxt() {
        return cpntxt;
    }
    public String getcpntxt1() {
        return cpntxt1;
    }
    public String getcpntxt2() {
        return cpntxt2;
    }
    public String getcpntxt3() {
        return cpntxt3;
    }
    public String getRandomuid() {
        return randomuid;
    }






    public void  setcpntxt(String cpntxt) {
        this.cpntxt = cpntxt;
    }
    public void  setcpntxt1(String cpntxt1) {
        this.cpntxt1 = cpntxt1;
    }
    public void  setcpntxt2(String cpntxt2) {
        this.cpntxt2 = cpntxt2;
    }
    public void  setcpntxt3(String cpntxt3) {
        this.cpntxt3 = cpntxt3;
    }

    public void  setrandomuid(String randomuid) {
        this.randomuid = randomuid;
    }
}
