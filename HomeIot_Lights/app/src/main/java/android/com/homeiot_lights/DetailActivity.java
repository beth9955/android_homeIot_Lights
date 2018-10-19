package android.com.homeiot_lights;

import android.com.homeiot_lights.fragment.NewItemFragment;
import android.com.homeiot_lights.model.Lights;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class DetailActivity extends AppCompatActivity implements Button.OnClickListener{

    int currentPosition=0;
    Spinner spinner;
    NewItemFragment fragment;
    Button save;
    Button delete;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        currentPosition = getIntent().getExtras().getInt(Main2Activity.LIGHTS_NUMBER,0);
        Lights lights= (Lights) getIntent().getExtras().getSerializable(Main2Activity.LIGHTS);

        if(lights==null){
            finish();
        }
        fragment = (NewItemFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.setItemName(lights.getName());
        fragment.setItemRecource(lights.getResourceIndex());
        fragment.setTitle("DETAIL INFO");
        save = findViewById(R.id.save);
        delete = findViewById(R.id.delete);
        save.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    private void sendDataToCallingActivity(boolean doSave){
        Lights lights = null;
        if(doSave){
            lights = new Lights(fragment.getName(), fragment.getResoucrceIndex());
        }
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Main2Activity.LIGHTS, lights);
        bundle.putInt(Main2Activity.LIGHTS_NUMBER,currentPosition);
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    @Override
    protected void onPause() {
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save: sendDataToCallingActivity(true); break;
            case R.id.delete: sendDataToCallingActivity(false); break;
        }

    }
}
