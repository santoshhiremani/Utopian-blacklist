package com.utopianblacklist.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.utopianblacklist.RetrofitClientInstance;
import com.utopianblacklist.objects.BannerUsers;
import com.utopianblacklist.objects.Blacklisted;
import com.utopianblacklist.services.GetDataService;
import com.utopianblocklist.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment implements View.OnClickListener {

    @BindView(R.id.inputSearch)
    EditText searchUser;

    @BindView(R.id.ban_period)
    TextView txtBanPeriod;

    @BindView(R.id.ban_since)
    TextView txtBanSince;

    @BindView(R.id.ban_until)
    TextView txtBanUntil;

    @BindView(R.id.blacklist)
    TextView txtBlackList;

    @BindView(R.id.global_list)
    TextView txtGlobal;

    @BindView(R.id.utopian_list)
    TextView txtUtopian;

    @BindView(R.id.btnSearch)
    Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,
                container, false);
        ButterKnife.bind(this, view);
        btnSearch.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity()); //Your Activity.this
        pDialog.setMessage("Checking...!");
        pDialog.setCancelable(false);

        if (searchUser.getText().toString().matches("")) {
            Toast.makeText(getActivity(), "You did not enter a username", Toast.LENGTH_SHORT).show();
            return;
        }else{
            pDialog.show();
            /*Create handle for the RetrofitInstance interface*/
            GetDataService serviceUtopian = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<BannerUsers>> callUtopian = serviceUtopian.getBanStatusOfUser(searchUser.getText().toString());


            callUtopian.enqueue(new Callback<List<BannerUsers>>() {
                @Override
                public void onResponse(Call<List<BannerUsers>> call, Response<List<BannerUsers>> response) {
                    //    progressDoalog.dismiss();
                    if(response.body().size()>0){
                        txtUtopian.setVisibility(View.VISIBLE);
                        txtBanPeriod.setText("Banned for:         "+response.body().get(0).getBan_length()+" Days");
                        txtBanSince.setText("Banned Since:    "+formatDateAsUTC(response.body().get(0).getBan_start().get$date()));
                        txtBanUntil.setText("Banned Until:      "+formatDateAsUTC(response.body().get(0).getBanned_until().get$date()));
                    }
                    pDialog.hide();
                }

                @Override
                public void onFailure(Call<List<BannerUsers>> call, Throwable t) {
                    //progressDoalog.dismiss();
                    pDialog.show();
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Blacklisted> call = service.getBlackList("http://blacklist.usesteem.com/user/"+searchUser.getText().toString());

            call.enqueue(new Callback<Blacklisted>() {
                @Override
                public void onResponse(Call<Blacklisted> call, Response<Blacklisted> response) {
                    //    progressDoalog.dismiss();
                    txtGlobal.setVisibility(View.VISIBLE);
                    txtBlackList.setText(convertToCommaSeparated(response.body().getBlacklisted()));
                }

                @Override
                public void onFailure(Call<Blacklisted> call, Throwable t) {
                    //progressDoalog.dismiss();
                    Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * @param strings
     * @return
     */
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
}
