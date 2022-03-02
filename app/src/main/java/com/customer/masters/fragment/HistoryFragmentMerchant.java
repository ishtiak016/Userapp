package com.customer.masters.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customer.masters.R;
import com.customer.masters.activity.IntroActivity;
import com.customer.masters.constants.BaseApp;
import com.customer.masters.item.OrderItem;
import com.customer.masters.json.HistoryRequestJson;
import com.customer.masters.json.HistoryResponseJson;
import com.customer.masters.models.TransaksiMerchantModel;
import com.customer.masters.models.User;
import com.customer.masters.models.fcm.DriverResponse;
import com.customer.masters.utils.Utility;
import com.customer.masters.utils.api.ServiceGenerator;
import com.customer.masters.utils.api.service.MerchantService;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragmentMerchant extends Fragment {

    private View getView;
    private Context context;
    private TextView pemasukan, harian, bulanan, tahunan;
    private RecyclerView historiorder;
    private OrderItem orderitem;
    private ShimmerFrameLayout historyshimmer;
    private List<TransaksiMerchantModel> order;
    private RelativeLayout rlnodata;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getView = inflater.inflate(R.layout.fragment_history_merchant, container, false);
        context = getContext();

        pemasukan = getView.findViewById(R.id.pemasukan);
        harian = getView.findViewById(R.id.harian);
        bulanan = getView.findViewById(R.id.bulanan);
        tahunan = getView.findViewById(R.id.tahunan);
        historiorder = getView.findViewById(R.id.hisotriorder);
        historyshimmer = getView.findViewById(R.id.shimmerhistory);
        rlnodata = getView.findViewById(R.id.rlnodata);

        historiorder.setHasFixedSize(true);
        historiorder.setNestedScrollingEnabled(false);
        historiorder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return getView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getdata();
        }catch (Exception exception){

        }

    }

    private void shimmershow() {
        historiorder.setVisibility(View.GONE);
        historyshimmer.startShimmerAnimation();
        historyshimmer.setVisibility(View.VISIBLE);

    }

    private void shimmertutup() {
        historyshimmer.stopShimmerAnimation();
        historyshimmer.setVisibility(View.GONE);
        historiorder.setVisibility(View.VISIBLE);
    }

    private void getdata() {
        try {
            //shimmershow();
            if (order != null) {
                order.clear();
            }
            User loginUser = BaseApp.getInstance(context).getLoginUser();
            MerchantService merchantService = ServiceGenerator.createService(MerchantService.class, loginUser.getNoTeleponMerchant(), loginUser.getPassword());
            HistoryRequestJson param = new HistoryRequestJson();
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = df.format(c);
            param.setNotelepon(loginUser.getNoTeleponMerchant());
            param.setIdmerchant(loginUser.getId_merchant());
            param.setDay(formattedDate);
            merchantService.history(param).enqueue(new Callback<HistoryResponseJson>() {
                @Override
                public void onResponse(Call<HistoryResponseJson> call, Response<HistoryResponseJson> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMessage().equalsIgnoreCase("success")) {
                            order = response.body().getData();
                            shimmertutup();
                            Utility.currencyTXT(harian, response.body().getDaily(), context);
                            Utility.currencyTXT(bulanan, response.body().getMonthly(), context);
                            Utility.currencyTXT(tahunan, response.body().getYear(), context);
                            Utility.currencyTXT(pemasukan, response.body().getEarning(), context);
                            orderitem = new OrderItem(context, order, R.layout.item_order_merchan);
                            historiorder.setAdapter(orderitem);
                            if (response.body().getData().isEmpty()) {
                                historiorder.setVisibility(View.GONE);
                                rlnodata.setVisibility(View.VISIBLE);
                            } else {
                                historiorder.setVisibility(View.VISIBLE);
                                rlnodata.setVisibility(View.GONE);
                            }
                        } else {
                            Realm realm = BaseApp.getInstance(getContext()).getRealmInstance();
                            realm.beginTransaction();
                            realm.delete(User.class);
                            realm.commitTransaction();
                            BaseApp.getInstance(getContext()).setLoginUser(null);
                            startActivity(new Intent(getContext(), IntroActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                            getActivity().finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<HistoryResponseJson> call, Throwable t) {

                }
            });
        }catch (Exception exception){

        }
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DriverResponse response) {
        Log.e("IN PROGRESS", response.getResponse());
        if (response.getResponse().equals("2") || response.getResponse().equals("3") || response.getResponse().equals("5")) {
            getdata();
        }

    }
}
