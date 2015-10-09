package com.jiayantech.jyandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.jiayantech.jyandroid.R;
import com.jiayantech.library.base.BaseSimpleModelAdapter;
import com.jiayantech.library.http.BitmapBiz;
import com.jiayantech.library.utils.GsonUtils;
import com.jiayantech.library.utils.TimeUtil;
import com.jiayantech.library.utils.Utils;
import com.jiayantech.umeng_push.UmengPushManager;
import com.jiayantech.umeng_push.model.BasePushMessage;
import com.jiayantech.umeng_push.model.JumpToPageData;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;

import java.util.List;

/**
 * Created by liangzili on 15/7/30.
 */
public class MessageAdapter extends BaseSimpleModelAdapter<BasePushMessage> {
    public static final int TYPE_REPLY = 0;
    public static final int TYPE_NORMAL = 4; //1、2、3 已经被UltimateRecyclerView占用
    private final Context mContext;

    public MessageAdapter(Context context, List<BasePushMessage> list) {
        super(list);
        mContext = context;
    }

    @Override
    public UltimateRecyclerviewViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new NormalViewHolder(viewGroup, R.layout.item_message_normal, this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_REPLY) {
            return new ReplyViewHolder(parent, R.layout.item_message_reply, this);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        BasePushMessage msg = getItem(position);
        if (msg != null) {
            if (mList.get(position).code <= UmengPushManager.CODE_COMMENT_COMMENTED) {
                return TYPE_REPLY;
            } else {
                return TYPE_NORMAL;
            }
        }

        return super.getItemViewType(position);
    }


    public class ReplyViewHolder extends NormalViewHolder {
        private TextView mTxtType;
        private TextView mTxtReplyContent;

        public ReplyViewHolder(ViewGroup parent, int layoutId) {
            this(parent, layoutId, null);
        }

        public ReplyViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<BasePushMessage> adapter) {
            super(parent, layoutId, adapter);
            mTxtType = (TextView) itemView.findViewById(R.id.txt_type);
            mTxtReplyContent = (TextView) itemView.findViewById(R.id.txt_reply_content);
        }

        @Override
        public void onBind(BasePushMessage item, int position) {
            super.onBind(item, position);
            mTxtType.setText(mContext.getResources().getString(R.string.msg_reply,
                    convertType(item.subject)));
            mTxtReplyContent.setText(item.subjectContent);
            mTxtContent.setText(item.commentContent);
        }
    }

    public class NormalViewHolder extends BaseSimpleModelAdapter.ViewHolder<BasePushMessage>
            implements View.OnClickListener {
        private ImageView mImageAvatar;
        private TextView mTxtUsername;
        private TextView mTxtDate;
        protected TextView mTxtContent;

        private String action;
        private long id;
        private String url;

        public NormalViewHolder(ViewGroup parent, int layoutId, BaseSimpleModelAdapter<BasePushMessage> adapter) {
            super(parent, layoutId, adapter);
            mImageAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            mTxtUsername = (TextView) itemView.findViewById(R.id.txt_username);
            mTxtDate = (TextView) itemView.findViewById(R.id.txt_date);
            mTxtContent = (TextView) itemView.findViewById(R.id.txt_content);
        }

        @Override
        public void onBind(BasePushMessage item, int position) {
            super.onBind(item, position);
            if (item.action == null || item.action.equals(UmengPushManager.ACTION_JUMP_TO_PAGE)) {
                //跳转到activity web页面
                JumpToPageData data = null;
                try {
                    data = GsonUtils.build().fromJson((String) item.data, JumpToPageData.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                if (data != null) {
                    action = data.page;
                    id = data.id;
                } else {
                    action = item.action;
                }
            } else {
                //跳转到web页面
                String data = (String) item.data;
                action = item.action;
                url = data;
            }
            itemView.setOnClickListener(this);
            BitmapBiz.display(mImageAvatar, item.fromUserAvatar);
            mTxtUsername.setText(item.fromUserName);
            mTxtDate.setText(TimeUtil.stamp2MonthDay((long) item.createTime * 1000));
            mTxtContent.setText(item.msg);
        }

        @Override
        public void onClick(View v) {
            if (url != null) {
                Utils.openBrowser(mContext, url);
            } else {
                Intent intent = UmengPushManager.getInstance().createActionIntent(action, id, url);
                if (intent != null) {
                    mContext.startActivity(intent);
                }
            }
        }
    }

    public String convertType(String type) {
        @StringRes int stringId;
        if (type == null) {
            return mContext.getResources().getString(R.string.message_type_default);
        }
        switch (type) {
            case "comment":
                stringId = R.string.message_type_comment;
                break;
            case "diary":
                stringId = R.string.message_type_diary;
                break;
            case "topic":
                stringId = R.string.message_type_topic;
                break;
            case "event":
                stringId = R.string.message_type_event;
                break;
            default:
                stringId = R.string.message_type_default;
        }

        return mContext.getResources().getString(stringId);
    }

}
