package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/4/2014.
 */
public class Members implements Parcelable {

    public String url, info, name, about;

    public Members(String url, String name, String info, String about) {
        this.url = url;
        this.info = info;
        this.name = name;
        this.about = about;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.info);
        dest.writeString(this.name);
        dest.writeString(this.about);
    }

    private Members(Parcel in) {
        this.url = in.readString();
        this.info = in.readString();
        this.name = in.readString();
        this.about = in.readString();
    }

    public static Creator<Members> CREATOR = new Creator<Members>() {
        public Members createFromParcel(Parcel source) {return new Members(source); }

        public Members[] newArray(int size) {
            return new Members[size];
        }
    };
}
