package digi.coders.capsicodeliverypartner.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import digi.coders.capsicodeliverypartner.fragment.DeliveredFragment;
import digi.coders.capsicodeliverypartner.fragment.NewOrderFragment;
import digi.coders.capsicodeliverypartner.fragment.OngoingFragment;

/* loaded from: classes3.dex */
public class HomeTabAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public HomeTabAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        this.myContext = context;
        this.totalTabs = totalTabs;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewOrderFragment newOrderFragment = new NewOrderFragment();
                return newOrderFragment;
            case 1:
                OngoingFragment ongoing = new OngoingFragment();
                return ongoing;
            case 2:
                DeliveredFragment delivered = new DeliveredFragment();
                return delivered;
            default:
                return null;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.totalTabs;
    }
}
