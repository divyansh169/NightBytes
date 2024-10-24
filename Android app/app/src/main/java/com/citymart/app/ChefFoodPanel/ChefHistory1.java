package com.citymart.app.ChefFoodPanel;

public class ChefHistory1 implements Comparable<ChefHistory1> {

    private String Finishdate, GrandTotalPrice;

    public ChefHistory1(String finishdate, String grandTotalPrice) {
        Finishdate = finishdate;
        GrandTotalPrice = grandTotalPrice;
    }

    public ChefHistory1() {
    }

    public String getFinishdate() {
        return Finishdate;
    }

    public void setFinishdate(String finishdate) {
        Finishdate = finishdate;
    }

    public String getGrandTotalPrice() {
        return GrandTotalPrice;
    }

    public void setGrandTotalPrice(String grandTotalPrice) {
        GrandTotalPrice = grandTotalPrice;
    }

    // Correct signature for compareTo method from Comparable interface
    // @Override
    // public int compareTo(ChefHistory1 other) {
    //     return other.getFinishdate().compareTo(this.Finishdate); // Reverse order for latest to oldest
    // }
    @Override
    public int compareTo(ChefHistory1 other) {
        // This will sort in descending order (latest to oldest)
        return this.Finishdate.compareTo(other.getFinishdate());
    }
}
