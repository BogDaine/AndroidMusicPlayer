package MyUICallbacks;

import android.media.PlaybackParams;
import android.os.Build;
import android.util.Log;
import android.widget.SeekBar;

import LowerLayers.MediaManager;
import MyUtils.BasicMath;

public class MySeekBars {
    private MySeekBars(){}

    public static class OnMediaSeekBarChange implements SeekBar.OnSeekBarChangeListener {
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
    public static class OnPitchSeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            float progress = seekBar.getProgress();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PlaybackParams params = MediaManager.INSTANCE.GetPlaybackParams();
                float newPitch = (float) BasicMath.lerp(MediaManager.INSTANCE.MinPitch(), MediaManager.INSTANCE.MaxPitch(), progress/100);
                params.setPitch(newPitch);
                Log.d("PITCH:", Float.toString(newPitch));
                MediaManager.INSTANCE.SetPlaybackParams(params);
            }
        }
    };

    public static class OnSpeedSeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            float progress = seekBar.getProgress();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PlaybackParams params = MediaManager.INSTANCE.GetPlaybackParams();
                float newSpeed = (float) BasicMath.lerp(MediaManager.INSTANCE.MinPitch(), MediaManager.INSTANCE.MaxPitch(), progress/100);
                params.setSpeed(newSpeed);
                Log.d("SPEED:", Float.toString(newSpeed));
                MediaManager.INSTANCE.SetPlaybackParams(params);
            }
        }
    };}
