package com.jiayantech.jyandroid.eventbus;

/**
 * Created by liangzili on 15/10/8.
 * 点赞数和评论数改变的通知
 */
public class PostStatusChangedEvent {
    public long postId;
    public int like; //1时点赞数+1， -1时点赞数-1， 0时忽略
    public int addComment; //1时评论数+1， 0时忽略;
}
