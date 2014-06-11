package ua.edu.universityprograms.app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vcaciuc on 6/11/2014.
 */
public class DtoAddComment implements Parcelable {

    public String cwid, commentText, commentTitle, email;

    public DtoAddComment(String cwid, String commentText, String commentTitle, String email) {
        this.cwid = cwid;
        this.commentText = commentText;
        this.commentTitle = commentTitle;
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cwid);
        dest.writeString(this.commentText);
        dest.writeString(this.commentTitle);
        dest.writeString(this.email);
    }

    private DtoAddComment(Parcel in) {
        this.cwid = in.readString();
        this.commentText = in.readString();
        this.commentTitle = in.readString();
        this.email = in.readString();
    }

    public static Creator<DtoAddComment> CREATOR = new Creator<DtoAddComment>() {
        public DtoAddComment createFromParcel(Parcel source) {
            return new DtoAddComment(source);
        }

        public DtoAddComment[] newArray(int size) {
            return new DtoAddComment[size];
        }
    };
}
