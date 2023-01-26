package Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mediaplayer.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import LowerLayers.MediaManager;
import MyUICallbacks.MySeekBars;

public class PlayerControls extends Fragment {
    SeekBar _seekBar;
    Timer _timer;
    private int _seekBarResolution = 1000;
    boolean _canUpdateSeekbar = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Setup();
        Log.d("PLAYER CONTROLS ", "onCreateView: ");
        return inflater.inflate(R.layout.fragment_playercontrols, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _seekBar = getView().findViewById(R.id.seekBar);
        _seekBar.setOnSeekBarChangeListener(new OnMediaSeekBarChange());
        _seekBar.setMax(_seekBarResolution);
        _timer = new Timer();

        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                UpdateSeekBar();
            }
        }, 100, 200);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void UpdateSeekBar(){
        int pos = MediaManager.INSTANCE.GetCurrentPosition();
        int dur = MediaManager.INSTANCE.GetCurrentTrackDuration();
        if(pos !=0 && dur != 0)
            _seekBar.setProgress(_seekBarResolution * pos/dur);
    }

    public void PauseClicked(View view) {
        MediaManager.INSTANCE.TogglePausePlayback();
    }
    private static class OnMediaSeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                MediaManager.INSTANCE.SeekToPercent(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
