package digi.coders.capsicodeliverypartner.helper;

/* loaded from: classes5.dex */
public class Constraints {
    public static final String BASE_URL = "http://designerpriya.com/assets/uploads/";
    public static final String BASE_URL1 = "http://designerpriya.com/";
    public static final String DELIVERY_BOY = "delivery_boy/";
    public static final String FETAURE = "feature";
    public static final String LATITUDE = "latitude";
    public static final String LOCATION = "location";
    public static final String LONGITUDE = "longitude";
    public static final String USER = "customer/";
    private String Latitude;
    private String Loc;
    private String Longitude;
    private String feature;

    public Constraints(String loc, String latitude, String longitude, String feature) {
        this.Loc = "";
        this.Latitude = "";
        this.Longitude = "";
        this.feature = "";
        this.Loc = loc;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.feature = feature;
    }

    public Constraints() {
        this.Loc = "";
        this.Latitude = "";
        this.Longitude = "";
        this.feature = "";
    }

    public String getFeature() {
        return this.feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getLoc() {
        return this.Loc;
    }

    public void setLoc(String loc) {
        this.Loc = loc;
    }

    public String getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(String latitude) {
        this.Latitude = latitude;
    }

    public String getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(String longitude) {
        this.Longitude = longitude;
    }
}
