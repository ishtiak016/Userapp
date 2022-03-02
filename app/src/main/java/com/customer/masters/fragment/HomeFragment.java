package com.customer.masters.fragment;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.customer.masters.R;
import com.customer.masters.activity.AllBeritaActivity;
import com.customer.masters.activity.IntroActivity;
import com.customer.masters.activity.TopupSaldoActivity;
import com.customer.masters.activity.WalletActivity;
import com.customer.masters.activity.WithdrawActivity;
import com.customer.masters.constants.BaseApp;
import com.customer.masters.constants.Constants;
import com.customer.masters.item.BeritaItem;
import com.customer.masters.item.CatMerchantItem;
import com.customer.masters.item.CatMerchantNearItem;
import com.customer.masters.item.FiturItem;
import com.customer.masters.item.MerchantItem;
import com.customer.masters.item.MerchantNearItem;
import com.customer.masters.item.RatingItem;
import com.customer.masters.item.SliderItem;
import com.customer.masters.json.GetHomeRequestJson;
import com.customer.masters.json.GetHomeResponseJson;
import com.customer.masters.json.GetMerchantbyCatRequestJson;
import com.customer.masters.json.MerchantByCatResponseJson;
import com.customer.masters.json.MerchantByNearResponseJson;
import com.customer.masters.models.CatMerchantModel;
import com.customer.masters.models.CatModel;
import com.customer.masters.models.MerchantModel;
import com.customer.masters.models.MerchantNearModel;
import com.customer.masters.models.User;
import com.customer.masters.utils.Log;
import com.customer.masters.utils.SettingPreference;
import com.customer.masters.utils.Utility;
import com.customer.masters.utils.api.ServiceGenerator;
import com.customer.masters.utils.api.service.UserService;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    View getView;
    Context context;
    ViewPager viewPager, rvreview;
    SliderItem adapter;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    CircleIndicator circleIndicator, circleIndicatorreview;
    RecyclerView rvCategory, rvberita, rvmerchant, rvcatmerchantpromo, rvcatmerchantnear, rvmerchantnear;
    LinearLayout llslider, promoslider, llrating, llberita, llmerchant, llmerchantnear, shimlistpromo, shimlistcatpromo, shimlistnear, shimlistcatnear;
    FiturItem fiturItem;
    RatingItem ratingItem;
    BeritaItem beritaItem;
    MerchantItem merchantItem;
    MerchantNearItem merchantNearItem;
    CatMerchantNearItem catMerchantNearItem;
    CatMerchantItem catMerchantItem;
    private ShimmerFrameLayout mShimmerCat, shimerPromo, shimerreview, shimberita, shimmerchantpromo, getShimmerchantnear;
    TextView saldo, showall, nodatapromo, nodatanear;
    RelativeLayout topup, withdraw, detail;
    SettingPreference sp;
    List<MerchantModel> click;
    List<MerchantNearModel> clicknear;

    private Button promoButton;
    private Button nearByButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getView = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();
        viewPager = getView.findViewById(R.id.viewPager);
        circleIndicator = getView.findViewById(R.id.indicator_unselected_background);
        circleIndicatorreview = getView.findViewById(R.id.indicator_unselected_background_review);
        viewPager = getView.findViewById(R.id.viewPager);
        rvCategory = getView.findViewById(R.id.category);
        rvreview = getView.findViewById(R.id.viewPagerreview);
        rvberita = getView.findViewById(R.id.berita);
        rvmerchant = getView.findViewById(R.id.merchantpromo);
        rvcatmerchantpromo = getView.findViewById(R.id.catmerchantpromo);
        rvcatmerchantnear = getView.findViewById(R.id.catmerchantnear);
        promoslider = getView.findViewById(R.id.rlslider);
        llslider = getView.findViewById(R.id.promoslider);
        saldo = getView.findViewById(R.id.saldo);
        topup = getView.findViewById(R.id.topup);
        withdraw = getView.findViewById(R.id.withdraw);
        detail = getView.findViewById(R.id.detail);
        llberita = getView.findViewById(R.id.llnews);
        llmerchant = getView.findViewById(R.id.llmerchantpromo);
        llmerchantnear = getView.findViewById(R.id.llmerchantnear);
        llrating = getView.findViewById(R.id.llrating);
        showall = getView.findViewById(R.id.showall);
        shimlistpromo = getView.findViewById(R.id.shimlistpromo);
        shimlistnear = getView.findViewById(R.id.shimlistnear);
        nodatapromo = getView.findViewById(R.id.nodatapromo);
        shimlistcatpromo = getView.findViewById(R.id.shimlistcatpromo);
        shimlistcatnear = getView.findViewById(R.id.shimlistcatnear);
        rvcatmerchantnear = getView.findViewById(R.id.catmerchantnear);
        rvmerchantnear = getView.findViewById(R.id.merchantnear);
        nodatanear = getView.findViewById(R.id.nodatanear);
        promoButton = getView.findViewById(R.id.promo_id);
        nearByButton = getView.findViewById(R.id.nearBy_id);
        setOnClickListenerButton(promoButton);
        setOnClickListenerButton(nearByButton);

        sp = new SettingPreference(context);

        mShimmerCat = getView.findViewById(R.id.shimmercat);
        shimerPromo = getView.findViewById(R.id.shimmepromo);
        shimerreview = getView.findViewById(R.id.shimreview);
        shimberita = getView.findViewById(R.id.shimberita);
        shimmerchantpromo = getView.findViewById(R.id.shimmerchantpromo);
        getShimmerchantnear = getView.findViewById(R.id.shimmerchantnear);

        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        //rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        rvberita.setHasFixedSize(true);
        rvberita.setNestedScrollingEnabled(false);
        rvberita.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvmerchant.setHasFixedSize(true);
        rvmerchant.setNestedScrollingEnabled(false);
        rvmerchant.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));

        rvcatmerchantnear.setHasFixedSize(true);
        rvcatmerchantnear.setNestedScrollingEnabled(false);
        rvcatmerchantnear.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
        rvmerchantnear.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));

        rvcatmerchantpromo.setHasFixedSize(true);
        rvcatmerchantpromo.setNestedScrollingEnabled(false);
        rvcatmerchantpromo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        Integer[] colors_temp = {
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent)
        };

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TopupSaldoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });


        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WithdrawActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AllBeritaActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WalletActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rvreview.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (ratingItem.getCount() - 1) && position < (colors.length - 1)) {
                    rvreview.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    rvreview.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
        mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    gethome(location);
                    Constants.LATITUDE = location.getLatitude();
                    Constants.LONGITUDE = location.getLongitude();
                    Log.e("BEARING:", String.valueOf(location.getBearing()));
                }
            }
        });

        colors = colors_temp;
        //shimmershow();

        return getView;
    }

    @SuppressLint({"ResourceAsColor", "NonConstantResourceId"})
    private void setOnClickListenerButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = button.getId();
                switch (id) {
                    case R.id.promo_id:
                        promoButton.setBackgroundResource(R.drawable.button_round_1);
                        promoButton.setTextColor(Color.WHITE);
                        nearByButton.setBackgroundResource(R.drawable.button_round_5);
                        nearByButton.setTextColor(R.color.colorPrimary);
                        llmerchant.setVisibility(View.VISIBLE);
                        llmerchantnear.setVisibility(View.GONE);

                        break;
                    case R.id.nearBy_id:
                        nearByButton.setBackgroundResource(R.drawable.button_round_1);
                        nearByButton.setTextColor(Color.WHITE);
                        promoButton.setBackgroundResource(R.drawable.button_round_5);
                        promoButton.setTextColor(R.color.colorPrimary);
                        llmerchant.setVisibility(View.GONE);
                        llmerchantnear.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }


    private void shimmershow() {
        rvCategory.setVisibility(View.GONE);
        rvreview.setVisibility(View.GONE);
        rvberita.setVisibility(View.GONE);
        rvmerchant.setVisibility(View.GONE);
        rvmerchantnear.setVisibility(View.GONE);
        rvcatmerchantpromo.setVisibility(View.GONE);
        shimmerchantpromo.startShimmerAnimation();
        getShimmerchantnear.startShimmerAnimation();
        shimberita.startShimmerAnimation();
        mShimmerCat.startShimmerAnimation();
        shimerreview.startShimmerAnimation();
        shimerPromo.startShimmerAnimation();
        saldo.setVisibility(View.GONE);
    }

    private void shimmertutup() {
        rvreview.setVisibility(View.VISIBLE);
        rvCategory.setVisibility(View.VISIBLE);
        rvberita.setVisibility(View.VISIBLE);
        rvmerchant.setVisibility(View.VISIBLE);
        rvcatmerchantpromo.setVisibility(View.VISIBLE);
        rvcatmerchantnear.setVisibility(View.VISIBLE);
        rvmerchantnear.setVisibility(View.VISIBLE);
        shimberita.stopShimmerAnimation();
        shimberita.setVisibility(View.GONE);
        shimmerchantpromo.stopShimmerAnimation();
        shimmerchantpromo.setVisibility(View.GONE);
        mShimmerCat.setVisibility(View.GONE);
        mShimmerCat.stopShimmerAnimation();
        shimerreview.setVisibility(View.GONE);
        shimerreview.stopShimmerAnimation();
        shimerPromo.setVisibility(View.GONE);
        shimerPromo.stopShimmerAnimation();
        getShimmerchantnear.stopShimmerAnimation();
        getShimmerchantnear.setVisibility(View.GONE);

        saldo.setVisibility(View.VISIBLE);
    }

    private void gethome(final Location location) {
        try {
            User loginUser = BaseApp.getInstance(context).getLoginUser();
            UserService userService = ServiceGenerator.createService(
                    UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
            GetHomeRequestJson param = new GetHomeRequestJson();
            param.setId(loginUser.getId().trim());
            param.setLat(String.valueOf(location.getLatitude()).trim());
            param.setLon(String.valueOf(location.getLongitude()).trim());
            param.setPhone(loginUser.getNoTelepon().trim());
            userService.home(param).enqueue(new Callback<GetHomeResponseJson>() {
                @Override
                public void onResponse(Call<GetHomeResponseJson> call, Response<GetHomeResponseJson> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getMessage().equalsIgnoreCase("success")) {
                                shimmertutup();
                                sp.updateCurrency(response.body().getCurrency());
                                sp.updateabout(response.body().getAboutus());
                                sp.updateemail(response.body().getEmail());
                                sp.updatephone(response.body().getPhone());
                                sp.updateweb(response.body().getWebsite());
                                sp.updatePaypal(response.body().getPaypalkey());
                                sp.updatepaypalmode(response.body().getPaypalmode());
                                sp.updatepaypalactive(response.body().getPaypalactive());
                                sp.updatestripeactive(response.body().getStripeactive());
                                sp.updatecurrencytext(response.body().getCurrencytext());
                                sp.updateRazorpay(response.body().getRazorpaykey());
                                sp.updaterazorpaymode(response.body().getRazorpaymode());
                                sp.updaterazorpayactive(response.body().getRazorpaymode());
                                sp.updateFlutterwave(response.body().getFlutterwavekey());
                                sp.updateFlutterwaveenc(response.body().getFlutterwaveenc());
                                sp.updateflutterwavemode(response.body().getFlutterwavemode());
                                sp.updateflutterwaveactive(response.body().getFlutterwaveactive());
                                sp.updateflutterwavecurrency(response.body().getFlutterwavecurrency());

                                Utility.currencyTXT(saldo, response.body().getSaldo(), context);

                                if (response.body().getSlider().isEmpty()) {
                                    llslider.setVisibility(View.GONE);
                                } else {
                                    promoslider.setVisibility(View.VISIBLE);
                                    adapter = new SliderItem(response.body().getSlider(), getActivity());
                                    viewPager.setAdapter(adapter);
                                    circleIndicator.setViewPager(viewPager);
                                    viewPager.setPadding(50, 0, 50, 0);
                                }


                                fiturItem = new FiturItem(getActivity(), response.body().getFitur(), R.layout.item_item);
                                rvCategory.setAdapter(fiturItem);
                                if (response.body().getRating().isEmpty()) {
                                    llrating.setVisibility(View.GONE);
                                } else {

                                    ratingItem = new RatingItem(response.body().getRating(), context);
                                    rvreview.setAdapter(ratingItem);
                                    circleIndicatorreview.setViewPager(rvreview);
                                    rvreview.setPadding(50, 0, 50, 0);
                                }
                                if (response.body().getBerita().isEmpty()) {
                                    llberita.setVisibility(View.GONE);
                                } else {
                                    beritaItem = new BeritaItem(getActivity(), response.body().getBerita(), R.layout.item_grid);
                                    rvberita.setAdapter(beritaItem);
                                }


                                if (response.body().getMerchantpromo().isEmpty()) {
                                    llmerchant.setVisibility(View.GONE);
                                } else {

                                    click = response.body().getMerchantpromo();
                                    merchantItem = new MerchantItem(getActivity(), click, R.layout.item_merchant);
                                    rvmerchant.setAdapter(merchantItem);
                                    catMerchantItem = new CatMerchantItem(getActivity(), response.body().getCatmerchant(), R.layout.item_cat_merchant, new CatMerchantItem.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(CatMerchantModel item) {

                                            click.clear();
                                            shimlistpromo.setVisibility(View.VISIBLE);
                                            shimmerchantpromo.setVisibility(View.VISIBLE);
                                            shimlistcatpromo.setVisibility(View.GONE);
                                            rvmerchant.setVisibility(View.GONE);
                                            nodatapromo.setVisibility(View.GONE);
                                            shimmerchantpromo.startShimmerAnimation();
                                            FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
                                            mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                                @Override
                                                public void onSuccess(Location location) {
                                                    if (location != null) {
                                                        getmerchntbycatpromo(location, item.getId_kategori_merchant());
                                                    }
                                                }
                                            });

                                        }
                                    });
                                    rvcatmerchantpromo.setAdapter(catMerchantItem);

                                }

                                if (response.body().getMerchantnear().isEmpty()) {
                                    llmerchantnear.setVisibility(View.GONE);
                                } else {
                                    clicknear = response.body().getMerchantnear();
                                    merchantNearItem = new MerchantNearItem(getActivity(), clicknear, R.layout.item_merchant);
                                    rvmerchantnear.setAdapter(merchantNearItem);

                                    catMerchantNearItem = new CatMerchantNearItem(getActivity(), response.body().getCatmerchant(), R.layout.item_cat_merchant, new CatMerchantNearItem.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(CatMerchantModel item) {
                                            clicknear.clear();
                                            shimlistnear.setVisibility(View.VISIBLE);
                                            getShimmerchantnear.setVisibility(View.VISIBLE);
                                            shimlistcatnear.setVisibility(View.GONE);
                                            rvmerchantnear.setVisibility(View.GONE);
                                            nodatanear.setVisibility(View.GONE);
                                            getShimmerchantnear.startShimmerAnimation();
                                            FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
                                            mFusedLocation.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                                @Override
                                                public void onSuccess(Location location) {
                                                    if (location != null) {
                                                        getmerchntbycatnear(location, item.getId_kategori_merchant());
                                                    }
                                                }
                                            });
                                        }
                                    });

                                    rvcatmerchantnear.setAdapter(catMerchantNearItem);
                                }
                                User user = response.body().getData().get(0);
                                saveUser(user);
                                if (HomeFragment.this.getActivity() != null) {
                                    Realm realm = BaseApp.getInstance(HomeFragment.this.getActivity()).getRealmInstance();
                                    User loginUser = BaseApp.getInstance(HomeFragment.this.getActivity()).getLoginUser();
                                    realm.beginTransaction();
                                    loginUser.setWalletSaldo(Long.parseLong(response.body().getSaldo()));
                                    realm.commitTransaction();
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
                        } else {
                            Log.e("GetHomeResponseJsonError11", "call " + call);
                        }
                    } catch (Exception exception) {
                        Log.e("GetHomeResponseJsonexception", exception + "");
                    }
                }

                @Override
                public void onFailure(Call<GetHomeResponseJson> call, Throwable t) {
                    Log.e("GetHomeResponseJsonError11", "call " + call + "Throwable" + t);
                }
            });
        } catch (Exception exception) {

        }
    }

    private void getmerchntbycatpromo(final Location location, String cat) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetMerchantbyCatRequestJson param = new GetMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(cat);
        userService.getmerchanbycat(param).enqueue(new Callback<MerchantByCatResponseJson>() {
            @Override
            public void onResponse(Call<MerchantByCatResponseJson> call, Response<MerchantByCatResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        click = response.body().getData();
                        shimmerchantpromo.setVisibility(View.GONE);
                        rvmerchant.setVisibility(View.VISIBLE);
                        shimmerchantpromo.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatapromo.setVisibility(View.VISIBLE);
                            rvmerchant.setVisibility(View.GONE);
                        } else {
                            nodatapromo.setVisibility(View.GONE);
                            merchantItem = new MerchantItem(getActivity(), click, R.layout.item_merchant);
                            rvmerchant.setAdapter(merchantItem);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MerchantByCatResponseJson> call, Throwable t) {

            }
        });
    }

    private void getmerchntbycatnear(final Location location, String cat) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetMerchantbyCatRequestJson param = new GetMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(cat);
        userService.getmerchanbynear(param).enqueue(new Callback<MerchantByNearResponseJson>() {
            @Override
            public void onResponse(Call<MerchantByNearResponseJson> call, Response<MerchantByNearResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        clicknear = response.body().getData();
                        getShimmerchantnear.setVisibility(View.GONE);
                        rvmerchantnear.setVisibility(View.VISIBLE);
                        getShimmerchantnear.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                            rvmerchantnear.setVisibility(View.GONE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            merchantNearItem = new MerchantNearItem(getActivity(), clicknear, R.layout.item_merchant);
                            rvmerchantnear.setAdapter(merchantNearItem);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MerchantByNearResponseJson> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        Utility.currencyTXT(saldo, String.valueOf(loginUser.getWalletSaldo()), context);

    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(context).setLoginUser(user);
    }

    public static List<CatModel> getCatdata(Context ctx) {
        List<CatModel> items = new ArrayList<>();

        CatModel obj = new CatModel();
        obj.id_kategori_merchant = "0";
        obj.nama_kategori = "all";
        obj.foto_kategori = "foto";
        obj.id_fitur = "0";
        items.add(obj);

        return items;
    }
}
