package com.sure.excel;

/**
 * Created by try on 2019/11/27.
 */
public class Key {

    private String householdID;

    private String communityID;

    public Key(String householdID, String communityID) {
        this.householdID = householdID;
        this.communityID = communityID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (householdID != null ? !householdID.equals(key.householdID) : key.householdID != null) return false;
        return communityID != null ? communityID.equals(key.communityID) : key.communityID == null;
    }

    @Override
    public int hashCode() {
        int result = householdID != null ? householdID.hashCode() : 0;
        result = 31 * result + (communityID != null ? communityID.hashCode() : 0);
        return result;
    }

    public String getHouseholdID() {
        return householdID;
    }

    public void setHouseholdID(String householdID) {
        this.householdID = householdID;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public static void main(String[] args) {
        Key one = new Key("123","321");
        Key two = new Key("123","321");
        System.out.println(one.equals(two));
        System.out.println(one.hashCode() == two.hashCode());
    }
}