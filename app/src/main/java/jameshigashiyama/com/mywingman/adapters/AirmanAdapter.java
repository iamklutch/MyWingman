package jameshigashiyama.com.mywingman.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jameshigashiyama.com.mywingman.Airman;
import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.activites.EditAirmanActivity;
import jameshigashiyama.com.mywingman.holders.AirmanViewHolder;
import jameshigashiyama.com.mywingman.support.RankHelper;

/**
 * Created by James on 6 June 15
 */
public class AirmanAdapter extends RecyclerView.Adapter<AirmanViewHolder>{

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
        RankHelper rank = new RankHelper();
        int rankImage = rank.RankIconHelper(airman.getRankValue());
        itemViewHolder.airmanLastNameView.setText("" + airman.getLastName());
        itemViewHolder.airmanAgeView.setText("" + airman.getAge());
        itemViewHolder.airmanFirstNameView.setText("" + airman.getFirstName());
        itemViewHolder.rankImageView.setImageResource(rankImage);

    }

    @Override
    public int getItemCount() {
        return mAirmanItems.size();
    }

    public void addOrUpdateAirman(Airman airman) {
        int mPosition = mAirmanItems.indexOf(airman);

    }
}