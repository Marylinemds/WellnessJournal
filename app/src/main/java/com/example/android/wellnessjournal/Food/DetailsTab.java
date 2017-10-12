package com.example.android.wellnessjournal.Food;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.android.wellnessjournal.ExpandableListAdapter;
import com.example.android.wellnessjournal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpandableListView listView;
    public ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ExpandableListView) getView().findViewById(R.id.expandableListView);
        initData();
        listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listHash);
        listView.setAdapter(listAdapter);

    }

    private HashMap<String,List<String>> listHash;


    private OnFragmentInteractionListener mListener;

    public DetailsTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsTab.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsTab newInstance(String param1, String param2) {
        DetailsTab fragment = new DetailsTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Water intake");
        listDataHeader.add("Intermittent Fasting");
        listDataHeader.add("Special treats");
        listDataHeader.add("General comment");

        List<String> waterIntake = new ArrayList<>();
        waterIntake.add("How much water did you have today (in mL)?");

        List<String> intermittentFasting = new ArrayList<>();
        intermittentFasting.add("How many hours between last dinner and breakfast ?");


        List<String> specialTreats = new ArrayList<>();
        specialTreats.add("Processed food");
        specialTreats.add("Meat/Fish");
        specialTreats.add("Egg");
        specialTreats.add("Fried/Oil");
        specialTreats.add("white sugar");

        specialTreats.add("white flour");


        List<String> generalComment = new ArrayList<>();
        generalComment.add("Write your comment here");


        listHash.put(listDataHeader.get(0),waterIntake);
        listHash.put(listDataHeader.get(1),intermittentFasting);
        listHash.put(listDataHeader.get(2),specialTreats);
        listHash.put(listDataHeader.get(3),generalComment);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_tab, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
