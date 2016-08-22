package com.example.e_deslan.magiclamp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.view.Menu;
import android.view.MenuItem;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch chave6 = (Switch) findViewById(R.id.switch6);
        if(ConexaoMQTT.existeConexao()){
            chave6.setChecked(true);
        }
        else{
            chave6.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {   //inicializa o layout do menu
        getMenuInflater().inflate(R.menu.config_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.config_broker) {   //vai pra tela de configuração
            Intent intent = new Intent(this, Config.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.desconectar){   //chama a função que finaliza a conexão com o broker
            finaliza();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void atuar(View v) {   //funçao que atua em todas chaves dos LEDs
        if(ConexaoMQTT.existeConexao()) {
            String comando = "";
            Switch chave = (Switch) findViewById(v.getId());
            if (chave.isChecked()) {
                comando = "ligar";
            } else {
                comando = "desligar";
            }
            switch (chave.getId()) {
                case R.id.switch1:
                    ConexaoMQTT.publica("led/1", comando);
                    break;
                case R.id.switch2:
                    ConexaoMQTT.publica("led/2", comando);
                    break;
                case R.id.switch3:
                    ConexaoMQTT.publica("led/3", comando);
                    break;
                case R.id.switch4:
                    ConexaoMQTT.publica("led/4", comando);
                    break;
                case R.id.switch5:
                    Switch s1 = (Switch) findViewById(R.id.switch1);
                    Switch s2 = (Switch) findViewById(R.id.switch2);
                    Switch s3 = (Switch) findViewById(R.id.switch3);
                    Switch s4 = (Switch) findViewById(R.id.switch4);
                    if (chave.isChecked()) {
                        s1.setChecked(true);
                        s2.setChecked(true);
                        s3.setChecked(true);
                        s4.setChecked(true);
                    } else {
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
    }

    //finaliza a conexao com o broker
    public void finaliza() {
        ConexaoMQTT.finalizaConexao();
        Switch chave6 = (Switch) findViewById(R.id.switch6);
        chave6.setChecked(false);
    }
}
