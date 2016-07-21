package com.example.e_deslan.magiclamp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        //preenche os editTexts com os valores de host e porta sugeridos caso exista
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        EditText et_host = (EditText) findViewById(R.id.editText_host);
        EditText et_porta = (EditText) findViewById(R.id.editText_porta);
        et_host.setText(sp.getString("host",""));
        et_porta.setText(sp.getString("porta", ""));
    }

    public void Conectar(View v){
        //salva os valores de host e porta para serem utilizados na proxima vez
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        EditText et_host = (EditText) findViewById(R.id.editText_host);
        EditText et_porta = (EditText) findViewById(R.id.editText_porta);
        String host = et_host.getText().toString();
        String porta = et_porta.getText().toString();
        editor.putString("host",host);
        editor.putString("porta",porta);
        editor.commit();
        //finaliza a conexao caso exista uma conexao aberta
        if(ConexaoMQTT.existeConexao()){
            ConexaoMQTT.finalizaConexao();
        }
        //inicia a conexao com o broker com valores de host e porta desejados
        ConexaoMQTT.iniciaConexao(host, porta);
        //volta para a MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //finish() nao inclui esta activity na pilha do botao voltar
        finish();
    }
}
