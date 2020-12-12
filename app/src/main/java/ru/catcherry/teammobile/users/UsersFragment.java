package ru.catcherry.teammobile.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.R;

public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    UsersListAdapter adapter;
    List<User> list;
    ApiInterface api;
    private CompositeDisposable disposables;

    ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;


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

        progressBar = view.findViewById(R.id.usersSpinner);
        getUsers();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsersFromSwipe();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    public void getUsersFromSwipe() {
        recyclerView.setVisibility(View.GONE);
        disposables.add(api.users()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((usersList) -> {
                    list.clear();
                    recyclerView.setVisibility(View.VISIBLE);
                    list.addAll(usersList.users);
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }, (error) -> {
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeContainer.setRefreshing(false);
                }));
    }

    public void getUsers() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        disposables.add(api.users()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((usersList) -> {
                    list.clear();
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    list.addAll(usersList.users);
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }, (error) -> {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    swipeContainer.setRefreshing(false);
                }));
    }
}

