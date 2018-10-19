package android.com.homeiot_lights.model;

import android.com.homeiot_lights.service.LightsService;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LightsList {
    private List<Lights> lightsList;
    private RecyclerView.Adapter adapter;
    private   LightsService service;;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.132.1:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public LightsList() {
        lightsList = new ArrayList<>();
        service = retrofit.create(LightsService.class);
    }


    public void registerAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }


    public Lights getLights(int position) {
        return this.lightsList.get(position);
    }

    public void getLightsList(){
        service.getLightsList().enqueue(new Callback<List<Lights>>() {
            @Override
            public void onResponse(Call<List<Lights>> call, Response<List<Lights>> response) {
                if(response.isSuccessful()){
                    lightsList = response.body();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Lights>> call, Throwable t) {
            }
        });
    }




    public void removeAllData() {
        lightsList.clear();
        if (existAdapter()) {
            adapter.notifyItemRangeRemoved(0, lightsList.size() - 1);
        }
    }

    public void addLight(Lights newLights) {
        service.createLights(newLights).enqueue(new Callback<Lights>() {
            @Override
            public void onResponse(Call<Lights> call, Response<Lights> response) {
                if(response.isSuccessful()){
                    Lights lights = response.body();
                    lightsList.add(lights);
                    if (existAdapter()) {
                        adapter.notifyItemInserted(lightsList.size() - 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<Lights> call, Throwable t) {

            }
        });
    }

    public void addLightsWithIndex(Lights newLights, int index) {
        lightsList.add(index, newLights);
        if (existAdapter()) {
            adapter.notifyItemInserted(index);
        }
    }

    public void remove(int position) {
        service.deleteLights(this.getLights(position)).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful() && response.body() !=null){
                    int index = response.body();
                    lightsList.remove(index);
                    if (existAdapter()) {
                        adapter.notifyItemRemoved(index);
                    }
                    Log.d("size_deleteLights", lightsList.size()+"");
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("remove", t.getMessage()+"");
            }
        });


    }

    private int changeIndex = 0;
    public void changeAnItem(Lights lights, int index) {
        changeIndex = index;
        service.updateLights(lights).enqueue(new Callback<Lights>() {
            @Override
            public void onResponse(Call<Lights> call, Response<Lights> response) {
                if(response.isSuccessful() && response.body() !=null){
                    Lights lights1 = response.body();
                    lightsList.set(changeIndex, lights1);
                    if (existAdapter()) {
                        adapter.notifyItemChanged(changeIndex, lights1);
                    }
                }
            }
            @Override
            public void onFailure(Call<Lights> call, Throwable t) {

            }
        });
    }

    public int getSize() {
        return lightsList.size();
    }



    public boolean existAdapter(){
        return (this.adapter != null);
    }


}
