package android.cirkle.com.component;

import android.cirkle.com.R;
import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.model.User;
import android.cirkle.com.service.CirkleService;
import android.cirkle.com.service.UserService;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Wagdy on 4/26/2015
 */
public class MyAutoCompleteTextView<T> extends AutoCompleteTextView {

    private static final int HANDLER_MESSAGE_WHAT = 123456;

    public MyAutoCompleteTextView(Context context) {
        super(context);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_WHAT, text));
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MyAutoCompleteTextView.super.performFiltering((CharSequence) msg.obj, -1);
        }
    };
}