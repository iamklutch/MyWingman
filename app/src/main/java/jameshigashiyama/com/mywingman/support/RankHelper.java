package jameshigashiyama.com.mywingman.support;

import android.widget.Switch;

/**
 * Created by James on 6/5/2015.
 */
public class RankHelper {

    public String RankNameHelper (int rankValue){
        String rankName = "AB";

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

    public int RankValueHelper (String rankName){
        int rankValue = 0;

        switch (rankName) {

            case "AB":
                rankValue = 0;
            case "Amn":
                rankValue = 1;
            case "A1C":
                rankValue = 2;
            case "SrA":
                rankValue = 3;
            case "SSgt":
                rankValue = 4;
            case "TSgt":
                rankValue = 5;
            case "MSgt":
                rankValue = 6;
            case "SMSgt":
                rankValue = 7;
            case "CMSgt":
                rankValue = 8;
        }

        return rankValue;
    }
}
