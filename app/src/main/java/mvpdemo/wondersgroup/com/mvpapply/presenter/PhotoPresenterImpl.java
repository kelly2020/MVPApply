package mvpdemo.wondersgroup.com.mvpapply.presenter;

import java.util.ArrayList;
import java.util.List;

import mvpdemo.wondersgroup.com.mvpapply.model.Photos;
import mvpdemo.wondersgroup.com.mvpapply.view.IPhotosView;

/**
 * Created by zhangwentao on 16/11/4.
 * Description : 图片路径添加
 * Version :
 */
public class PhotoPresenterImpl implements IPhotoPresenter {
    private List<Photos> photoses = new ArrayList<>();
    private IPhotosView photosView;


    public PhotoPresenterImpl (IPhotosView view){
        photosView = view;

    }


    @Override
    public void addPhotoPath(String[] strs) {

        for (String path:strs){
            Photos photos = new Photos();
            photos.setImgPath(path);
            photoses.add(photos);
        }

        photosView.addPhotoDone(true,photoses);
    }
}
