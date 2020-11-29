package com.rosseti.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rosseti.MainActivity;
import com.rosseti.R;
import com.rosseti.base.BaseFragment;
import com.rosseti.models.BaseModel;
import com.rosseti.models.Suggestion;
import com.rosseti.network.ApiClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.jetbrains.annotations.NotNull;

public class ProjectDetailsFragment extends BaseFragment {

    private TextView tvExistingSolution;
    private ImageButton btnVideoPreviewNow;
    private ImageView imageViewExistingSolution;

    private TextView tvProposedSolution;
    private ImageButton btnVideoPreviewProposedSolution;
    private ImageView imageViewProposedSolution;

    private ImageView imagePreviewExistingSolution;
    private ImageView imagePreviewProposedSolution;

    private TextView tvPositiveEffect;
    private RatingBar ratingBar;

    private RelativeLayout rlVideoExistingSolution;
    private RelativeLayout rlVideoProposedSolution;

    private LinearLayout llMediaExistingSolution;
    private LinearLayout llMediaProposedSolution;

    private Suggestion suggestion = new Suggestion();

    public ProjectDetailsFragment(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_details, container, false);

        tvExistingSolution = view.findViewById(R.id.tvExistingSolution);
        btnVideoPreviewNow = view.findViewById(R.id.btnVideoPreviewNow);
        imageViewExistingSolution = view.findViewById(R.id.imageViewExistingSolution);

        tvProposedSolution = view.findViewById(R.id.tvProposedSolution);
        btnVideoPreviewProposedSolution = view.findViewById(R.id.btnVideoPreviewProposedSolution);
        imageViewProposedSolution = view.findViewById(R.id.imageViewProposedSolution);

        tvPositiveEffect = view.findViewById(R.id.tvPositiveEffect);
        ratingBar = view.findViewById(R.id.ratingBar);

        rlVideoExistingSolution = view.findViewById(R.id.rlVideoExistingSolution);
        rlVideoProposedSolution = view.findViewById(R.id.rlVideoProposedSolution);

        imagePreviewExistingSolution = view.findViewById(R.id.imagePreviewExistingSolution);
        imagePreviewProposedSolution = view.findViewById(R.id.imagePreviewProposedSolution);

        tvPositiveEffect.setText(suggestion.getPositive_effect() != null ? suggestion.getPositive_effect() : "");


        //EXISTING SOLUTION
        if (suggestion.getExisting_solution_text() != null) {
            tvExistingSolution.setText(suggestion.getExisting_solution_text());
        }

        if (suggestion.getExisting_solution_image() != null) {
            imageViewExistingSolution.setVisibility(View.VISIBLE);
            Picasso.get().load(suggestion.getExisting_solution_image()).error(R.drawable.logo).into(imageViewExistingSolution);
        } else {
            imageViewExistingSolution.setVisibility(View.INVISIBLE);
        }

        if (suggestion.getExisting_solution_video() == null || suggestion.getExisting_solution_video().isEmpty()) {
            rlVideoExistingSolution.setVisibility(View.INVISIBLE);
        }

        btnVideoPreviewNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playVideo = new Intent(Intent.ACTION_VIEW);
                playVideo.setDataAndType(Uri.parse(suggestion.getExisting_solution_video()), "video/mp4");
                startActivity(playVideo);
//                ((MainActivity) getMainActivity()).pushFragment(new VideoFragment(suggestion.getExisting_solution_video()), true);
            }
        });

        llMediaExistingSolution = view.findViewById(R.id.llMediaExistingSolution);

        if (suggestion.getExisting_solution_video() == null || suggestion.getExisting_solution_video().isEmpty() &&
                suggestion.getExisting_solution_image() == null){
            llMediaExistingSolution.setVisibility(View.GONE);
        }


        //PROPOSED SOLUTION
        if (suggestion.getProposed_solution_image() != null) {
            imageViewProposedSolution.setVisibility(View.VISIBLE);
            new Picasso.Builder(getContext()).build().load(suggestion.getProposed_solution_image()).into(imageViewProposedSolution);
        } else {
            imageViewProposedSolution.setVisibility(View.INVISIBLE);
        }

        if (suggestion.getProposed_solution_video() == null || suggestion.getProposed_solution_video().isEmpty()) {
            rlVideoProposedSolution.setVisibility(View.INVISIBLE);
        }

        if (suggestion.getProposed_solution_text() != null) {
            tvProposedSolution.setText(suggestion.getProposed_solution_text());
        }

        llMediaProposedSolution = view.findViewById(R.id.llMediaProposedSolution);
        if (suggestion.getProposed_solution_video() == null || suggestion.getProposed_solution_video().isEmpty() &&
                suggestion.getProposed_solution_image() == null){
            llMediaProposedSolution.setVisibility(View.GONE);
        }

        btnVideoPreviewProposedSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent playVideo = new Intent(Intent.ACTION_VIEW);
                playVideo.setDataAndType(Uri.parse(suggestion.getProposed_solution_video()), "video/mp4");
                startActivity(playVideo);
//                ((MainActivity) getMainActivity()).pushFragment(new VideoFragment(suggestion.getProposed_solution_video()), true);
            }
        });


        //POSITIVE EFFECT
        if (suggestion.getPositive_effect() != null) {
            tvPositiveEffect.setText(suggestion.getPositive_effect());
        }

        if (suggestion.getRating() != null) {
            ratingBar.setRating(suggestion.getRating());
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                showProgress();
                ApiClient.getApi().setRating(suggestion.getId(), (int) rating).enqueue(new Callback<BaseModel>() {
                    @Override
                    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                        hideProgress();
                    }

                    @Override
                    public void onFailure(Call<BaseModel> call, Throwable t) {
                        hideProgress();
                    }
                });
            }
        });


        RelativeLayout rlComments = view.findViewById(R.id.rlComments);
        rlComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getMainActivity()).pushFragment(new ChatFragment(suggestion), true);
            }
        });

        return view;
    }

    @NotNull
    @Override
    public String getTitle() {
        return "Проекты";
    }

    @Override
    public void setTitle(@NotNull String title) {

    }

}
