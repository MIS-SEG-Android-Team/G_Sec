package org.rmj.guanzongroup.gsecurity.ui.components.adapter.notification;

import static org.rmj.guanzongroup.gsecurity.constants.Constants.NOTIFICATION_ALARM;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.NOTIFICATION_VISIT;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.ListItemNotificationBinding;
import org.rmj.guanzongroup.gsecurity.pojo.notification.Notification;

import java.util.List;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationViewHolder> {

    private final List<Notification> notificationList;
    private final AdapterNotificationCallback listener;

    public AdapterNotification(List<Notification> notificationList, AdapterNotificationCallback listener) {
        this.notificationList = notificationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(
                ListItemNotificationBinding.inflate(
                        LayoutInflater.from(
                                parent.getContext()
                        ),
                        parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        holder.binding.notificationTitle.setText(notification.getMessageTitle());
        holder.binding.notificationBody.setText(notification.getMessageBody());

        switch (notification.getMessageType()) {
            case NOTIFICATION_ALARM:
                holder.binding.notificationTypeIcon.setImageResource(R.drawable.ic_notification_alarm);
                break;
            case NOTIFICATION_VISIT:
                holder.binding.notificationTypeIcon.setImageResource(R.drawable.ic_location_next);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{

        public ListItemNotificationBinding binding;

        public NotificationViewHolder(ListItemNotificationBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

    }

}
