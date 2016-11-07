package mvpdemo.wondersgroup.com.mvpapply.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvpdemo.wondersgroup.com.mvpapply.R;
import mvpdemo.wondersgroup.com.mvpapply.model.Photos;

/**
 * Created by zhangwentao on 16/11/3.
 * Description :显示图片适配器
 * Version :
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Photos> photoses;
    private IPhotoItemClickListener photoItemClickListener;

    public PhotoAdapter(Context context, List<Photos> photosList) {
        mContext = context;
        photoses = photosList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoItemHolder(LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("adapter", "path=" + photoses.get(position).getImgPath());

        if (holder instanceof PhotoItemHolder) {
            final PhotoItemHolder photoItemHolder = (PhotoItemHolder) holder;

            Glide.with(mContext)
                    .load("http://p4.qhimg.com/t011a1440792f3a44b1.jpg")
                    .placeholder(R.mipmap.cover)
                    .centerCrop()
                    .into(photoItemHolder.photoImg);
        }


//        Glide.with(mContext).load(photoses.get(position).getImgPath()).into(((PhotoItemHolder)holder).photoImg);

    }

    @Override
    public int getItemCount() {
        return photoses.size();
    }

    class PhotoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.photo_img)
        ImageView photoImg;

        public PhotoItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != photoItemClickListener) {

                photoItemClickListener.setOnItemClickListener(v, getLayoutPosition());
            }
        }
    }

   public interface IPhotoItemClickListener {
        void setOnItemClickListener(View view, int position);
    }

    public void setOnPhotoItemClick(IPhotoItemClickListener clickListener) {
        photoItemClickListener = clickListener;
    }
}
