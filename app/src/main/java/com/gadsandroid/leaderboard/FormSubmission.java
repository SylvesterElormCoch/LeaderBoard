package com.gadsandroid.leaderboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gadsandroid.leaderboard.api.utils.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormSubmission extends AppCompatActivity {

    private static final String BASE_URL = "https://docs.google.com/forms/d/e/";
    private static final String TAG = FormSubmission.class.getName();
    private TextView mFirstNameView;
    private TextView mlastNameView;
    private TextView mEmailView;
    private TextView mGitHubView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_submission);
        Toolbar mToolbar = findViewById(R.id.topAppBar);
        Button mSubmit = findViewById(R.id.submit_button);
        mFirstNameView = findViewById(R.id.first_name_layout);
        mlastNameView = findViewById(R.id.last_name_layout);
        mEmailView = findViewById(R.id.email_layout);
        mGitHubView = findViewById(R.id.github_layout);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmission();
            }
        });

    }

    private void handleSubmission() {
        Log.d(TAG, "handleSubmission: ");
        if (submissionIsValid()) {
            checkSubmissionConfirmed();
        }
    }

    private void clearTextFields() {
        mFirstNameView.setText("");
        mlastNameView.setText("");
        mEmailView.setText("");
        mGitHubView.setText("");
    }

    private void checkSubmissionConfirmed() {
        AlertDialog.Builder EditDialog = new AlertDialog.Builder(this);
        // Create input Layout for Dialog and InputType
        View customLayout = getLayoutInflater().inflate(R.layout.confirmation_dialog, null);
        EditDialog.setView(customLayout);
        ImageButton cancel = customLayout.findViewById(R.id.cancel);
        Button confirmButton = customLayout.findViewById(R.id.confirm);


        AlertDialog dialog = EditDialog.create();
        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                clearTextFields();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                clearTextFields();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendForm();
                clearTextFields();
            }
        });
    }

    private void sendForm() {
        String firstName = mFirstNameView.getText().toString();
        String lastName = mlastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String gitHubLink = mGitHubView.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        api.postProjectSubmission(firstName, lastName, email, gitHubLink)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            displaySuccess();
                        }else{
                            displayFailure();
                        }
                        Log.d(TAG, "onResponse: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        displayFailure();
                        Log.d(TAG, "onFailure: ");
                    }
                });
    }

    private void displayFailure() {

        AlertDialog.Builder EditDialog = new AlertDialog.Builder(this);
        // Create input Layout for Dialog and InputType
        View customLayout = getLayoutInflater().inflate(R.layout.failure_dialog, null);
        EditDialog.setView(customLayout);


        final AlertDialog dialog = EditDialog.create();
        dialog.show();
    }

    private void displaySuccess() {
        AlertDialog.Builder EditDialog = new AlertDialog.Builder(this);
        // Create input Layout for Dialog and InputType
        View customLayout = getLayoutInflater().inflate(R.layout.sucess_dialog, null);
        EditDialog.setView(customLayout);


        final AlertDialog dialog = EditDialog.create();
        dialog.show();
    }

    private boolean submissionIsValid() {
        boolean isValid = true;
        if (mFirstNameView.getText().length() == 0) {
            mFirstNameView.setError("Required");
            isValid = false;
        }
        if (mlastNameView.getText().length() == 0) {
            mlastNameView.setError("Required");
            isValid = false;

        }
        if (mEmailView.getText().length() == 0) {
            mEmailView.setError("Required");
            isValid = false;
        }

        if (mGitHubView.getText().length() == 0) {
            mGitHubView.setError("Required");
            isValid = false;
        }

        return isValid;
    }
}