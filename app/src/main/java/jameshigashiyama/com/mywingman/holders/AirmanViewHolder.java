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

    public TextView airmanLastNameView;
    public TextView airmanAgeView;
    public TextView airmanFirstNameView;
    public ImageView rankImageView;

    public AirmanViewHolder(View itemView) {
        super(itemView);
        rankImageView = (ImageView) itemView.findViewById(R.id.airmanRankImageView);
        airmanLastNameView = (TextView) itemView.findViewById(R.id.airmanLastNameTextView);
        airmanAgeView = (TextView) itemView.findViewById(R.id.airmanAgeTextView);
        airmanFirstNameView = (TextView) itemView.findViewById(R.id.airmanFirstNameTextView);
    }
}
