package com.example.e_deslan.magiclamp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch chave1 = (Switch) findViewById(R.id.switch1);
        chave1.setOnClickListener(this);
        Switch chave2 = (Switch) findViewById(R.id.switch2);
        chave2.setOnClickListener(this);
        Switch chave3 = (Switch) findViewById(R.id.switch3);
        chave3.setOnClickListener(this);
        Switch chave4 = (Switch) findViewById(R.id.switch4);
        chave4.setOnClickListener(this);
        Switch chave5 = (Switch) findViewById(R.id.switch5);
        chave5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {   //onClickListener para todas chaves dos LEDs
        String comando = "";
        Switch s1 = (Switch) findViewById(R.id.switch1);
        Switch s2 = (Switch) findViewById(R.id.switch2);
        Switch s3 = (Switch) findViewById(R.id.switch3);
        Switch s4 = (Switch) findViewById(R.id.switch4);
        Switch s5 = (Switch) findViewById(R.id.switch5);
        switch (v.getId()) {
            case R.id.switch1:
                if (s1.isChecked()){
                    comando = "ligar";
                }
                else{
                    comando = "desligar";
                }
                ConexaoMQTT.publica("led/1", comando);
                break;
            case R.id.switch2:
                if (s2.isChecked()){
                    comando = "ligar";
                }
                else{
                    comando = "desligar";
                }
                ConexaoMQTT.publica("led/2", comando);
                break;
            case R.id.switch3:
                if (s3.isChecked()){
                    comando = "ligar";
                }
                else{
                    comando = "desligar";
                }
                ConexaoMQTT.publica("led/3", comando);
                break;
            case R.id.switch4:
                if (s4.isChecked()){
                    comando = "ligar";
                }
                else{
                    comando = "desligar";
                }
                ConexaoMQTT.publica("led/4", comando);
                break;
            case R.id.switch5:
                if (s5.isChecked()){
                    comando = "ligar";
                    s1.setChecked(true);
                    s2.setChecked(true);
                    s3.setChecked(true);
                    s4.setChecked(true);
                }
                else{
                    comando = "desligar";
                    s1.setChecked(false);
                    s2.setChecked(false);
                    s3.setChecked(false);
                    s4.setChecked(false);
                }
                ConexaoMQTT.publica("led/1", comando);
                ConexaoMQTT.publica("led/2", comando);
                ConexaoMQTT.publica("led/3", comando);
                ConexaoMQTT.publica("led/4", comando);

                break;
            default:
                break;
        }
    }

    //finaliza a conexao com o broker
    public void finaliza(View v) {
        ConexaoMQTT.finalizaConexao();
    }

    //vai pra tela de configuracao do broker
    public void config(View v){
        Intent intent = new Intent(this, Config.class);
        startActivity(intent);
        finish();
    }
}
