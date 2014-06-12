package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/12/2014.
 */
public class User implements Parcelable {

    String uName, uEmail, uCwid;

    public User(String uName, String uEmail, String uCwid) {
        this.uName = uName;
        this.uEmail = uEmail;
        this.uCwid = uCwid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uName);
        dest.writeString(this.uEmail);
        dest.writeString(this.uCwid);
    }

    private User(Parcel in) {
        this.uName = in.readString();
        this.uEmail = in.readString();
        this.uCwid = in.readString();
    }

    public static Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
