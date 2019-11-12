package ktu.bombit;

import android.os.Parcel;
import android.os.Parcelable;

public class User {

    private String username;
    private int id;
    private int imgId;
    private String description;
    private String password;
    private String email;

    public User(){

    }

    public User(int id, int imgId, String username, String password, String email, String description) {
        this.id = id;
        this.imgId = imgId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String title) {
        this.username = title;
    }

    public int getImageId() {
        return imgId;
    }

    public void setImageId(int imageId){
        this.imgId = imageId;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }



   /* @Override
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
    */

}
