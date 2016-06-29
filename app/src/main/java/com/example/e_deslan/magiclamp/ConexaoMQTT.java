package com.example.e_deslan.magiclamp;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by e-deslan on 28/06/16.
 */
public class ConexaoMQTT{
    private static MqttClient conexaoBroker = null;

    public static void iniciaConexao(){
        String broker = "tcp://192.168.0.106:5007";
        String nomeCliente = "controlador";
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions opcoes_de_conexao = new MqttConnectOptions();
        opcoes_de_conexao.setCleanSession(true);
        try {
            conexaoBroker = new MqttClient(broker, nomeCliente, persistence);
            conexaoBroker.connect(opcoes_de_conexao);
        }
        catch (MqttException e){
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }

    }

    public static void finalizaConexao(){
        if (conexaoBroker != null){
            try {
                conexaoBroker.disconnect();
            }
            catch (MqttException e){
                System.out.println("reason " + e.getReasonCode());
                System.out.println("msg " + e.getMessage());
                System.out.println("loc " + e.getLocalizedMessage());
                System.out.println("cause " + e.getCause());
                System.out.println("excep " + e);
                e.printStackTrace();
            }
        }
    }

    public static MqttClient getConexao_broker(){
        return conexaoBroker;
    }
}
