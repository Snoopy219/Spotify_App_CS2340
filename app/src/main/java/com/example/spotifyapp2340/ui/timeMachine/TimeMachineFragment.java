package com.example.spotifyapp2340.ui.timeMachine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.databinding.FragmentTimeMachineBinding;
import com.example.spotifyapp2340.wrappers.Wrapped;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

/**
 * The type Time Machine fragment.
 */
public class TimeMachineFragment extends Fragment {

    private static FragmentTimeMachineBinding binding;
    private static ArrayList<Wrapped> currWraps = new ArrayList<>();

    private static TimeMachineCardAdapter recyclerAdapter;

    private static Context context;

    private static RecyclerView pastWraps;

    private static NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimeMachineViewModel timeMachineViewModel =
                new ViewModelProvider(this).get(TimeMachineViewModel.class);

        binding = FragmentTimeMachineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();
        pastWraps = root.findViewById(R.id.recyclerview_timemachine);
        navController = MainActivity.navController;
        if (MainActivity.currUser != null) {
            recyclerAdapter = new TimeMachineCardAdapter(getContext(), MainActivity.currUser.getWraps(), navController);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            pastWraps.setLayoutManager(linearLayoutManager);
            pastWraps.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter = new TimeMachineCardAdapter(getContext(), new ArrayList<>(), navController);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            pastWraps.setLayoutManager(linearLayoutManager);
            pastWraps.setAdapter(recyclerAdapter);
        }
        ArrayList<Wrapped> currWraps = MainActivity.currUser == null ? new ArrayList<>() : MainActivity.currUser.getWraps();
        if (currWraps.size() > 0) {
//            binding.recyclerviewTimemachine.setAdapter(recyclerAdapter);
            binding.textTimeMachine.setVisibility(View.INVISIBLE);
        } else {
            binding.recyclerviewTimemachine.setVisibility(View.INVISIBLE);
        }

        if (MainActivity.currUser != null) {
            System.out.println(MainActivity.currUser.getWraps().size());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void updateUser() {
        System.out.println("UPDATING TIME FRAG");
        if (MainActivity.currUser != null && binding != null) {
            currWraps = MainActivity.currUser.getWraps();
//            recyclerAdapter = new TimeMachineCardAdapter(context, currWraps);
            if (recyclerAdapter == null) {
                recyclerAdapter = new TimeMachineCardAdapter(context, MainActivity.currUser.getWraps(), navController);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                if (pastWraps != null) {
                    pastWraps.setLayoutManager(linearLayoutManager);
                    pastWraps.setAdapter(recyclerAdapter);
                }
            }
            if (currWraps.size() > 0) {
//                binding.recyclerviewTimemachine.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
                binding.textTimeMachine.setVisibility(View.INVISIBLE);
            } else {
                binding.recyclerviewTimemachine.setVisibility(View.INVISIBLE);
            }
            System.out.println(MainActivity.currUser.getWraps().size());
        }
    }
}