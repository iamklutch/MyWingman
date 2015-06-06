package jameshigashiyama.com.mywingman;


/**
 * Created by James on  5/29/2015.
 */
public class Airman {

    private int id;
    private String mLastName;
    private String mFirstName;
    private String mAge;
    private String mRank;
    private int mRankValue;
    private String mLastFour;
    private String mDOR;
    private String mDES;

    public Airman(int id, String lastName, String firstName, String age, String rank,
                  int rankValue, String lastFour, String DOR, String DES) {

     }
    public int getId() {return id;    }

    public void setId(int id) {this.id = id;}

    public String getLastName() {return mLastName;}

    public void setLastName(String lastName) {this.mLastName = lastName;}

    public String getFirstName() {return mFirstName;}

    public void setFirstName(String firstName) {mFirstName = firstName;}

    public String getAge() {return mAge;}

    public void setAge(String age) {this.mAge = age;}

    public String getRank() {return mRank;}

    public void setRank(String rank) {this.mRank = rank;}

    public String getDES() {return mDES;}

    public void setDES(String DES) {mDES = DES;}

    public String getDOR() {return mDOR;}

    public void setDOR(String DOR) {mDOR = DOR;}

    public int getRankValue() {return mRankValue;}

    public void setRankValue(int rankValue) {mRankValue = rankValue;}

    public String getLastFour() {return mLastFour;}

    public void setLastFour(String lastFour) {mLastFour = lastFour;}

}
