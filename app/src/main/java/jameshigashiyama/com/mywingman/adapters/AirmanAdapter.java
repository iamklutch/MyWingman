package jameshigashiyama.com.mywingman.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.holders.AirmanViewHolder;

/**
 * Created by JAmes o  n 4 mat.
 */
public class AirmanAdapter extends RecyclerView.Adapter<AirmanViewHolder> {

   List<Airman> mAirmanItems;
    private Context mContext;


    public AirmanAdapter(Context context, ArrayList<Airman> airmanItems) {
        this.mContext = context;
        this.mAirmanItems = airmanItems;
    }

    @Override
    public AirmanViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.airman_list_item, viewGroup, false);
        return new AirmanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AirmanViewHolder itemViewHolder, int i) {
        Airman airman = mAirmanItems.get(i);
        itemViewHolder.airmanNameView.setText("" + airman.getName());
        itemViewHolder.airmanAgeView.setText("" + airman.getAge());
        itemViewHolder.airmanRankView.setText("" + airman.getRank());
        itemViewHolder.rankImageView.setImageResource(R.drawable.down);
    }

    @Override
    public int getItemCount() {
        return mAirmanItems.size();
    }

    public void addOrUpdateAirman(Airman q) {
        int pos = mAirmanItems.indexOf(q);

    }

}