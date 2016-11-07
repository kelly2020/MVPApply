package mvpdemo.wondersgroup.com.mvpapply.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mvpdemo.wondersgroup.com.mvpapply.adapter.NewsAdapter;
import mvpdemo.wondersgroup.com.mvpapply.model.News;
import mvpdemo.wondersgroup.com.mvpapply.view.INewsView;

/**
 * Created by zhangwentao on 16/10/31.
 * Description :
 * Version :
 */
public class NewsPresenterImpl implements INewsPreserent {
    private INewsView newsView;
    private List<News> newses = new ArrayList<>();
    private List<News> moreNews = new ArrayList<>();
    private NewsAdapter adapter;


    public NewsPresenterImpl(INewsView view,NewsAdapter newsAdapter) {
        newsView =  view;
        adapter = newsAdapter;
    }

    @Override
    public void loadNews() {
        for (int i = 0; i < 10; i++) {
            News news = new News();
            news.setContent("今日有大新闻" + i);

            newses.add(news);
        }

        adapter.setAdapter(newses);
        newsView.newsLoad(true);
    }

    @Override
    public void loadMoreNews() {
        for (int i = 0; i < 5; i++) {
            News news = new News();
            news.setContent("上拉加载更多" + i);

            moreNews.add(news);
        }
        adapter.addMoreItem(moreNews);
    }
}
