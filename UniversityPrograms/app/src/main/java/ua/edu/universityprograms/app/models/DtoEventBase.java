package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/10/2014.
 */
public class DtoEventBase implements Parcelable {

    public String eventName, eventDescription, startDate, endDate, imageUrl;
    public int eventId, numberAttending;
    public Boolean isRegistered;

    public DtoEventBase(){

    }

    public DtoEventBase(String eventName, String eventDescription, String startDate, String endDate, String imageUrl, int eventId, int numberAttending, Boolean isRegistered) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.eventId = eventId;
        this.numberAttending = numberAttending;
        this.isRegistered = isRegistered;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventName);
        dest.writeString(this.eventDescription);
        dest.writeString(this.startDate);
        dest.writeString(this.endDate);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.eventId);
        dest.writeInt(this.numberAttending);
        if (isRegistered == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isRegistered ? 0x01 : 0x00));
        }
    }

    private DtoEventBase(Parcel in) {
        this.eventName = in.readString();
        this.eventDescription = in.readString();
        this.startDate = in.readString();
        this.endDate = in.readString();
        this.imageUrl = in.readString();
        this.eventId = in.readInt();
        this.numberAttending = in.readInt();
        byte isRegisteredVal = in.readByte();
        isRegistered = isRegisteredVal == 0x02 ? null : isRegisteredVal != 0x00;
    }

    public static Creator<DtoEventBase> CREATOR = new Creator<DtoEventBase>() {
        public DtoEventBase createFromParcel(Parcel source) {
            return new DtoEventBase(source);
        }

        public DtoEventBase[] newArray(int size) {
            return new DtoEventBase[size];
        }
    };
}
