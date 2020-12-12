package ru.catcherry.teammobile.config;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.R;

public class ConfigsFragment extends Fragment {

    TextView configId;
    TextView configEventChance;
    TextView configUpdatedAt;
    TextView configCreatedAt;
    Button updateConfig;
    Double event_chance;

    ApiInterface api;
    private CompositeDisposable disposables;

    private SwipeRefreshLayout swipeContainer;

    public ConfigsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.config_main, container, false);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        configId = view.findViewById(R.id.configId);
        configEventChance = view.findViewById(R.id.configEventChance);
        configUpdatedAt = view.findViewById(R.id.configUpdatedAt);
        updateConfig = view.findViewById(R.id.updateConfig);

        event_chance = 1.0;

        View.OnClickListener updateConfigClick = v -> {
            Intent i = new Intent(ConfigsFragment.this.getActivity(), EditConfigActivity.class);
            i.putExtra("event_chance", event_chance);
            startActivity(i);
        };

        updateConfig.setOnClickListener(updateConfigClick);

        swipeContainer = view.findViewById(R.id.configSwipeContainer);
        swipeContainer.setOnRefreshListener(this::getConfigFromSwipe);
        swipeContainer.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getConfig();
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void getConfig() {
        disposables.add(api.configs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((configData) -> {
                    configId.setText("Редакция: #"+ configData.config.config_id);
                    event_chance = configData.config.event_chance;
                    configEventChance.setText("Шанс случайного события: "+ configData.config.event_chance);
                    configUpdatedAt.setText("Дата изменения: "+ configData.config.createdAt);
                    swipeContainer.setRefreshing(false);
                }, (error) -> swipeContainer.setRefreshing(false)));
    }

    @SuppressLint("SetTextI18n")
    public void getConfigFromSwipe() {
        disposables.add(api.configs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((configData) -> {
                    configId.setText("Редакция: #"+ configData.config.config_id);
                    event_chance = configData.config.event_chance;
                    configEventChance.setText("Шанс случайного события: "+ configData.config.event_chance);
                    configUpdatedAt.setText("Дата изменения: "+ configData.config.createdAt);
                    swipeContainer.setRefreshing(false);
                }, (error) -> swipeContainer.setRefreshing(false)));
    }


}

