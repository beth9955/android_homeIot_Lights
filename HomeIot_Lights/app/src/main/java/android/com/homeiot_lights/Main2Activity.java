package android.com.homeiot_lights;

import android.app.DialogFragment;
import android.com.homeiot_lights.adapter.LightsRecycleAdapter;
import android.com.homeiot_lights.dialog.NewDialog;
import android.com.homeiot_lights.model.Lights;
import android.com.homeiot_lights.model.LightsList;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ProgressBar;


public class Main2Activity extends AppCompatActivity implements  NewDialog.NoticeDialogListener {

    final static String LIGHTS_NUMBER = "LIGHTS_NUMBER";
    final static String LIGHTS = "LIGHTS";

    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    LightsList lightsList;
    LightsRecycleAdapter mAdapter;
    ProgressBar progressBar;
    int j=0;

    final static int REQUEST_ACT=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               NewDialog dialog = new NewDialog();
                dialog.show(getFragmentManager(), "test");
            }
        });
        progressBar=findViewById(R.id.progressBar);
        mRecyclerView=findViewById(R.id.lights_list);

        lightsList = new LightsList();
        mAdapter = new LightsRecycleAdapter(this, lightsList);

        mRecyclerView.setAdapter(mAdapter);
        lightsList.registerAdapter(mAdapter);
        lightsList.getLightsList();

       mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
      // mRecyclerView.setLayoutManager(new GridLayoutManager(this,2 ));

       // mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mAdapter.setOnItemClickListener(new LightsRecycleAdapter.LightsItemClickListner() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(Main2Activity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(LIGHTS, lightsList.getLights(position));
                bundle.putInt(LIGHTS_NUMBER,position);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_ACT );
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });




        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mAdapter.removeAt(viewHolder.getAdapterPosition());
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Lights newLights) {
        startProgress();
        lightsList.addLight(newLights);
        mRecyclerView.smoothScrollToPosition(lightsList.getSize());
    }

    public void startProgress()  {
        progressBar.setVisibility(View.VISIBLE);

        for(int i=0; i<10000; i++){
            progressBar.setProgress(i);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                progressBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                },500);
            }
        }).start();
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_ACT:
                    final int index= data.getIntExtra(LIGHTS_NUMBER,0);
                    Lights lights = (Lights) data.getSerializableExtra(LIGHTS);
                    if(lights!=null){
                        startProgress();
                        lightsList.changeAnItem(lights, index);
                        Snackbar.make(findViewById(R.id.main), "successfully saved", Snackbar.LENGTH_SHORT).show();
                    }else{
                        lightsList.remove(index);
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "successfully deleted", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    break;
            }
        }
    }

}
