/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 February 9, 2017
 */
package edu.asu.msse.pmural10.Assignment3;

import android.app.Activity;
import android.util.Log;

public class PlaceLibrary extends Activity {
    public static int count = 0;
    int i = 0;
    public static PlaceDescription[] place = new PlaceDescription[100];
    public String address1, address2, elevation, longitude, latitude, name, description, category;

    void addDescription(String name, String description, String category, String address1, String address2, String elevation, String latitude, String longitude) {

        place[count] = new PlaceDescription();
        place[count].placeDetails(name, description, category, address1, address2, elevation, latitude, longitude);
        Log.v("name",place[count].name);
        Log.v("description",place[count].description);
        //Log.v("prev name",place[count-1].name);
        count++;

        Log.v("count=", "" + count);

    }

    boolean ifExists(String name) {
        for (i = 0; i < count; i++) {

            //Log.v("countIf", "" + count);
            Log.v("name Here",name);
            try {
                if (name.equals(place[i].name)) {
                    Log.v("EXISTS","It EXISTS!");
                    return true;
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    PlaceDescription search(String name)
    {
        for (i = 0; i < count; i++) {

            try {

                if (name.equals(place[i].name)) {
                    //Log.v("IN SEARCH",name);
                    //Log.v("IN SEARCH",place[i].description);
                    description=place[i].description;
                    category=place[i].category;
                    address1=place[i].addressTitle;
                    address2=place[i].addressText;
                    elevation=place[i].elevation;
                    latitude=place[i].lat;
                    longitude=place[i].lon;

                    //place[i].placeDetails(name,description,category,address1,address2,elevation,latitude,longitude);

                    return place[i];
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        //Log.v("I",""+i);
        //Log.v("Place",place[i].name);
        return null;
    }

    void modify(String name,String description,String category,String address1,String address2,String elevation,String latitude
    ,String longitude)
    {
        for (i = 0; i < count; i++) {

            try {

                if (name.equals(place[i].name)) {

                    place[i].description=description;
                    place[i].category=category;
                    place[i].addressText=address2;
                    place[i].addressTitle=address1;
                    place[i].elevation=elevation;
                    place[i].lat=latitude;
                    place[i].lon=longitude;
                }
            }

            catch (NullPointerException e)
            {
            e.printStackTrace();
            }
        }
    }
    void delete(String name)
    {
        Log.v("INSIDE DELETE1","here");
        for (i = 0; i < count; i++) {

            try {

                if (name.equals(place[i].name)) {

                    MainActivity.cities.remove(place[i].name);
                    MainActivity.cities1.remove(place[i].name);

                    Log.v("INSIDE DELETE",place[i].name);

                    for(int j=i;j<count-1;j++)
                    {
                        Log.v("I",place[j].name);
                        Log.v("I+1",place[j+1].name);
                        place[j].name=place[j+1].name;
                        place[j].description=place[j+1].description;
                        place[j].category=place[j+1].category;
                        place[j].addressTitle=place[j+1].addressTitle;
                        place[j].addressText=place[j+1].addressText;
                        place[j].elevation=place[j+1].elevation;
                        place[j].lat=place[j+1].lat;
                        place[j].lon=place[j+1].lon;
                    }



                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        count--;

    }

    void LogPrint() {
        for (int i = 0; i < count; i++) {

            try {
                Log.v("data", place[i].name);
            }
            catch(NullPointerException e)
            {
                e.printStackTrace();
            }
        }
    }


}
