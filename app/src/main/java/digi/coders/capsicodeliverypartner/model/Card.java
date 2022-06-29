package digi.coders.capsicodeliverypartner.model;

/* loaded from: classes6.dex */
public class Card {
    private int image;
    private String title;
    private String value;

    public Card(String title, String value, int image) {
        this.title = title;
        this.value = value;
        this.image = image;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
