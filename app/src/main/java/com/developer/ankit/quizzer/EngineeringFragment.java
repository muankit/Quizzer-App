package com.developer.ankit.quizzer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class EngineeringFragment extends Fragment {

    private Button mEnggChemistryBtn , mEnggPhysicsBtn , mEnggMathematicsBtn;

    private View enggFragmentView;

    private TextView mEnggChemTV , mEnggPhyTV , mEnggMathTV;

    public EngineeringFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        enggFragmentView =  inflater.inflate(R.layout.fragment_engineering, container, false);

        mEnggChemistryBtn = (Button) enggFragmentView.findViewById(R.id.chemistryEngineeringButton);
        mEnggPhysicsBtn = (Button) enggFragmentView.findViewById(R.id.physicsEngineeringButton);
        mEnggMathematicsBtn = (Button) enggFragmentView.findViewById(R.id.mathematicsEngineeringbutton);

        mEnggChemTV = (TextView) enggFragmentView.findViewById(R.id.enggChemTextView);
        mEnggPhyTV = (TextView) enggFragmentView.findViewById(R.id.enggPhyTextView);
        mEnggMathTV = (TextView) enggFragmentView.findViewById(R.id.enggMathTextView);

        mEnggChemistryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enggChemIntent = new Intent(getActivity() , QuizStartingActivity.class);
                enggChemIntent.putExtra("enggChem" , mEnggChemTV.getText().toString());
                enggChemIntent.putExtra("enggChemIntent" , true);
                startActivity(enggChemIntent);
            }
        });

        mEnggPhysicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enggPhyIntent = new Intent(getActivity() , QuizStartingActivity.class);
                enggPhyIntent.putExtra("enggPhy" , mEnggPhyTV.getText().toString());
                enggPhyIntent.putExtra("enggPhyIntent" , true);
                startActivity(enggPhyIntent);
            }
        });

        mEnggMathematicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enggMathIntent = new Intent(getActivity() , QuizStartingActivity.class);
                enggMathIntent.putExtra("enggMath" , mEnggMathTV.getText().toString());
                enggMathIntent.putExtra("enggMathIntent" , true);
                startActivity(enggMathIntent);
            }
        });

        return enggFragmentView;
    }
}
