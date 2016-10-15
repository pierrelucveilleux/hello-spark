package app.marketplace.subscription;

import com.google.gson.annotations.SerializedName;

public class Order {
    public  String accountIdentifier;
    public  String editionCode;
    @SerializedName("pricingDuration")
    public  String pricingModel;
}
