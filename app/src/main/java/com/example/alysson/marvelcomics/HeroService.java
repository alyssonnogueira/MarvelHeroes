package com.example.alysson.marvelcomics;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HeroService {

    OkHttpClient client;
    private String publicKey;
    private String privateKey;
    String mUrl = "http://gateway.marvel.com/v1/public/characters?";
    String mUrlOneHero = "http://gateway.marvel.com/v1/public/characters/";
    private int lastID = 0;
    private int limitID = 0;

    HeroService(){
        client = new OkHttpClient();
        publicKey = "00554ed173ff5ed8e7af907d22bfbd12";
        privateKey = "f3902fec4b3a20657f036147416e1d1dee609ba1";
    }

    public List<Hero> getHeroes() throws IOException, JSONException {

        Long timestamp = System.currentTimeMillis();
        String hash = generateHash(timestamp.toString(), this.publicKey, this.privateKey);
        Request request = null;
        if(lastID == 0) {
            request = new Request.Builder()
                    .url(this.mUrl + "ts=" + timestamp + "&apikey=" + publicKey + "&hash=" + hash)
                    .build();
        } else {
            if (lastID < limitID) {
                int newID = lastID + 1;
                System.out.println("Url: " + this.mUrlOneHero + newID + "?ts=" + timestamp + "&apikey=" + publicKey + "&hash=" + hash);
                request = new Request.Builder()
                        .url(this.mUrlOneHero + newID + "?ts=" + timestamp + "&apikey=" + publicKey + "&hash=" + hash)
                        .build();
                lastID = newID;
            } else {
                throw new IOException("LIMIT EXCEED");
            }
        }

        Response response = client.newCall(request).execute();

        //Log.d("HeroService", response.body().string());
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            JSONObject responseObject = new JSONObject(buffer.toString());
            return parseResponse(responseObject);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    List<Hero> parseResponse(JSONObject response) throws JSONException {
        List<Hero> heroes = new ArrayList<Hero>();

        JSONObject data = response.getJSONObject("data");
        //Log.d("JSON DATA", data.toString());
        JSONArray resultsArray = data.getJSONArray("results");
        //Log.d("JSON DATA", resultsArray.toString());

        for (int i = 0; i < resultsArray.length(); i++){
            JSONObject hero = resultsArray.getJSONObject(i);
            heroes.add(
                    new Hero(
                            hero.getInt("id"),
                            hero.getString("name"),
                            hero.getString("description"),
                            hero.getJSONObject("comics").getInt("available"),
                            hero.getJSONObject("series").getInt("available"),
                            hero.getJSONObject("stories").getInt("available"),
                            hero.getJSONObject("events").getInt("available"),
                            hero.getJSONArray("urls").getJSONObject(1).getString("url"),
                            hero.getJSONObject("thumbnail").getString("path")+"."+hero.getJSONObject("thumbnail").getString("extension")
                    ));
        }
        if (lastID == 0){
            System.out.println(lastID + " : " + heroes.get(heroes.size()-1).getId());
            lastID = heroes.get(heroes.size()-1).getId();
            limitID = data.getInt("total") + heroes.get(0).getId();
        }

        return heroes;
    }

    String generateHash(String timestamp, String publicKey, String privateKey) {
        try {
            String value = timestamp + privateKey + publicKey;
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5Encoder.digest(value.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (int i = 0; i < md5Bytes.length; ++i) {
                md5.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public int getLimitID() {
        return limitID;
    }

    public void setLimitID(int limitID) {
        this.limitID = limitID;
    }
}
