package com.codepath.cribslist.models;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import com.codepath.cribslist.constants.ITEM_FIELD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Item implements Parcelable {
    private long uid;
    private String title;
    private long price;
    private String description;
    private long sellerID;
    private String location;
    private double latitude;
    private double longitude;
    private String created;
    private String category;
    private String thumbnailURL;
    private ArrayList<String> photoURLs = new ArrayList<>();

    public long getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public long getSellerID() {
        return sellerID;
    }

    public String getLocation() {
        return location;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCreated() {
        return created;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<String> getPhotoURLs() {
        return photoURLs;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }


    public Item(){}

    public Item(JSONObject jsonObject) throws JSONException{
        this.uid = jsonObject.getLong(ITEM_FIELD.ID);
        this.title = jsonObject.getString(ITEM_FIELD.TITLE);
        this.description = jsonObject.getString(ITEM_FIELD.DESCRIPTION);
        this.sellerID = jsonObject.getLong(ITEM_FIELD.SELLER);
        this.location = jsonObject.getString(ITEM_FIELD.LOCATION);
        this.created = jsonObject.getString(ITEM_FIELD.CREATED);
        this.category = jsonObject.getString(ITEM_FIELD.CATEGORY);
        JSONArray urls = jsonObject.getJSONArray(ITEM_FIELD.PHOTO_URLS);
        for (int i = 0; i < urls.length(); i++){
            photoURLs.add(urls.getString(i));
        }
        thumbnailURL = jsonObject.getString(ITEM_FIELD.THUMBNAIL);

    }

    public static ArrayList<Item> fromJSONArray(JSONArray array) {
        ArrayList<Item> results = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                Item itm = new Item(obj);
                results.add(itm);
            }
            return results;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(uid);
        out.writeString(title);
        out.writeLong(price);
        out.writeString(description);
        out.writeLong(sellerID);
        out.writeString(location);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeString(created);
        out.writeString(category);
        out.writeString(thumbnailURL);
        out.writeStringList(photoURLs);
    }

    private Item(Parcel in) {
        this.uid = in.readLong();
        this.title = in.readString();
        this.price = in.readLong();
        this.description = in.readString();
        this.sellerID = in.readLong();
        this.location = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.created = in.readString();
        this.category = in.readString();
        this.thumbnailURL = in.readString();
        in.readStringList(photoURLs);

    }

    public void setLocationName(String locationName) {
        this.location = locationName;
    }

    public void setLocationFull(Address address){
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
        this.location = address.getLocality();
    }

    public void setLocationFull(Address address, String name){
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
        this.location = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Item> CREATOR
            = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

}
