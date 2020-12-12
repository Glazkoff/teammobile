package ru.catcherry.teammobile.config;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.catcherry.teammobile.AddReviewActivity;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.MainActivity;
import ru.catcherry.teammobile.R;
import ru.catcherry.teammobile.reviews.ReviewsFragment;

public class ConfigsFragment extends Fragment {

    TextView configId, configEventChance, configUpdatedAt;
    Button updateConfig;
    Double event_chance;
    ProgressBar progressBar;

    ApiInterface api;
    private CompositeDisposable disposables;

    private SwipeRefreshLayout swipeContainer;

    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy ", Locale.getDefault());

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
            Intent intent = new Intent(ConfigsFragment.this.getActivity(), EditConfigActivity.class);
            intent.putExtra("event_chance", event_chance);
            startActivityForResult(intent, EditConfigActivity.EDIT);
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
        String old_id = configId.getText().toString();
        disposables.add(api.configs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((configData) -> {
                    if (old_id.equals(("Редакция: #"+configData.config.config_id))) {
                        getConfig();
                    }
                    configId.setText("Редакция: #"+ configData.config.config_id);
                    event_chance = configData.config.event_chance;
                    configEventChance.setText("Шанс случайного события: "+ configData.config.event_chance);
                    configUpdatedAt.setText("Дата изменения: "+ format.format(configData.config.createdAt));
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
                    configUpdatedAt.setText("Дата изменения: "+ format.format(configData.config.createdAt));
                    swipeContainer.setRefreshing(false);
                }, (error) -> swipeContainer.setRefreshing(false)));
    }

}

