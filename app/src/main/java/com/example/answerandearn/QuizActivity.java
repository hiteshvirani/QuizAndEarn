package com.example.answerandearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.answerandearn.databinding.ActivityQuizBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    ActivityQuizBinding binding;
    private AdView mAdView;

    ArrayList<Question> questions;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswers = 0;

    int index = 0;


    private RewardedAd mRewardedAd;
    private final String TAG = "---Ad";


    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
//                loadRewardedAd();
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();
        final String catId = getIntent().getStringExtra("catId");

        Random random=new Random();
        final int rand = random.nextInt(8);

        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() < 5){
                    database.collection("categories")
                            .document(catId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index", rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                    Question question = snapshot.toObject(Question.class);
                                    questions.add(question);
                                }
                            setNextQuestion();

                        }
                    });
                }else {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();
                }
            }
        });


        resetTimer();
        setNextQuestion();



    }

    void resetTimer()
    {
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void  showAnswer(){
        if (question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
    }

    void setNextQuestion(){
        if (timer != null)
        {
            timer.cancel();
        }


        timer.start();
        if (index<questions.size()){
            binding.questionCounter.setText(String.format("%d/%d",(index+1), questions.size()));
            question = questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());
        }

    }


//    private void loadRewardedAd(){
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        RewardedAd.load(QuizActivity.this, "ca-app-pub-3940256099942544/5224354917",
//                adRequest, new RewardedAdLoadCallback() {
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error.
//                        Log.d(TAG, loadAdError.getMessage());
//                        mRewardedAd = null;
//                    }
//
//                    @Override
//                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
//                        mRewardedAd = rewardedAd;
//                        Log.d(TAG, "Ad was loaded.");
//
//
//
//                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//                            @Override
//                            public void onAdShowedFullScreenContent() {
//                                // Called when ad is shown.
//                                Log.d(TAG, "Ad was shown.");
//                            }
//
//                            @Override
//                            public void onAdFailedToShowFullScreenContent(AdError adError) {
//                                // Called when ad fails to show.
//                                Log.d(TAG, "Ad failed to show.");
//                            }
//
//                            @Override
//                            public void onAdDismissedFullScreenContent() {
//                                // Called when ad is dismissed.
//                                // Set the ad reference to null so you don't show the ad a second time.
//                                Log.d(TAG, "Ad was dismissed.");
//                                mRewardedAd = null;
//                                loadRewardedAd();
//                            }
//                        });
//
//
//
//                    }
//                });
//    }




//    private void showRewardedAd(){
//        if (mRewardedAd != null) {
//
//            mRewardedAd.show(this, new OnUserEarnedRewardListener() {
//                @Override
//                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                    // Handle the reward.
//                    Log.d(TAG, "The user earned the reward.");
//                    int rewardAmount = rewardItem.getAmount();
//                    String rewardType = rewardItem.getType();
//                }
//            });
//        } else {
//            Log.d(TAG, "The rewarded ad wasn't ready yet.");
//        }
//    }



    void checkAnswer(TextView textView){
        String selectedAnswer = textView.getText().toString();
        if (selectedAnswer.equals(question.getAnswer())){
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.quitBtn:
//                showRewardedAd();
                Intent intent1 = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if (timer != null)
                {
                    timer.cancel();
                }
                TextView selected = (TextView) view;
                checkAnswer(selected);
                break;
            case R.id.nextBtn:

                if (index <= questions.size()) {
                    index++;
                    reset();
                    setNextQuestion();
                }else {
                    reset();
//                    showRewardedAd();
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);

                    intent.putExtra("correct", correctAnswers);
                    intent.putExtra("total", questions.size());
                    startActivity(intent);
                    //Toast.makeText(this, "Quiz Finished", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}