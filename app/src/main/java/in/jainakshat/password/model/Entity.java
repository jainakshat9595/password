package in.jainakshat.password.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Akshat on 6/2/2016.
 */
@IgnoreExtraProperties
public class Entity implements Parcelable {

    private String id;
    private String name;
    private String password;
    private String information;

    public Entity(String id, String name, String password, String information) {
        this.id = id;this.name = name;this.password = password;this.information = information;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setInformation(String information) {
        this.information = information;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getPassword() {
        return this.password;
    }
    public String getInformation() {
        return this.information;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(information);
    }

    private Entity(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.information = in.readString();
    }

    public Entity() {

    }

    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() {

        @Override
        public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };



}
