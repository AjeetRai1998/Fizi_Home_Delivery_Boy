package digi.coders.capsicodeliverypartner.model.ProofModel;

import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.Constants;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes4.dex */
public class ResponseProof {
    @SerializedName(Constants.ScionAnalytics.MessageType.DATA_MESSAGE)
    private List<DataItem> data;
    @SerializedName(NotificationCompat.CATEGORY_MESSAGE)
    private String msg;
    @SerializedName("res")
    private String res;

    public String getMsg() {
        return this.msg;
    }

    public String getRes() {
        return this.res;
    }

    public List<DataItem> getData() {
        return this.data;
    }
}
