package android.com.homeiot_lights.adapter;

import android.com.homeiot_lights.R;
import android.com.homeiot_lights.model.Lights;
import android.com.homeiot_lights.model.LightsList;
import android.com.homeiot_lights.util.CommonUtil;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LightsRecycleAdapter extends RecyclerView.Adapter<LightsRecycleAdapter.LightsViewHolder>   {

    private LayoutInflater mInflater;
    private LightsList mLightsList;
    private Context mContext;
    private  static LightsItemClickListner mClickListener;

    public interface LightsItemClickListner {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(LightsItemClickListner clickListener) {
        LightsRecycleAdapter.mClickListener = clickListener;
    }

    // you provide access to all the views for a data item in a view holder
    public static class LightsViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mImageView;
        public LightsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mTextView=itemView.findViewById(R.id.name);
            mImageView=itemView.findViewById(R.id.lights_icon);
        }


        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            mClickListener.onItemClick(getAdapterPosition(), v);
            return false;
        }
    }


    public LightsRecycleAdapter(Context context,  LightsList lightsList) {
        this.mInflater = LayoutInflater.from(context);
        lightsList.registerAdapter(this);
        this.mLightsList =lightsList;
        this.mContext=context;
    }

    @NonNull
    @Override
    public LightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_cardview, parent, false);
        return new LightsViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LightsViewHolder holder, int position) {
        final Lights lights = mLightsList.getLights(position);
        holder.mTextView.setText(lights.getName());
        holder.mImageView.setImageDrawable(CommonUtil.makeLightsType( mContext.getResources(), lights.getResourceIndex()));
    }



    @Override
    public int getItemCount() {
        return mLightsList.getSize();
    }

    public void removeAt(int index){
        mLightsList.remove(index);
    }




}