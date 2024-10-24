package com.citymart.app.ChefFoodPanel;

public class Type4 {
    String rzpid;
    String randomuid;
    String rzpkey;

    public Type4(){

    }

    public Type4(String rzpid, String randomuid, String rzpkey) {
        this.rzpid = rzpid;
        this.randomuid = randomuid;
        this.rzpkey = rzpkey;
    }
    public String getRzpid() {
        return rzpid;
    }
    public String getRandomuid() {
        return randomuid;
    }
    public String getRzpkey() {
        return rzpkey;
    }

    public void  setrzpid(String rzpid) {
        this.rzpid = rzpid;
    }
    public void  setrandomuid(String randomuid) {
        this.randomuid = randomuid;
    }
    public void  setrzpkey(String rzpkey) {
        this.rzpkey = rzpkey;
    }
}
