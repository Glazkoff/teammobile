package ru.catcherry.teammobile.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.catcherry.teammobile.R;

public class ConfigsListAdapter extends RecyclerView.Adapter<ConfigsListAdapter.ViewHolder> {

    private Context context;
    private List<Config> list;

    ConfigsListAdapter(Context context, List<Config> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ConfigsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail, parent, false);
        return new ConfigsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Config config = list.get(position);
        holder.configId.setText("ID #"+config.config_id);
        holder.eventChance.setText("Вероятность: "+config.event_chance);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView configId;
        TextView eventChance;
        LinearLayout configDetailLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            configId = itemView.findViewById(R.id.config_id);
            eventChance = itemView.findViewById(R.id.event_chance);
            configDetailLayout = itemView.findViewById(R.id.configDetailLayout);
        }
    }
}
