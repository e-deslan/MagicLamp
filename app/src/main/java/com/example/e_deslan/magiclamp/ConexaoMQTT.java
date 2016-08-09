package com.example.e_deslan.magiclamp;

import android.widget.Switch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by e-deslan on 28/06/16.
 */
public class ConexaoMQTT {

    private static MqttClient clienteMQTT = null;

    public static void iniciaConexao(String host, String porta) {
        String broker = "tcp://" + host + ":" + porta;
        String nomeCliente = "controlador";
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions opcoes_de_conexao = new MqttConnectOptions();
        opcoes_de_conexao.setCleanSession(true);
        //opcoes_de_conexao.setUserName("");
        //opcoes_de_conexao.setPassword("".toCharArray());
        try {
            //inicializa o cliente MQTT
            clienteMQTT = new MqttClient(broker, nomeCliente, persistence);
            //funcoes que ficam na escuta da conexao com o broker
            clienteMQTT.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) { //Called when the client lost the connection to the broker
                }

                //funcao chamada ao receber uma msg em algum topico inscrito
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println(topic + ": " + message.toString());
                    //como alterar algo na MainActivity a partir daqui?
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {//Called when a outgoing publish is complete
                }
            });
            //inicia conexao como o broker
            clienteMQTT.connect(opcoes_de_conexao);
            //se inscreve no topico
            clienteMQTT.subscribe("led/#");
        } catch (MqttException e) {
            System.out.println("Nao conseguiu criar cliente no broker");
            e.printStackTrace();
        }

    }

    public static void finalizaConexao() {
        if (clienteMQTT != null) {
            try {
                clienteMQTT.disconnect();
            } catch (MqttException e) {
                System.out.println("Nao conseguiu desconectar cliente do broker");
                e.printStackTrace();
            }
        }
    }

    public static MqttClient getCliente() {
        return clienteMQTT;
    }

    public static boolean existeConexao() {
        if (clienteMQTT != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void publica(String topico, String comando) {
        int qos = 2;
        try {
            MqttMessage msg = new MqttMessage(comando.getBytes());
            msg.setQos(qos);
            clienteMQTT.publish(topico, msg);
            clienteMQTT.setTimeToWait(2000);
        }
        catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
