package jameshigashiyama.com.mywingman.support;

import android.widget.Switch;

/**
 * Created by James on 6/5/2015.
 */
public class RankHelper {

    public String RankHelper (int rankValue){
        String rankName = "";

        switch (rankValue) {

            case 0:
                rankName = "AB";
            case 1:
                rankName = "Amn";
            case 2:
                rankName = "A1C";
            case 3:
                rankName = "SrA";
            case 4:
                rankName = "SSgt";
            case 5:
                rankName = "TSgt";
            case 6:
                rankName = "MSgt";
            case 7:
                rankName = "SMSgt";
            case 8:
                rankName = "CMSgt";
        }

        return rankName;
    }
}
