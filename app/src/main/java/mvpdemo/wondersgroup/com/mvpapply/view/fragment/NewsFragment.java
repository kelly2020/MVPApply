package mvpdemo.wondersgroup.com.mvpapply.view.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvpdemo.wondersgroup.com.mvpapply.R;
import mvpdemo.wondersgroup.com.mvpapply.adapter.NewsAdapter;
import mvpdemo.wondersgroup.com.mvpapply.model.News;
import mvpdemo.wondersgroup.com.mvpapply.presenter.NewsPresenterImpl;
import mvpdemo.wondersgroup.com.mvpapply.view.INewsView;
import mvpdemo.wondersgroup.com.mvpapply.view.activity.MainActivity;
import mvpdemo.wondersgroup.com.mvpapply.view.activity.NewsDetailActivity;

/**
 * Created by zhangwentao on 16/10/31.
 * Description : 用到了注解，SwipeRefreshLayout 下拉刷新
 * Version :
 */
public class NewsFragment extends Fragment implements INewsView ,NewsAdapter.RecyclerViewClickInterface {
    @Bind(R.id.news_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.news_recycler_view)
    RecyclerView recyclerView;

    private MainActivity activity;

    private NewsAdapter newsAdapter;

    private NewsPresenterImpl newsPresenter;

    private List<News> newses = new ArrayList<>();

    private int lastVisibleItemPosition;//recyclerView 最后一个item 位置

    private LinearLayoutManager linearLayoutManager;// recyclerView 布局显示管理类

    private int colors[] = {R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimary, R.color.gray_light};

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initView();
        initDatas();
        initListener();
    }

    /**
     * recyclerview 下拉刷新，上拉加载更多
     */
    private void initListener() {
        // recyclerview item 点击
        newsAdapter.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Toast.makeText(getActivity(), "加载数据", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "数据刷新完成", Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2500);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // linearLayoutManager = new LinearLayoutManager(activity);
                //recyclerView.setLayoutManager(linearLayoutManager);
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();// 当滑动到recycelrView最后一个item 的时候
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                newsAdapter.changeMoreStatus(1);//正在加载
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == newsAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            newsPresenter.loadMoreNews();
                            newsAdapter.changeMoreStatus(0);//上拉加载更多
                        }
                    }, 2500);
                }
            }
        });
    }

    private void initDatas() {
        newsAdapter = new NewsAdapter(getContext(), newses);
        recyclerView.setAdapter(newsAdapter);

        newsPresenter = new NewsPresenterImpl(this, newsAdapter);
        newsPresenter.loadNews();
    }

    private void initView() {
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        swipeRefreshLayout.setProgressViewOffset(true, 50, 100);
        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshLayout.setColorSchemeColors(colors);

//        swipeRefreshLayout.setBackground(getResources().getColor(R.color.gray_light));
    }

    @Override
    public void newsLoad(Boolean isLoad) {
        if (isLoad) {
            Toast.makeText(activity, "数据加载完成", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * recyclerView item 点击事件
     * @param view
     * @param position
     */
    @Override
    public void setOnItemClickListener(View view, int position) {
        this.startActivity(new Intent(activity, NewsDetailActivity.class));

    }
}
