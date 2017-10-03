package praticalapp.pratical.com.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import praticalapp.pratical.com.demo.adapter.DataAdapter;
import praticalapp.pratical.com.demo.model.Datum;

import praticalapp.pratical.com.demo.model.ResponseData;
import praticalapp.pratical.com.demo.network.RequestInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeScreen_Activity extends AppCompatActivity {


    public static final String BASE_URL = "http://api.giphy.com/";
    public static final String PUBLIC_API_KEY = "8516ae5930e2408f8dd8849ccb63835d";

    private RecyclerView mRecyclerView;

    private CompositeDisposable mCompositeDisposable;

    private DataAdapter mAdapter;

    private ArrayList<String> imageurlarraylist = new ArrayList<>();
    private ArrayList<String> vediourlarraylist = new ArrayList<>();

    private PullToRefreshView mPullToRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_);

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mCompositeDisposable = new CompositeDisposable();

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        imageurlarraylist.clear();
                        vediourlarraylist.clear();
                        loadJSON();
                    }
                }, 1000);
            }
        });

        initRecyclerView();
        loadJSON();



    }


    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void loadJSON() {


        RequestInterface requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface.class);

        mCompositeDisposable.add(requestInterface.getTrendingResults(10, PUBLIC_API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<ResponseData>() {
                    @Override
                    public void accept(ResponseData responseData) throws Exception {
                        Log.e("Response", new Gson().toJson(responseData.getData()));
                        for (final Datum datum : responseData.getData()) {
                            String imageurl = datum.getImages().getOriginal().getUrl();
                            String vediourl = datum.getImages().getOriginal().getMp4();
                            Log.e("urls", "imageurl " + imageurl);
                            Log.e("urls", "vediourl " + vediourl);
                            imageurlarraylist.add(imageurl);
                            vediourlarraylist.add(vediourl);
                        }
                        mAdapter = new DataAdapter(HomeScreen_Activity.this,imageurlarraylist,vediourlarraylist);
                        mRecyclerView.setAdapter(mAdapter);

                    }
                }));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
