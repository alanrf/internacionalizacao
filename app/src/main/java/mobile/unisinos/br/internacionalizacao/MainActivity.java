package mobile.unisinos.br.internacionalizacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView lbTitulo;
    private TextView lbMensagem;
    private Button btPT;
    private Button btES;
    private Button btEN;

    private final static String KEY_SHARED_PREF = "mobile.unisinos.br.language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lbTitulo = (TextView) findViewById(R.id.lbTitulo);
        lbMensagem = (TextView) findViewById(R.id.lbMensagem);

        bindButtons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferencesEditor();
        //se nao tiver nenhuma pre-definida, usa o portugues.
        String language = sharedPreferences.getString(KEY_SHARED_PREF, "pt");
        setLanguage(language);
    }

    /**
     * Realiza o bind dos botoes de tela
     */
    private void bindButtons() {
        btPT = (Button) findViewById(R.id.btPT);
        btPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("pt");
            }
        });

        btES = (Button) findViewById(R.id.btES);
        btES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("es");
            }
        });

        btEN = (Button) findViewById(R.id.btEN);
        btEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage("en");
            }
        });
    }

    /**
     * Define o novo idioma do aplicativo
     * @param language
     */
    private void setLanguage(String language) {
        Log.i("setLanguage", "Inicio, idioma "+ language );
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = getApplicationContext().getResources();
        Configuration config = res.getConfiguration();
        //config.setLocale(locale); -- uso a linha de baixo pq a versao minima de target esta a 10 (android 2.3.3)
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());

        refreshScreen();

        //Armazena as SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferencesEditor();
        sharedPreferences.edit().putString("language", language).commit();
        Log.i("setLanguage", "FIM");
    }

    /**
     * Reescreve os valores em tela.
     */
    private void refreshScreen() {
        lbMensagem.setText(getString(R.string.mensagem));
        lbTitulo.setText(getString(R.string.app_name));
        btPT.setText(getString(R.string.btpt));
        btES.setText(getString(R.string.btes));
        btEN.setText(getString(R.string.bten));
    }

    /**
     * Obtem as SharedPreferences para a KEY_SHARED_PREF
     * @return
     */
    private SharedPreferences getSharedPreferencesEditor() {
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                KEY_SHARED_PREF, Context.MODE_PRIVATE);
        return sharedPref;
    }
}
