/*
* Created by Prashanth Murali on 2/09/17.
* Copyright © 2017 Prashanth Murali. All rights reserved.
* Right To Use for the instructor and the University to build and evaluate the software package
* @author Prashanth Murali mail to: pmurali10@asu.edu
* @version 1.0 February 9, 2017
 */
package edu.asu.msse.pmural10.Assignment3;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    public static String city;
    public static boolean flag=false;
    public static int flag1=0;
    public static double l1,g1,l2,g2;
    public String name1,description1,category1,address1,address2,elevation1,longitude1,latitude1;
    public static ArrayList<String> cities1=new ArrayList<>(Arrays.asList("Select","ASU-West", "UAK-Anchorage", "Circlestone"));
    public static ArrayList<String> cities=new ArrayList<>(Arrays.asList("ASU-West", "UAK-Anchorage", "Circlestone"));
    public static TextView display1;
    public static TextView initialbearing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(flag==false)
        {
            parseJson("places.json");
            flag=true;
        }

        //final TextView display1=(TextView)findViewById(R.id.distance);
        display1=(TextView) findViewById(R.id.distanceDisplay);
        initialbearing=(TextView)findViewById(R.id.initialbearing);
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, cities);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                city = String.valueOf(parent.getItemAtPosition(position));
                flag1=0;

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);

            }
        });


        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        SpinnerAdapter adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,cities1);
        spinner.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                city = String.valueOf(parent.getItemAtPosition(position));
                flag1=0;

                if(city!="Select") {
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }
        );

        Spinner spinnerLeft=(Spinner)findViewById(R.id.spinnerLeft);
        Spinner spinnerRight=(Spinner)findViewById(R.id.spinnerRight);
        spinnerLeft.setAdapter(adapter1);
        spinnerRight.setAdapter(adapter1);

        spinnerLeft.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                city = String.valueOf(parent.getItemAtPosition(position));

                if(city!="Select") {
                    PlaceDescription places=DisplayActivity.p.search(city);
                    l1=Double.parseDouble(places.lat);
                    g1=Double.parseDouble(places.lon);
                    Log.v("L1",String.valueOf(l1));
                    Log.v("G1",String.valueOf(g1));
                    city="";

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }
        );

        spinnerRight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 city = String.valueOf(parent.getItemAtPosition(position));
                 if(city!="Select") {
                     Log.v("INSIDE LISTENER",city);
                     PlaceDescription places=DisplayActivity.p.search(city);
                     l2=Double.parseDouble(places.lat);
                     g2=Double.parseDouble(places.lon);
                     city="";
                     //Log.v("L2",String.valueOf(l2));
                     //Log.v("G2",String.valueOf(g2));
                 }


             }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
                                              }
        );

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city!="Select") {
                    double answer = greatCircle(l1, g1, l2, g2);
                    display1.setText("Distance : "+String.valueOf(answer));
                    //initialbearing.setText(String.valueOf());

                }
            }
        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        SpinnerAdapter adapter1=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,cities1);
        spinner.setAdapter(adapter1);

    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menudesign,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        if(id==R.id.add){
            flag1=1;
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void parseJson(String filename) {
        JSONObject jsonObj = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            InputStream is = getResources().getAssets().open(filename);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            JSONObject parse = new JSONObject(json);
            for(int i=0;i<3;i++) {
                JSONObject object = parse.getJSONObject(cities.get(i));
                address1 = object.getString("address-title");
                address2 = object.getString("address-street");
                elevation1 = object.getString("elevation");
                longitude1 = object.getString("longitude");
                latitude1 = object.getString("latitude");
                name1 = object.getString("name");
                description1 = object.getString("description");
                category1 = object.getString("category");


                if (!DisplayActivity.p.ifExists(name1)) {
                    //Log.v("HERE!","INGAAA");
                    DisplayActivity.p.addDescription(name1, description1, category1, address1, address2, elevation1, latitude1, longitude1);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

    public double greatCircle(double l1,double g1,double l2,double g2)
    {

        Double lat1 = l1;
        Double lon1 = g1;
        Double lat2 = l2;
        Double lon2 = g2;
        Double φ1 = Math.toRadians(lat1);
        Double φ2 = Math.toRadians(lat2);
        Double Δφ = Math.toRadians(lat2 - lat1);
        Double Δλ = Math.toRadians(lon2 - lon1);

        Double a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double R = 6371e3;
        Double distance = R * c;
        distance = distance/1000;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        display1.setText(df.format(distance));

       Double y = Math.sin(g1-g2) * Math.cos(φ2);
        Double x = Math.cos(φ1)*Math.sin(φ2) -
                Math.sin(φ1)*Math.cos(φ2)*Math.cos(g1-g2);
        Double brng = Math.toDegrees(Math.atan2(y, x));
        Log.v("initial Bearing",String.valueOf(brng));
        initialbearing.setText("Initial Bearing : "+df.format(brng));

        return distance;

    }
}
