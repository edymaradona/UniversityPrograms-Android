package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/17/2014.
 */
public class DtoUnRsvp implements Parcelable {

    int EventId;
    String cwid;

    public DtoUnRsvp(int eventId, String cwid) {
        EventId = eventId;
        this.cwid = cwid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.EventId);
        dest.writeString(this.cwid);
    }

    private DtoUnRsvp(Parcel in) {
        this.EventId = in.readInt();
        this.cwid = in.readString();
    }

    public static Parcelable.Creator<DtoUnRsvp> CREATOR = new Parcelable.Creator<DtoUnRsvp>() {
        public DtoUnRsvp createFromParcel(Parcel source) {
            return new DtoUnRsvp(source);
        }

        public DtoUnRsvp[] newArray(int size) {
            return new DtoUnRsvp[size];
        }
    };
}
