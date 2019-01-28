package com.example.topmovies.CustomChoice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.topmovies.R;

public class CustomChoiceFragment extends Fragment implements View.OnClickListener {

   private CustomModel customModel;
   private EditText vote, rating, year;
   private Button confirm, cancel;
   private ChoiceFragmentListener listener;
   private FrameLayout layout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_choise, container, false);
        vote = view.findViewById(R.id.choice_fragment_vote);
        rating = view.findViewById(R.id.choice_fragment_rating);
        year = view.findViewById(R.id.choice_fragment_year);
        confirm = view.findViewById(R.id.choice_fragment_confirm_btn);
        cancel = view.findViewById(R.id.choice_fragment_cancel_btn);
        layout = view.findViewById(R.id.choice_fragment_layout);
        layout.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choice_fragment_confirm_btn:
           if (!checkEditFields())return;
                customModel = new CustomModel(Integer.parseInt(vote.getText().toString()), Double.parseDouble(rating.getText().toString()), Integer.parseInt(year.getText().toString()));
                if (listener == null) return;
                hideKeyboard();
                getActivity().onBackPressed();
                listener.onConfirmClicked(customModel);
                break;
            case R.id.choice_fragment_cancel_btn:
                hideKeyboard();
                getActivity().onBackPressed();
                break;
            case R.id.choice_fragment_layout:
                hideKeyboard();
                getActivity().onBackPressed();
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChoiceFragmentListener) {
            listener = (ChoiceFragmentListener) context;
        }
    }


    private boolean checkEditFields(){
        if(!TextUtils.isDigitsOnly(vote.getText().toString()) || TextUtils.isEmpty(vote.getText().toString()) ||  Integer.parseInt(vote.getText().toString())<0){
            vote.setError("only positive numbers");
            return false;

        }
        if( TextUtils.isEmpty(rating.getText().toString()) || Double.parseDouble(rating.getText().toString())<0.0 ||Double.parseDouble(rating.getText().toString()) > 10.0){
            rating.setError("only positive and not over 10 numbers ");
            return false;
        }

        if(!TextUtils.isDigitsOnly(year.getText().toString()) || TextUtils.isEmpty(year.getText().toString()) || Integer.parseInt(year.getText().toString())<0 ){
            year.setError("only positive numbers ");
            return false;
        }



        return true;
    }

    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
