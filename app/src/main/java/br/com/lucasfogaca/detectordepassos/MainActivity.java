package br.com.lucasfogaca.detectordepassos;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


//A activity implementa a interface capaz de escutar mudanças nos sensores
public class MainActivity extends AppCompatActivity implements SensorEventListener{
    //textview para mostrar o total de passos
    private TextView tvTotalPassos;
    //objeto para acessar os sensores do sistemas
    private SensorManager mSensorManager;
    //objeto para guardar o detector de passar recebido da variavel anterior
    private Sensor mStepDetectorSensor;

    private int totalPassos = 0;

    private TextView totalCalorias;

    private Button btnReset;

    private double calorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalPassos = (TextView) findViewById(R.id.tvTotalPassos);

        btnReset = (Button) findViewById(R.id.idBtnReset);

        totalCalorias  = (TextView) (findViewById(R.id.tvCalorias));

        //usa o getSystemService para acessar os sensores (fazendo o cast)
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // variavel recebe o detector de passos do gerenciador do sensores do sistema
        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalPassos = 0;
                tvTotalPassos.setText("0");
            }
        });


    }



    //controle de ciclo de vida de activity
    @Override
    protected void onResume() {
        super.onResume();
        //ao entrar na app ele escuta o sensor, passando a activity, o sensor e o tempo de intervalo
        mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //controle de ciclo de vida de activity
    @Override
    protected void onStop() {
        super.onStop();
        //ao sair do app ele para de escutar o sensor passando a actovity e o sensor
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

    //metodo da interface
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //executa a cada mudança no sensor
        Sensor sensor = sensorEvent.sensor;
        //verifiva se o sensor é do tipo detector de passos, concatena e insere no textview
        if(sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            totalPassos++;
            tvTotalPassos.setText(""+totalPassos);

            calorias = totalPassos/20;

            totalCalorias.setText(String.format("%.2f", calorias));

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
