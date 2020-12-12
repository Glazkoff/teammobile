package ru.catcherry.teammobile.reviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
    ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;

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

        progressBar = view.findViewById(R.id.reviewsSpinner);

        loadReviews();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.reviewsSwipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadReviewsFromSwipe();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    public void loadReviewsFromSwipe() {
        reviewsRecyclerView.setVisibility(View.GONE);
        disposables.add(api.reviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((reviewsList) -> {
                    list.clear();
                    list.addAll(reviewsList.reviews);
                    adapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                    reviewsRecyclerView.setVisibility(View.VISIBLE);
                }, (error) -> {
                    swipeContainer.setRefreshing(false);
                    reviewsRecyclerView.setVisibility(View.VISIBLE);
                }));
    }

    public void loadReviews() {
        progressBar.setVisibility(View.VISIBLE);
        reviewsRecyclerView.setVisibility(View.GONE);
        disposables.add(api.reviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((reviewsList) -> {
                    int oldSize = list.size();
                    list.clear();
                    list.addAll(reviewsList.reviews);
                    if (list.size() == oldSize && oldSize != 0) {
                        loadReviews();
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    reviewsRecyclerView.setVisibility(View.VISIBLE);
                }, (error) -> {
                    progressBar.setVisibility(View.GONE);
                    reviewsRecyclerView.setVisibility(View.VISIBLE);
                }));
    }

}
