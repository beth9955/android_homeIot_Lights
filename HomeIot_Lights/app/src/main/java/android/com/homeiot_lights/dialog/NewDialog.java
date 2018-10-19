package android.com.homeiot_lights.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.com.homeiot_lights.R;
import android.com.homeiot_lights.model.Lights;
import android.com.homeiot_lights.util.CommonUtil;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class NewDialog extends DialogFragment {
    Context contxext;
    NoticeDialogListener mListener;
    EditText editText;
    int currentIndex=0;
    boolean isCompleted=false;
    View view;
    @Override
    public void onAttach(Context context) {
        this.contxext = contxext;
        this.mListener = (NoticeDialogListener)context;
        super.onAttach(context);
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Lights newLights);
        public void onDialogNegativeClick(DialogFragment dialog);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.content_new_lights , null);
        Spinner spinner = view.findViewById(R.id.spinner);

        final ImageView imageView = view.findViewById(R.id.imageView);
        editText = view.findViewById(R.id.editText);

//        String[] lightsName = getResources().getStringArray(R.array.lights_names);
//        ArrayAdapter<CharSequence> adapter= new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,lightsName);

        ArrayAdapter<CharSequence> adapter  = ArrayAdapter.createFromResource(getActivity(),
                R.array.lights_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        builder.setView(view);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentIndex=position;
                imageView.setImageDrawable(makeLightsType(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                imageView.setImageDrawable(makeLightsType(0));
            }
        });



        builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), "positive", Toast.LENGTH_SHORT).show();
                String name = editText.getText().toString();
                if(TextUtils.isEmpty(name)){
                    return;
                }
                isCompleted=true;
                Lights lights = new Lights(name, currentIndex);
                mListener.onDialogPositiveClick(NewDialog.this, lights);
            }
        });

        builder.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(), "cancle", Toast.LENGTH_SHORT).show();
                isCompleted=true;
                mListener.onDialogNegativeClick(NewDialog.this);
            }
        });

        return builder.create();
    }




    private Drawable makeLightsType(int index){
//        TypedArray images = getResources().obtainTypedArray(R.array.lights_resource);
//        Drawable drawable = images.getDrawable(index);
//        return drawable;


        return CommonUtil.makeLightsType( getResources(), index);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
