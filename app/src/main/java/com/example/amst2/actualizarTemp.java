package com.example.amst2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class actualizarTemp extends AppCompatActivity {
    private RequestQueue mQueue = null;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_temp);

        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");
    }

    public void volver(View v){
        Intent red_sensores = new Intent(getBaseContext(), menu.class);
        red_sensores.putExtra("token", token);
        startActivity(red_sensores);
    }

    public void actualizarDatos(View v){
        final EditText temperatura = (EditText) findViewById(R.id.temperatura);
        String str_temperatura = temperatura.getText().toString();
        actualizar(str_temperatura);
    }


    private void actualizar(String temperatura){
        Map<String, String> params = new HashMap();
        params.put("token", token);
        params.put("temperatura", temperatura);
        JSONObject parametros = new JSONObject(params);
        String login_url = "https://amst-labx.herokuapp.com/api/sensores/1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, login_url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    //token = response.getString("token");
                        Intent menuPrincipal = new Intent(getBaseContext(), menu.class);
                        menuPrincipal.putExtra("token", token);
                        startActivity(menuPrincipal);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new AlertDialog.Builder(actualizarTemp.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("No se realizo conexion con la base de datos");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        mQueue.add(request);
    }
}