package ru.catcherry.teammobile.users;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.catcherry.teammobile.R;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private Context context;
    private List<User> list;

    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy ", Locale.getDefault());

    UsersListAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_detail, parent, false);
        return new UsersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = list.get(position);
        holder.userId.setText("ID #"+user.user_id);
        holder.userName.setText("Имя: "+user.name);
        holder.userLogin.setText("Логин: "+user.login);
        holder.userCreatedAt.setText("Дата: "+format.format(user.createdAt));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<User> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView userId;
        TextView userLogin;
        TextView userName;
        TextView userCreatedAt;
        LinearLayout userDetailLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userId);
            userLogin = itemView.findViewById(R.id.userLogin);
            userName = itemView.findViewById(R.id.userName);
            userCreatedAt = itemView.findViewById(R.id.userCreatedAt);
            userDetailLayout = itemView.findViewById(R.id.userDetailLayout);
        }
    }
}
