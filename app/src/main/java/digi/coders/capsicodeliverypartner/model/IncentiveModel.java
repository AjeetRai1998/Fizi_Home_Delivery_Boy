package digi.coders.capsicodeliverypartner.model;

public class IncentiveModel {

    String amount,order;

    public IncentiveModel(String amount, String order) {
        this.amount = amount;
        this.order = order;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
