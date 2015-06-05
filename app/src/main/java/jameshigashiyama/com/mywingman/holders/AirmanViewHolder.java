package jameshigashiyama.com.mywingman.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jameshigashiyama.com.mywingman.R;

/**
 * Created by James Higashiyama on 6/2/2015.
 */
public class AirmanViewHolder extends RecyclerView.ViewHolder {

    public TextView airmanNameView;
    public TextView airmanAgeView;
    public TextView airmanRankView;
    public ImageView rankImageView;

    public AirmanViewHolder(View itemView) {
        super(itemView);
        rankImageView = (ImageView) itemView.findViewById(R.id.airmanRankImageView);
        airmanNameView = (TextView) itemView.findViewById(R.id.airmanLastNameTextView);
        airmanAgeView = (TextView) itemView.findViewById(R.id.airmanAgeTextView);
        airmanRankView = (TextView) itemView.findViewById(R.id.airmanFirstNameTextView);
    }
}
