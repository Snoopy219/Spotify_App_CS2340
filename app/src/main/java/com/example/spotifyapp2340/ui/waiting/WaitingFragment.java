package com.example.spotifyapp2340.ui.waiting;

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
import com.example.spotifyapp2340.databinding.FragmentWaitingBinding;
import com.example.spotifyapp2340.databinding.FragmentWrappedBinding;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineCardAdapter;
import com.example.spotifyapp2340.ui.timeMachine.TimeMachineViewModel;
import com.example.spotifyapp2340.wrappers.Wrapped;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

/**
 * The type Time Machine fragment.
 */
public class WaitingFragment extends Fragment {

    private static FragmentWaitingBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWaitingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).setNavView(View.VISIBLE);
        binding = null;
    }
}