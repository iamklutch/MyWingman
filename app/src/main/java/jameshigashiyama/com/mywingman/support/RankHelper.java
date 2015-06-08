package jameshigashiyama.com.mywingman.support;

import android.widget.Switch;

import jameshigashiyama.com.mywingman.R;

/**
 * Created by James on 6/5/2015.
 */
public class RankHelper {

    public int RankIconHelper (int rankValue){
        int rankIcon = R.drawable.e1;

        switch (rankValue) {

            case 0:
                rankIcon = R.drawable.e1;
                break;
            case 1:
                rankIcon = R.drawable.e2;
                break;
            case 2:
                rankIcon = R.drawable.e3;
                break;
            case 3:
                rankIcon = R.drawable.e4;
                break;
            case 4:
                rankIcon = R.drawable.e5;
                break;
            case 5:
                rankIcon = R.drawable.e6;
                break;
            case 6:
                rankIcon = R.drawable.e7;
                break;
            case 7:
                rankIcon = R.drawable.e8;
                break;
            case 8:
                rankIcon = R.drawable.e9;
                break;
        }

        return rankIcon;
    }

    public int RankValueHelper (String rankName){
        int rankValue = 0;

        switch (rankName) {

            case "AB":
                rankValue = 0;
                break;
            case "Amn":
                rankValue = 1;
                break;
            case "A1C":
                rankValue = 2;
                break;
            case "SrA":
                rankValue = 3;
                break;
            case "SSgt":
                rankValue = 4;
                break;
            case "TSgt":
                rankValue = 5;
                break;
            case "MSgt":
                rankValue = 6;
                break;
            case "SMSgt":
                rankValue = 7;
                break;
            case "CMSgt":
                rankValue = 8;
                break;
        }

        return rankValue;
    }
}
