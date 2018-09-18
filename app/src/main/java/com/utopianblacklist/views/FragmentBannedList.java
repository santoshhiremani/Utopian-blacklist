package com.utopianblacklist.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.utopianblacklist.RetrofitClientInstance;
import com.utopianblacklist.adapter.BanListAdapter;
import com.utopianblacklist.listener.CustomItemClickListener;
import com.utopianblacklist.objects.BannerUsers;
import com.utopianblacklist.services.GetDataService;
import com.utopianblocklist.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FragmentBannedList extends Fragment {

    private BanListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,
                container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewContainer);

        getBannedList();
        return view;
    }

    /**
     * get Banned list
     */
    private void getBannedList() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity()); //Your Activity.this
        pDialog.setMessage("Loading...!");
        pDialog.setCancelable(false);
        pDialog.show();
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<BannerUsers>> call = service.getAllBannedUsers();
        call.enqueue(new Callback<List<BannerUsers>>() {
            @Override
            public void onResponse(Call<List<BannerUsers>> call, Response<List<BannerUsers>> response) {
                generateBlacklistList(response.body());
                pDialog.hide();
            }

            @Override
            public void onFailure(Call<List<BannerUsers>> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateBlacklistList(final List<BannerUsers> usersList) {

        adapter = new BanListAdapter(getActivity(), usersList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String itemId = usersList.get(position).get_id().get$oid().toString();
                String userName = usersList.get(position).getName();
                String banLength = usersList.get(position).getBan_length();
                Long banStart = usersList.get(position).getBan_start().get$date();
                Long banEnd = usersList.get(position).getBanned_until().get$date();
                Intent intent = new Intent(getActivity(),UserItemActivity.class);
                intent.putExtra("user_name",userName);
                intent.putExtra("ban_length",banLength);
                intent.putExtra("ban_start",banStart);
                intent.putExtra("ban_end",banEnd);

                startActivity(intent);

                Log.d(TAG, "clicked position:" + itemId);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
