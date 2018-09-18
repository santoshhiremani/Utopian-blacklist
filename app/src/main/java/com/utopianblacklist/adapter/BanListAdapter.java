package com.utopianblacklist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.utopianblacklist.objects.BannerUsers;
import com.utopianblocklist.R;
import com.utopianblacklist.listener.CustomItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BanListAdapter extends RecyclerView.Adapter<BanListAdapter.CustomViewHolder> {

    private List<BannerUsers> dataList;
    private Context context;
    private CustomItemClickListener listener;

    public BanListAdapter(Context context, List<BannerUsers> dataList, CustomItemClickListener listener){
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final View mView;

        TextView txtTitle, txtPeriod;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.user_name);
            txtPeriod = mView.findViewById(R.id.textPeriod);
            coverImage = mView.findViewById(R.id.coverImage);
            mView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getLayoutPosition());
        }
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_row, parent, false);
        return new CustomViewHolder(view);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtTitle.setText(dataList.get(position).getName());

        holder.txtPeriod.setText("Banned Until: " + formatDateAsUTC(dataList.get(position).getBanned_until().get$date()) + "               " + dataList.get(position).getBan_length() + "");

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load("https://img.busy.org/@"+dataList.get(position).getName())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

    }

    /**
     * @param unixSeconds
     * @return
     */
    public String formatDateAsUTC(long unixSeconds) {
        String num = String.valueOf(unixSeconds);
        // convert seconds to milliseconds
        Date date = new java.util.Date(Long.valueOf(num.substring(0, 10))*1000L);
        // the format of your date
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        // give a timezone reference for formatting (see comment at the bottom)
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        return  formattedDate;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}