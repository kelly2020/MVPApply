package mvpdemo.wondersgroup.com.mvpapply.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvpdemo.wondersgroup.com.mvpapply.R;
import mvpdemo.wondersgroup.com.mvpapply.adapter.PhotoAdapter;
import mvpdemo.wondersgroup.com.mvpapply.model.Photos;
import mvpdemo.wondersgroup.com.mvpapply.presenter.PhotoPresenterImpl;
import mvpdemo.wondersgroup.com.mvpapply.view.IPhotosView;
import mvpdemo.wondersgroup.com.mvpapply.view.activity.PhotoDetailActivity;

/**
 * Created by zhangwentao on 16/10/31.
 * Description :
 * Version :
 */
public class PhotosFragment extends Fragment implements IPhotosView, PhotoAdapter.IPhotoItemClickListener {
    @Bind(R.id.photo_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.photo_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private PhotoPresenterImpl photoPresenter;

    private PhotoAdapter adapter;

    private List<Photos> photoses = new ArrayList<>();

    public static PhotosFragment getInstance() {
        return new PhotosFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        photoPresenter = new PhotoPresenterImpl(this);

        initView();
        initDatas();
        initListener();
    }

    private void initListener() {
        adapter.setOnPhotoItemClick(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2500);
            }
        });

    }

    private void initDatas() {
        String[] paths = getResources().getStringArray(R.array.photos);

        photoPresenter.addPhotoPath(paths);

    }

    private void initView() {
        swipeRefreshLayout.setProgressViewOffset(true,50,200);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new PhotoAdapter(getActivity(), photoses);

        recyclerView.setAdapter(adapter);
    }


    @Override
    public void addPhotoDone(Boolean flag, List<Photos> photosList) {
        if (flag) {
            photoses.clear();
            photoses.addAll(photosList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setOnItemClickListener(View view, int position) {
        startActivity(new Intent(getActivity(), PhotoDetailActivity.class));
    }
}
