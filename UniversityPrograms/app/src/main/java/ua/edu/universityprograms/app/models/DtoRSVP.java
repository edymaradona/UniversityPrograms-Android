package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/17/2014.
 */
public class DtoRSVP implements Parcelable {

    public int eventId;
    public String cwid, firstName, lastName, email;

    public DtoRSVP(int eventId, String cwid, String firstName, String lastName, String email) {
        this.eventId = eventId;
        this.cwid = cwid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    public DtoRSVP(int i, User u){
        this.eventId = i;
        this.cwid = u.uCwid;
        this.firstName = u.uFirstName;
        this.lastName = u.uLastName;
        this.email = u.uEmail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.eventId);
        dest.writeString(this.cwid);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
    }

    private DtoRSVP(Parcel in) {
        this.eventId = in.readInt();
        this.cwid = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
    }

    public static Parcelable.Creator<DtoRSVP> CREATOR = new Parcelable.Creator<DtoRSVP>() {
        public DtoRSVP createFromParcel(Parcel source) {
            return new DtoRSVP(source);
        }

        public DtoRSVP[] newArray(int size) {
            return new DtoRSVP[size];
        }
    };
}
