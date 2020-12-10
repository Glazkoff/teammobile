package ru.catcherry.teammobile.config;

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

        View.OnClickListener updateConfigClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfigsFragment.this.getActivity(), EditConfigActivity.class);
                i.putExtra("event_chance", event_chance);
                startActivity(i);
            }
        };

        updateConfig.setOnClickListener(updateConfigClick);

        disposables.add(api.configs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((configData) -> {
                    configId.setText("Редакция: #"+ configData.config.config_id);
                    event_chance = configData.config.event_chance;
                    configEventChance.setText("Шанс случайного события: "+ configData.config.event_chance);
                    configUpdatedAt.setText("Дата изменения: "+ configData.config.createdAt);
                }, (error) -> Toast.makeText(view.getContext(), "При поиске возникла ошибка:\n" + error.getMessage(),
                        Toast.LENGTH_LONG).show()));
        return view;
    }


}

