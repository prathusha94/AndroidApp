package com.example.prathusha.friendfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class loginActivity extends AppCompatActivity {
    Button loginactivity_button;
    EditText username_login, password_login;
    GPSTracker gps;
    double latitude;
    double longitude;
    String u_name,pwd;
    String[] params = new String[13];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_login = (EditText)findViewById(R.id.username_loginfield);
        password_login = (EditText)findViewById(R.id.password_loginfield);
        loginactivity_button = (Button)findViewById(R.id.LoginActivity_button);

        loginactivity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GPSTracker(loginActivity.this);
                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                }else{
                    gps.showSettingsAlert();
                }
                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());


                params[0]= "https://0a5bbb3d.ngrok.io/friendFinder/login.php";
                params[1] = "userName";
                params[2] = username_login.getText().toString();
                params[3] = "Password";
                params[4] = password_login.getText().toString();
                params[5] = "Latitude";
                params[6] = Double.toString(latitude);
                params[7] = "Longitude";
                params[8] = Double.toString(longitude);
                params[9]="Timestamp";
                params[10]=timestamp;


                login l = new login();
                l.execute(params);


            }
        });
    }

    class login extends AsyncTask<String , String , String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            HttpsURLConnection client = null;
            String data="";
            String response;
            int tmp;
            try
            {
                URL regurl = new URL(url);
                client = (HttpsURLConnection) regurl.openConnection();
                client.setRequestMethod("POST");
                client.setDoOutput(true);
                String urlparameters="userName="+params[2]+"&Password="+params[4]+"&Latitude="+params[6]+"&Longitude="+params[8]+"&Timestamp="+params[10];

                OutputStream outputpost = new
                        BufferedOutputStream(client.getOutputStream());
                outputpost.write(urlparameters.getBytes());
                outputpost.flush();
                outputpost.close();

                InputStream ip = client.getInputStream();
                while ((tmp=ip.read())!=-1){
                    data+=(char)tmp;
                }
                ip.close();

                client.disconnect();
                return data;

            }
            catch(Exception e)
            {
                e.printStackTrace();
                return e.getMessage();

            }



        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            String[] name= new String[100];
            String[] Latitude = new String[100];
            String[] Longitude = new String[100];

            String status;
            String err=null;
            try {

                JSONObject root = new JSONObject(result);
                JSONObject login_result = root.getJSONObject("Login");
                status = login_result.getString("Result");

                if(status.equals("1"))
                {
                    JSONObject user_details1=login_result.getJSONObject("friends");
                    int len=user_details1.length();
                    for(int i=1;i<=len;i++) {
                        JSONObject object = user_details1.getJSONObject(Integer.toString(i));
                        name[i] = object.getString("name");
                        Latitude[i] = object.getString("Latitude");
                        Longitude[i] = object.getString("Longitude");
                    }

                        Intent i = new Intent(loginActivity.this, MapsActivity.class);
                         i.putExtra("user", params[2]);
                        i.putExtra("userLat", params[6]);
                        i.putExtra("userLon", params[8]);
                        i.putExtra("friends", name);
                        i.putExtra("f_Lat", Latitude);
                       i.putExtra("f_Lon", Longitude);
                        startActivity(i);

                }
                else
                {
                   // Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Login failed! User account not found!",Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

        }
    }

}




