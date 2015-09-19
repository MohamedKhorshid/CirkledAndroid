package com.augenblick.cirkle.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

/**
 * Created by Mohamed Wagdy on 4/26/2015
 */
public class CirkleAutoCompleteTextView extends AutoCompleteTextView {

    private static final int HANDLER_MESSAGE_WHAT = 123456;

    public CirkleAutoCompleteTextView(Context context) {
        super(context);
    }

    public CirkleAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CirkleAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_WHAT, text));
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            CirkleAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, -1);
        }
    };
}