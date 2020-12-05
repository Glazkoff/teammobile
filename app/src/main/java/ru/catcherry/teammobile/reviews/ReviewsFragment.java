package ru.catcherry.teammobile.reviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.R;


public class ReviewsFragment extends Fragment {
    RecyclerView reviewsRecyclerView;
    ReviewsListAdapter adapter;
    List<Review> list;
    ApiInterface api;
    private CompositeDisposable disposables;

    public ReviewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reviews_list, container, false);
        list = new ArrayList<>();
        adapter = new ReviewsListAdapter(view.getContext(), list);
        reviewsRecyclerView = view.findViewById(R.id.list);
        reviewsRecyclerView.setAdapter(adapter);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();

        disposables.add(api.reviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((reviewsList) -> {
                    list.clear();
                    list.addAll(reviewsList.reviews);
                    adapter.notifyDataSetChanged();
                }, (error) -> Toast.makeText(view.getContext(), "При поиске возникла ошибка:\n" + error.getMessage(),
                        Toast.LENGTH_LONG).show()));
        return view;
    }

}
