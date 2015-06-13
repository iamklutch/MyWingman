package jameshigashiyama.com.mywingman.holders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jameshigashiyama.com.mywingman.R;
import jameshigashiyama.com.mywingman.activites.Crypto;
import jameshigashiyama.com.mywingman.activites.EditAirmanActivity;
import jameshigashiyama.com.mywingman.activites.ViewAirmenActivity;

/**
 * Created by James Higashiyama on 6/2/2015.
 */
public class AirmanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final String TAG = ViewAirmenActivity.class.getSimpleName();

    public TextView airmanLastNameView;
    public TextView airmanAgeView;
    public TextView airmanFirstNameView;
    public ImageView rankImageView;

    public AirmanViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        rankImageView = (ImageView) itemView.findViewById(R.id.airmanRankImageView);
        airmanLastNameView = (TextView) itemView.findViewById(R.id.airmanLastNameTextView);
        airmanAgeView = (TextView) itemView.findViewById(R.id.airmanAgeTextView);
        airmanFirstNameView = (TextView) itemView.findViewById(R.id.airmanFirstNameTextView);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), EditAirmanActivity.class);
        intent.putExtra("ViewID", getAdapterPosition());
        v.getContext().startActivity(intent);
    }
}
