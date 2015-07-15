package com.jiayantech.jyandroid.widget.category;

import com.jiayantech.jyandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangzili on 15/7/14.
 */
public class Category {
    public long id;
    public String title;
    public int imageId;
    public static List<Category> list;

    public Category(long id, String title, int imageId){
        this.id = id;
        this.title = title;
        this.imageId = imageId;
    }

    public static List<Category> list(){
        if(list == null){
            list = new ArrayList<>();
            list.add(new Category(1001, "美胸", R.drawable.category_breast));
            list.add(new Category(1002, "美鼻", R.drawable.category_nose));
            list.add(new Category(1003, "美脸", R.drawable.category_face));
            list.add(new Category(1004, "美眼", R.drawable.category_eye));
            list.add(new Category(1005, "美唇" , R.drawable.category_mouth));
        }
        return list;
    }
}
