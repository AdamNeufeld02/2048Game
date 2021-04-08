package persistence;

import org.json.JSONObject;

//TODO citation: code taken and modified from JsonReader.java package in JsonSerializationDemo

public interface Writable {

    public abstract JSONObject toJson();
}
