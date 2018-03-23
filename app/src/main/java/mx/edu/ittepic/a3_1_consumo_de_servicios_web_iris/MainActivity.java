package mx.edu.ittepic.a3_1_consumo_de_servicios_web_iris;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AsyncResponse{

    ConectionWeb con;
    TextView temp, pres, hm, amanecer;
    TextView puesta, cord, wather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.txtTemperatura);
        pres = findViewById(R.id.txtPresion);
        hm = findViewById(R.id.txtHumedad);
        amanecer = findViewById(R.id.txtAmanecer);
        puesta = findViewById(R.id.txtPuesta);
        cord = findViewById(R.id.txtCoordenadas);
        wather = findViewById(R.id.txtClima);
        fnWeatherConection();
    }


    public void procesarRespuesta(String r) {
        try{
            JSONObject object = new JSONObject(r);
            JSONArray weather = object.getJSONArray("weather");

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < weather.length(); i++) {
                JSONObject objeto = weather.getJSONObject(i);
                String clima = objeto.getString("main");
                String des = objeto.getString("description");
                stringBuilder.append(clima+" : "+des+"         ");
            }
            JSONObject  main= object.getJSONObject("main");
            JSONObject  sys= object.getJSONObject("sys");
            JSONObject  coord= object.getJSONObject("coord");
            temp.setText(""+main.getString("temp")+"*F");
            pres.setText(""+main.getString("pressure"));
            hm.setText(main.getString("humidity"));

            amanecer.setText(sys.getString("sunrise"));
            puesta.setText(sys.getString("sunset"));

            cord.setText(coord.getString("lon")+","+coord.getString("lat"));

            wather.setText(""+stringBuilder);

        }catch (JSONException e){
            System.out.println(""+e);
        }

    }

    public void fnWeatherConection(){
        try {
            con = new ConectionWeb (MainActivity.this);
            URL direcciopn = new URL("http://api.openweathermap.org/data/2.5/weather?q=Tepic,mx&APPID=8d69ad70a1ab6c667aaa96dd5968885b");
            con.execute(direcciopn);
        } catch (MalformedURLException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
