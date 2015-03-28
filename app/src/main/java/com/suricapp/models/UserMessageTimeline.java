package com.suricapp.models;

/**
 * Created by maxence on 27/03/15.
 */
public class UserMessageTimeline {

    private int mUserId;
    private int mMessagePosition;

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof  UserMessageTimeline))
        {
            return false;
        }
        return ((UserMessageTimeline)o).getmUserId() == this.getmUserId();
    }

    public UserMessageTimeline() {
    }

    public UserMessageTimeline(int mUserId, int mMessagePosition) {
        this.mUserId = mUserId;
        this.mMessagePosition = mMessagePosition;
    }

    public int getmUserId() {
        return mUserId;
    }

    public void setmUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getmMessagePosition() {
        return mMessagePosition;
    }

    public void setmMessagePosition(int mMessageId) {
        this.mMessagePosition = mMessageId;
    }
}
