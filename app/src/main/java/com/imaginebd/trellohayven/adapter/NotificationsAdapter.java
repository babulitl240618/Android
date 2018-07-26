package com.imaginebd.trellohayven.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.imaginebd.trellohayven.R;
import com.imaginebd.trellohayven.Utils;
import com.imaginebd.trellohayven.gravatar.GravatarAPI;
import com.imaginebd.trellohayven.gravatar.GravatarView;
import com.imaginebd.trellohayven.model.TrelloModel;
import com.imaginebd.trellohayven.vo.MemberVO;
import com.imaginebd.trellohayven.vo.NotificationVO;


public class NotificationsAdapter extends ArrayAdapter<NotificationVO> {

    public List<NotificationVO> mNotifications;
    private LayoutInflater mInflater;
    private TrelloModel mModel;
    
    static class ViewHolder {
        protected TextView notificationText;
        protected TextView dateText;
        protected GravatarView gravatar;
    }
    
    public NotificationsAdapter(Context context, int textViewResourceId, List<NotificationVO> notifications) {
        super(context, textViewResourceId, notifications);

        mModel = TrelloModel.getInstance();
        mInflater = LayoutInflater.from(context);
        mNotifications = notifications;
    }
    
    public void updateNotifications(List<NotificationVO> notifications) {
        mNotifications = notifications;
        notifyDataSetChanged();
    }

    public void addNotifications(List<NotificationVO> notifications) {
        mNotifications.addAll(notifications);
        notifyDataSetChanged();
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.notification_row, null);

            holder = new ViewHolder();
            holder.notificationText = (TextView)     convertView.findViewById(R.id.notification_text);
            holder.dateText         = (TextView)     convertView.findViewById(R.id.date);
            holder.gravatar         = (GravatarView) convertView.findViewById(R.id.gravatar);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NotificationVO notification = mNotifications.get(position);
        
        if (notification != null) {
            holder.notificationText.setText(Utils.getHtmlFormattedNotificationText(notification));
            holder.dateText.setText(Utils.getFormattedStringFromDate(notification.date));
            
            //MemberVO member = mModel.getAllBoardsResult().members.get(mModel.getAllBoardsResult().members.indexOf(notification.idMemberCreator));
            
            //holder.gravatar.setInitials(member.initials);
            holder.gravatar.setDefaultImageType(GravatarAPI.DEFAULT_IMAGE_404);
            holder.gravatar.setAvatarURL(GravatarAPI.getAvatarURLById(mModel.getGravatarId(notification.idMemberCreator)));
        }
        
        return convertView;
    }
}
