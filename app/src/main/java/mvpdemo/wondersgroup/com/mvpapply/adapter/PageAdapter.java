package mvpdemo.wondersgroup.com.mvpapply.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mvpdemo.wondersgroup.com.mvpapply.view.fragment.NewsFragment;
import mvpdemo.wondersgroup.com.mvpapply.view.fragment.PhotosFragment;

/**
 * Created by zhangwentao on 16/10/31.
 * Description :fragment 页面适配
 * Version :
 */
public class PageAdapter extends FragmentPagerAdapter {
    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return NewsFragment.getInstance();
        } else if (position == 1) {
            return PhotosFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "新闻";
        } else if (position == 1) {
            return "美图";
        }
        return null;
    }
}
