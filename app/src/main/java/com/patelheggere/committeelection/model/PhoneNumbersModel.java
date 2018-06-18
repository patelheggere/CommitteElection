package com.patelheggere.committeelection.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PhoneNumbersModel implements Parcelable{

    private String name;
    private String phone;
    private boolean isVoted;

    public PhoneNumbersModel() {
    }

    public PhoneNumbersModel(String name, String phone, boolean isVoted) {
        this.name = name;
        this.phone = phone;
        this.isVoted = isVoted;
    }

    protected PhoneNumbersModel(Parcel in) {
        name = in.readString();
        phone = in.readString();
        isVoted = in.readByte() != 0;
    }

    public static final Creator<PhoneNumbersModel> CREATOR = new Creator<PhoneNumbersModel>() {
        @Override
        public PhoneNumbersModel createFromParcel(Parcel in) {
            return new PhoneNumbersModel(in);
        }

        @Override
        public PhoneNumbersModel[] newArray(int size) {
            return new PhoneNumbersModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        this.isVoted = voted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeByte((byte) (isVoted ? 1 : 0));
    }
}
