package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/11/2014.
 */
public class DtoComment implements Parcelable {
    public String commentText, commentTitle;

    public DtoComment(String commentText, String commentTitle) {
        this.commentText = commentText;
        this.commentTitle = commentTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commentText);
        dest.writeString(this.commentTitle);
    }

    private DtoComment(Parcel in) {
        this.commentText = in.readString();
        this.commentTitle = in.readString();
    }

    public static Creator<DtoComment> CREATOR = new Creator<DtoComment>() {
        public DtoComment createFromParcel(Parcel source) {
            return new DtoComment(source);
        }

        public DtoComment[] newArray(int size) {
            return new DtoComment[size];
        }
    };
}
