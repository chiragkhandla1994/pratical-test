package praticalapp.pratical.com.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import praticalapp.pratical.com.demo.R;
import praticalapp.pratical.com.demo.VedioPlayer_activity;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<String> mAndroidList;
    private ArrayList<String> vediourlarraylist;
    int upvotecounter = 0;
    int downvotecounter = 0;
    Context context;

    public DataAdapter(Context context, ArrayList<String> androidList, ArrayList<String> vediourlarraylist) {
        mAndroidList = androidList;
        this.vediourlarraylist = vediourlarraylist;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        Log.e("imageurl", mAndroidList.get(position));

        Ion.with(holder.img_gifimage)
                .centerCrop()
                .load(mAndroidList.get(position));
        holder.img_upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upvotecounter++;
                holder.txt_upvotecounter.setText("" + upvotecounter);
            }
        });

        holder.img_downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downvotecounter++;
                holder.txt_downvotecounter.setText("" + downvotecounter);
            }
        });

        holder.img_gifimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = vediourlarraylist.get(position);
                Log.e("sdfjghfsd", s);

                Intent intent = new Intent(context, VedioPlayer_activity.class);
                intent.putExtra("url", s);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mAndroidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_gifimage, img_upvote, img_downvote;
        private TextView txt_upvotecounter, txt_downvotecounter;

        public ViewHolder(View view) {
            super(view);
            img_gifimage = (ImageView) view.findViewById(R.id.img_gifimage);
            img_upvote = (ImageView) view.findViewById(R.id.img_upvote);
            img_downvote = (ImageView) view.findViewById(R.id.img_downvote);
            txt_upvotecounter = (TextView) view.findViewById(R.id.txt_upvotecounter);
            txt_downvotecounter = (TextView) view.findViewById(R.id.txt_downvotecounter);


        }
    }


}
