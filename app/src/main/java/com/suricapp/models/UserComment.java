package com.suricapp.models;

/**
 * Created by maxence on 30/03/15.
 */
public class UserComment {

    private int mUserId;
    private int mCommentPosition;

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof  UserMessageTimeline))
        {
            return false;
        }
        return ((UserMessageTimeline)o).getmUserId() == this.getmUserId();
    }

    public UserComment() {
    }

    public UserComment(int mUserId, int mCommentPosition) {
        this.mUserId = mUserId;
        this.mCommentPosition = mCommentPosition;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getmCommentPosition() {
        return mCommentPosition;
    }

    public void setmCommentPosition(int mMessageId) {
        this.mCommentPosition = mMessageId;
    }
}
