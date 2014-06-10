package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/10/2014.
 */
public class DtoLocation implements Parcelable {

    public String city, name, roomNumber, state, street1, street2, zip;

    public DtoLocation(String city, String name, String roomNumber, String state, String street1, String street2, String zip) {
        this.city = city;
        this.name = name;
        this.roomNumber = roomNumber;
        this.state = state;
        this.street1 = street1;
        this.street2 = street2;
        this.zip = zip;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.name);
        dest.writeString(this.roomNumber);
        dest.writeString(this.state);
        dest.writeString(this.street1);
        dest.writeString(this.street2);
        dest.writeString(this.zip);
    }

    private DtoLocation(Parcel in) {
        this.city = in.readString();
        this.name = in.readString();
        this.roomNumber = in.readString();
        this.state = in.readString();
        this.street1 = in.readString();
        this.street2 = in.readString();
        this.zip = in.readString();
    }

    public static Parcelable.Creator<DtoLocation> CREATOR = new Parcelable.Creator<DtoLocation>() {
        public DtoLocation createFromParcel(Parcel source) {
            return new DtoLocation(source);
        }

        public DtoLocation[] newArray(int size) {
            return new DtoLocation[size];
        }
    };
}
