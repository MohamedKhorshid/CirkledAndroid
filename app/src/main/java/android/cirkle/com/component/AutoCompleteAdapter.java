package android.cirkle.com.component;

import android.cirkle.com.exception.CirkleException;
import android.cirkle.com.model.Cirkle;
import android.cirkle.com.service.CirkleService;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Wagdy on 4/26/2015
 */
public abstract class AutoCompleteAdapter<T> extends BaseAdapter implements Filterable {

    private List<T> values = new ArrayList<>();

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public T getItem(int i) {
        return values.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public abstract List<T> getAutoCompleteResults(CharSequence charSequence);
    public CharSequence convertResultToString(Object result) {
        return getObjectAsString((T) result);
    }

    public abstract CharSequence getObjectAsString(T object);

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null || charSequence.length() == 0) {
                    return filterResults;
                }

                List<T> results = getAutoCompleteResults(charSequence);

                if(results != null) {
                    filterResults.values = results;
                    filterResults.count = results.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                if (results != null && results.count > 0) {
                    values = (List<T>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }

            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return AutoCompleteAdapter.this.convertResultToString(resultValue);
            }
        };
    }
}
