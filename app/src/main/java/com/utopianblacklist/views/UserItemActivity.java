package com.utopianblacklist.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.utopianblacklist.RetrofitClientInstance;
import com.utopianblacklist.services.GetDataService;
import com.utopianblocklist.R;
import com.utopianblacklist.objects.Blacklisted;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserItemActivity extends AppCompatActivity {
    @BindView(R.id.ban_period)
    TextView txtBanPeriod;

    @BindView(R.id.ban_since)
    TextView txtBanSince;

    @BindView(R.id.ban_until)
    TextView txtBanUntil;

    @BindView(R.id.blacklist)
    TextView txtBlackList;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.ban_details);

        // bind the view using butterknife
        ButterKnife.bind(this);

        Intent intent = getIntent();

        bindViews(intent);
    }

    private void bindViews(Intent intent) {
        String userName =  intent.getStringExtra("user_name");
        String banLength = intent.getStringExtra("ban_length");
        long banStart = intent.getLongExtra("ban_start",0);
        long banEnd = intent.getLongExtra("ban_end",0);

        getSupportActionBar().setTitle(userName+"'s Ban status");
        txtBanPeriod.setText("Banned for:         "+banLength+" Days");
        txtBanSince.setText("Banned Since:    "+formatDateAsUTC(banStart));
        txtBanUntil.setText("Banned Until:      "+formatDateAsUTC(banEnd));

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Blacklisted> call = service.getBlackList("http://blacklist.usesteem.com/user/"+userName);

        call.enqueue(new Callback<Blacklisted>() {
            @Override
            public void onResponse(Call<Blacklisted> call, Response<Blacklisted> response) {
                //    progressDoalog.dismiss();
                txtBlackList.setText(convertToCommaSeparated(response.body().getBlacklisted()));
            }

            @Override
            public void onFailure(Call<Blacklisted> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(mContext, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

    public static String convertToCommaSeparated(String[] strings) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; strings != null && i < strings.length; i++) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    //getting back to listview
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
