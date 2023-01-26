package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.mediaplayer.R;

import LowerLayers.MediaManager;
import MyUICallbacks.MySeekBars;
import MyUtils.BasicMath;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SlidersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlidersFragment extends Fragment {

    SeekBar _pitchBar;
    SeekBar _speedBar;
    Button _resetPitchBtn;
    Button _resetSpeedBtn;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SlidersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SlidersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SlidersFragment newInstance(String param1, String param2) {
        SlidersFragment fragment = new SlidersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sliders, container, false);
        _pitchBar = view.findViewById(R.id.pitchBar);
        _speedBar = view.findViewById(R.id.speedBar);
        _pitchBar.setOnSeekBarChangeListener(new MySeekBars.OnPitchSeekBarChange());
        _speedBar.setOnSeekBarChangeListener(new MySeekBars.OnSpeedSeekBarChange());

        _resetPitchBtn = view.findViewById(R.id.btn_reset_pitch);
        _resetSpeedBtn = view.findViewById(R.id.btn_reset_speed);
        _resetPitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPitch();
            }
        });
        _resetSpeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetSpeed();
            }
        });
        //ResetSliders();

        // Inflate the layout for this fragment
        return view;
    }
    void ResetPitch(){
        MediaManager.INSTANCE.ResetPitch();
        float currentPitch = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if(MediaManager.INSTANCE.IsPrepared())
                currentPitch = MediaManager.INSTANCE.GetPlaybackParams().getPitch();
        }
        int newPitchProgress = (int) ((currentPitch-MediaManager.INSTANCE.MinPitch())/
                (MediaManager.INSTANCE.MaxPitch() - MediaManager.INSTANCE.MinPitch())*100);
        _pitchBar.setProgress(newPitchProgress);
    }
    public void ResetSpeed(){
        Log.d("SET THE SPEED", "RE");
        MediaManager.INSTANCE.ResetSpeed();
        float currentSpeed = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if(MediaManager.INSTANCE.IsPrepared())
                currentSpeed = MediaManager.INSTANCE.GetPlaybackParams().getSpeed();
        }
        int newSpeedProgress = (int) (currentSpeed/MediaManager.INSTANCE.MaxPlaybackSpeed()*100);

        _speedBar.setProgress(newSpeedProgress);
    }

    private void ResetSliders(){
        float currentPitch = 0;
        float currentSpeed = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            currentPitch = MediaManager.INSTANCE.GetPlaybackParams().getPitch();
            currentSpeed = MediaManager.INSTANCE.GetPlaybackParams().getSpeed();
        }
        int newPitchProgress = (int) BasicMath.lerp(MediaManager.INSTANCE.MinPitch(), MediaManager.INSTANCE.MaxPitch()*100, currentPitch/MediaManager.INSTANCE.MaxPitch());
        int newSpeedProgress = (int) BasicMath.lerp(MediaManager.INSTANCE.MinPlaybackSpeed(), MediaManager.INSTANCE.MaxPlaybackSpeed()*100, currentSpeed/MediaManager.INSTANCE.MaxPlaybackSpeed());

        _pitchBar.setProgress(newPitchProgress);
        _speedBar.setProgress(newSpeedProgress);

    }
    @Override
    public void onResume() {

        super.onResume();
        Log.d("RESUMEEEE", "a");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("HIDDENCHANGED", "a");
    }
}