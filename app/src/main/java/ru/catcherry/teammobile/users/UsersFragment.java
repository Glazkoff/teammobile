package ru.catcherry.teammobile.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.ListAdapter;
import ru.catcherry.teammobile.R;
import ru.catcherry.teammobile.Review;

public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    UsersListAdapter adapter;
    List<User> list;
    ApiInterface api;
    private CompositeDisposable disposables;

    public UsersFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_list, container, false);
        list = new ArrayList<>();
        adapter = new UsersListAdapter(view.getContext(), list);
        recyclerView = view.findViewById(R.id.userList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        disposables.add(api.users()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((usersList) -> {
                    list.clear();
                    list.addAll(usersList.users);
                    adapter.notifyDataSetChanged();
                }, (error) -> Toast.makeText(view.getContext(), "При поиске возникла ошибка:\n" + error.getMessage(),
                        Toast.LENGTH_LONG).show()));
        return view;
    }
}
