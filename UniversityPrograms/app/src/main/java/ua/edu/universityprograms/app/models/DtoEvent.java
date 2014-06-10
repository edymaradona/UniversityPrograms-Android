package ua.edu.universityprograms.app.models;

import android.os.Parcel;

/**
 * Created by vcaciuc on 6/10/2014.
 */
public class DtoEvent extends DtoEventBase implements android.os.Parcelable {

    public DtoLocation location;

    public DtoEvent(String eventName, String eventDescription, String startDate, String endDate, String imageUrl, int eventId, int numberAttending, Boolean isRegistered, DtoLocation location) {
        super(eventName, eventDescription, startDate, endDate, imageUrl, eventId, numberAttending, isRegistered);
        this.location = location;
    }

    public DtoEvent() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(location);
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

    private DtoEvent(Parcel in) {
        this.location = (DtoLocation) in.readValue(DtoLocation.class.getClassLoader());
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

    public static Creator<DtoEvent> CREATOR = new Creator<DtoEvent>() {
        public DtoEvent createFromParcel(Parcel source) {
            return new DtoEvent(source);
        }

        public DtoEvent[] newArray(int size) {
            return new DtoEvent[size];
        }
    };
}
