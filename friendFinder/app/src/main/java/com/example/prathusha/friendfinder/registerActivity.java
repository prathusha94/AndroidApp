package com.example.prathusha.friendfinder;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class registerActivity extends AppCompatActivity {

    Button registerActivity_register;
    EditText userName,email, Password ;
    GPSTracker gps;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerActivity_register = (Button)findViewById(R.id.button2_register);
        userName = (EditText) findViewById(R.id.userId_field);
        Password = (EditText) findViewById(R.id.password_field);
        email = (EditText)findViewById(R.id.email_field);




        registerActivity_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Location
                gps = new GPSTracker(registerActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }


                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                String[] params = new String[13];
                params[0]= "https://0a5bbb3d.ngrok.io/friendFinder/register.php";
                params[1] = "userId";
                params[2] = email.getText().toString();
                params[3] = "userName";
                params[4] = userName.getText().toString();
                params[5] = "Password";
                params[6] = Password.getText().toString();
                params[7] = "Latitude";
                params[8] = Double.toString(latitude);
                params[9] = "Longitude";
                params[10] = Double.toString(longitude);
                params[11]="Timestamp";
                params[12]=timestamp;


                registration r = new registration();
                r.execute(params);


            }
        });
    }

    class registration extends AsyncTask<String , String , String>{

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
                String urlparameters="userId="+params[2]+"&userName="+params[4]+"&Password="+params[6]+"&Latitude="+params[8]+"&Longitude="+params[10]+"&Timestamp="+params[12];

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
                JSONObject object = new JSONObject(data);
                int result = new Integer(object.getString("Result")).intValue();
                if(result == 1)
                    response="1";
                else
                    response="0";
                client.disconnect();
                return response;



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
           // super.onPostExecute(s);
           // Toast.makeText(getApplicationContext(),"usrid: ",Toast.LENGTH_LONG).show();
            if(result.equals("1"))
                Toast.makeText(getApplicationContext(),"Registration Successful! Please Login!",Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(),"Registration Unsuccessful",Toast.LENGTH_LONG).show();
        }
    }

}
