package android.com.homeiot_lights.model;

import java.io.Serializable;

public class Lights implements Serializable{
    
    private int id;
    private String name;
    private int resourceIndex;
    //private LightsTypeEnum type;

    public Lights( String name,  int index) {
        this.name = name;

        if(index<0 || index >=3){
            this.resourceIndex=0;
        }else{
            this.resourceIndex = index;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public int getResourceIndex() {
        return resourceIndex;
    }



    @Override
    public String toString() {
        return " name: "+ name+" index: "+resourceIndex;
    }
}
