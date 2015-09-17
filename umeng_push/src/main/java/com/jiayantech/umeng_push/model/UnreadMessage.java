package com.jiayantech.umeng_push.model;

/**
 * Created by liangzili on 15/9/16.
 */
public class UnreadMessage {
    public String page;
    public long id;
    public String url;

    public UnreadMessage(String action, long id, String url){
        page = action;
        this.id = id;
        this.url = url;
    }

    public UnreadMessage(){

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof UnreadMessage){
            if(page == null && url.equals(((UnreadMessage) o).url)){
                return true;
            }else{
                if(page.equals(((UnreadMessage) o).page) && id == ((UnreadMessage) o).id){
                    return true;
                }
            }
        }

        return false;
    }

    public static UnreadMessage createUnreadMessage(BasePushMessage message){
        UnreadMessage msg = new UnreadMessage();
        if(message.data instanceof JumpToPageData){
            msg.id = ((JumpToPageData) message.data).id;
            msg.page = ((JumpToPageData) message.data).page;
        }else{
            msg.url = (String)message.data;
        }

        return msg;
    }
}
