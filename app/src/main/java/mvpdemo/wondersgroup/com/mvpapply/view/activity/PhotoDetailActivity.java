package mvpdemo.wondersgroup.com.mvpapply.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bm.library.PhotoView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvpdemo.wondersgroup.com.mvpapply.R;
import mvpdemo.wondersgroup.com.mvpapply.view.BaseActivity;

/**
 * Created by zhangwentao on 16/11/4.
 * Description : 图片详情页面
 * Version :
 */
public class PhotoDetailActivity extends BaseActivity {
    @Bind(R.id.photo_view)
    PhotoView photoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        photoView.enable();
//        photoView.

    }
}
