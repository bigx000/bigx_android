/**
 * 2015年4月23日 下午11:27:39
 * ListBaseAdapter.java
 * com.yemaozi.shopkeeper.adapter
 */
package com.bigx.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * <p></p>
 * ListBaseAdapter
 * 2015年4月23日 下午11:27:39
 * @author z```s
 */
public abstract class AbstractListAdapter<T> extends BaseAdapter {
	
	protected Context context;
	
	protected List<T> data;
	
	/**
	 * Constructor
	 * <p></p>
	 * 2015年4月23日 下午11:29:52
	 * @author z```s
	 */
	public AbstractListAdapter(Context context, List<T> data) {
		this.context = context;
		this.data = data;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return data != null ? data.size() : 0;
	}
	
	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}
	
	/**
	 * @return the data
	 */
	public List<T> getData() {
		return data;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public abstract long getItemId(int position);
	
	/**
	 * <p></p>
	 * 2015年4月23日 下午11:33:33
	 * @author z```s
	 */
	public void clear() {
		if (data != null) {
			data.clear();
			notifyDataSetInvalidated();
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

}
