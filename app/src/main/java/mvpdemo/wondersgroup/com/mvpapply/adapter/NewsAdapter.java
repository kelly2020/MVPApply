package mvpdemo.wondersgroup.com.mvpapply.adapter;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvpdemo.wondersgroup.com.mvpapply.R;
import mvpdemo.wondersgroup.com.mvpapply.model.News;

/**
 * Created by zhangwentao on 16/10/31.
 * Description :支持多个 item 布局 以及使用了 注解
 * Version :
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_FOOTER = 0; //加载更多 item view
    private final int TYPE_ITEM = 1;// 普通 item View

    private final int STATUS_LOAD_MORE = 0; //0 上拉加载跟多
    private final int STATUS_LOADING = 1;// 1 正在加载

    private Context mContext;
    private List<News> newses;
    private int footerStatus = 0;//  加载状态标记

    private RecyclerViewClickInterface itemClickInterface;

    public NewsAdapter(Context context, List<News> newsList) {
        mContext = context;
        newses = newsList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据viewType 来判断 显示 加载更多还是 正常item
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.load_more_item, parent, false));
        } else if (viewType == TYPE_ITEM) {
            return new NewsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {

            ((NewsViewHolder) holder).contentView.setText(newses.get(position).getContent());
            holder.itemView.setTag(position);

        } else if (holder instanceof FooterViewHolder) {
            // 根据状态判断 当前是否需要加载数据
            FooterViewHolder footerViewHolder = ((FooterViewHolder) holder);
            switch (footerStatus) {
                case STATUS_LOAD_MORE:
                    footerViewHolder.progressBar.setVisibility(View.GONE);
                    footerViewHolder.loadMore.setText("上拉加载更多...");
                    break;
                case STATUS_LOADING:
                    footerViewHolder.progressBar.setVisibility(View.VISIBLE);
                    footerViewHolder.loadMore.setText("正在加载...");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return newses.size() + 1;
    }


    /**
     * adapter 添加数据
     *
     * @param newsList
     */
    public void setAdapter(List<News> newsList) {
        newses = newsList;
        notifyDataSetChanged();

    }

    /**
     * 上拉加载更多添加列表内容
     *
     * @param newsList
     */
    public void addMoreItem(List<News> newsList) {
        newses.addAll(newsList);
//        notifyDataSetChanged();
    }


    /**
     * 上拉刷新状态更新
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        footerStatus = status;
        notifyDataSetChanged();
    }

    /**
     * 列表 item
     */
    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.news_item_content)
        TextView contentView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Log.e("FooterViewHolder","click.........");
            itemClickInterface.setOnItemClickListener(v, getLayoutPosition());
        }
    }

    /**
     * 列表 加载更多
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.load_more_view)
        TextView loadMore;
        @Bind(R.id.load_more_progress)
        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public interface RecyclerViewClickInterface {
        void setOnItemClickListener(View view, int position);
    }

    public void setOnItemClickListener(RecyclerViewClickInterface clickListener) {
        itemClickInterface = clickListener;
    }

}
