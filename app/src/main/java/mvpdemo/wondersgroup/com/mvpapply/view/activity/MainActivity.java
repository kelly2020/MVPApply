package mvpdemo.wondersgroup.com.mvpapply.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvpdemo.wondersgroup.com.mvpapply.R;
import mvpdemo.wondersgroup.com.mvpapply.adapter.PageAdapter;
import mvpdemo.wondersgroup.com.mvpapply.view.BaseActivity;

/**
 * 首页面
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
//        // App Logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
//// Title
//        toolbar.setTitle("My Title");
//// Sub Title
//        toolbar.setSubtitle("Sub title");

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.mipmap.back);


        adapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(2);// viewpager 页面数
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
