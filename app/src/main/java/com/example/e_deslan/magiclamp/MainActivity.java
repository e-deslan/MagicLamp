package com.example.e_deslan.magiclamp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity{

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConexaoMQTT.iniciaConexao();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConexaoMQTT.finalizaConexao();
    }

    public void publish(View v) {
        Switch chave = (Switch) findViewById(R.id.switch1);
        String comando = "";
        if (chave.isChecked()){
            comando = "ligar";
        }
        else{
            comando = "desligar";
        }
        new PublishAsyncTask(this).execute(comando);
    }

    private class PublishAsyncTask extends AsyncTask<String, Void, Void>{

        final Context context;

        public PublishAsyncTask(Context c){
            this.context = c;
        }

        protected void onPreExecute(){
            progress = new ProgressDialog(this.context);
            progress.setMessage("Publishing...");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {

            String topico = "lampada";
            String comando = params[0];
            int qos = 2;
            try {
                MqttClient cliente = ConexaoMQTT.getConexao_broker();
                MqttMessage message = new MqttMessage(comando.getBytes());
                message.setQos(qos);
                cliente.publish(topico, message);
                cliente.setTimeToWait(2000);
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progress.dismiss();
                    }
                });
            } catch (MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
                me.printStackTrace();
            }
            return null;
        }
    }
}
