package com.bigx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigx.app.R;
import com.bigx.beans.UserBean;
import com.bigx.tools.AsyncImageTool;

import java.util.List;

/**
 * Created by zhaoshuai on 16/9/15.
 */
public class UserAdaptor extends AbstractListAdapter<UserBean> {

    /**
     * Constructor
     * <p></p>
     * 2015年4月23日 下午11:29:52
     * @param context
     * @param data
     * @author z```s
     */
    public UserAdaptor(Context context, List<UserBean> data) {
        super(context, data);
    }

    @Override
    public long getItemId(int position) {
        return ((UserBean) getItem(position)).userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos = position;
        UserBean userBean = data.get(pos);
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.item_user_name);
            holder.portraitView = (ImageView) convertView.findViewById(R.id.item_user_portrait);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AsyncImageTool.INSTANCE.load(holder.portraitView, userBean.portraitUri);
        holder.nameView.setText(userBean.name);
        return convertView;
    }
    
    public final class ViewHolder {
        public ImageView portraitView;
        public TextView nameView;
    }
}
