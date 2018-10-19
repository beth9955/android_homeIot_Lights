package android.com.homeiot_lights.fragment;


import android.com.homeiot_lights.R;
import android.com.homeiot_lights.util.CommonUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class NewItemFragment extends android.support.v4.app.Fragment {

    View view ;
    Spinner spinner;
    TextView title;
    EditText itemName;
    ImageView imageView;
    static NewItemFragment fragment;
    int index;

//    public static NewItemFragment newInstance(int index, Lights lights) {
//        if(fragment==null){
//            fragment = new NewItemFragment();
//        }
//        Bundle args = new Bundle();
//        args.putInt(LIGHTS_NUMBER, index);
//        args.putSerializable(LIGHTS, lights);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view!=null){
            ViewGroup parent = (ViewGroup)view.getParent();
            if(parent !=  null){
                parent.removeView(view);
            }
        }

        view=  inflater.inflate(R.layout.content_new_lights, container, false);
        spinner = view.findViewById(R.id.spinner);
        itemName = view.findViewById(R.id.editText);
        imageView = view.findViewById(R.id.imageView);
        title= view.findViewById(R.id.textView3);
        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(getActivity(), R.array.lights_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageView.setImageDrawable(CommonUtil.makeLightsType(getResources() ,position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                imageView.setImageDrawable(CommonUtil.makeLightsType(getResources() ,0));
            }
        });

        return view;
    }

    public String getName(){
        return this.itemName.getText().toString();
    }

    public int getResoucrceIndex(){
        return this.spinner.getSelectedItemPosition();
    }


    public void setTitle(String name){
        this.title.setText(name);
    }

    public void setItemName(String name){
        this.itemName.setText(name);
        this.itemName.setSelection(name.length());
    }

    public void setItemRecource(int index){
        this.spinner.setSelection(index);
    }

}
