package ktu.bombit;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private String title;
    private int imageId;
    private String description;
    private int bestScore;

    public Player(){

    }

    public Player(String title, int imageId, String description, int bestScore) {
        this.title = title;
        this.imageId = imageId;
        this.description = description;
        this.bestScore = bestScore;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId){
        this.imageId = imageId;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getBestScore() { return bestScore;}

    public void setBestScore(int sk){
        this.bestScore = sk;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(bestScore);

    }

    public Player(Parcel in){
        imageId = in.readInt();
        title = in.readString();
        description = in.readString();
        bestScore = in.readInt();
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
