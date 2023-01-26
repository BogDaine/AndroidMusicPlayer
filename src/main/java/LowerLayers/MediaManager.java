package LowerLayers;

import static android.media.MediaPlayer.SEEK_CLOSEST_SYNC;

import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.media.PlaybackParams;
import android.os.Build;
import android.util.Log;

public enum MediaManager implements MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener
{
    INSTANCE;
    MediaPlayer _mediaPlayer;
    PlaybackParams _playbackParams;
    boolean _isPrepared = false;
    boolean _isPaused = false;
    public float MinPlaybackSpeed(){return 0.01f;}
    public float MaxPlaybackSpeed(){return 5;}
    public float MinPitch(){return 0.7f;}
    public float MaxPitch(){return 1.8f;}

    private MediaManager(){
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setOnErrorListener(new MyOnErrorListener());
        _mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                _isPrepared = true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _playbackParams = new PlaybackParams();
        }
    }
    public boolean IsPrepared(){return _isPrepared;}
    public boolean IsPlayed(){return _isPaused;}
    private void PauseIfPaused(){
        if(_isPaused)
            _mediaPlayer.pause();
    }
    public void ResetPitch(){
        if(_isPrepared){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _playbackParams.setPitch(1f);
            }
            SetPlaybackParams(_playbackParams);
            PauseIfPaused();
        }
    }
    public void ResetSpeed(){
        if(_isPrepared){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _playbackParams.setSpeed(1f);
                SetPlaybackParams(_playbackParams);
                PauseIfPaused();
            }

        }
    }
    public void OnMediaPlayerError(MediaPlayer.OnErrorListener listener) {

        listener.toString();
                try {
                    if (_mediaPlayer != null) {
                        _mediaPlayer.stop();
                    }
                } catch (Exception e) {
                    System.out.println("SetOnErrorListener\nError Player Video: " + e.toString());
                }
    }
    public void SetPlaybackParams(PlaybackParams params) {
        if (_isPrepared){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                _mediaPlayer.setPlaybackParams(params);
            }
        }
        //else Log.w("MEDIA MANAGER", "Playback parameters not set!");
    }
    public PlaybackParams GetPlaybackParams(){
        return _playbackParams;
    }

    public void SeekToPercent(int percent){
        //TODO:
        //SeekBar resolution
        if(_mediaPlayer.isPlaying()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int msec = _mediaPlayer.getDuration()*percent/1000;
                _mediaPlayer.seekTo(msec, SEEK_CLOSEST_SYNC );
            }
        }
    }
    public void PlayFile(String path){
        /*if(_mediaPlayer.isPlaying()) {
            _mediaPlayer.reset();
        }*/

        try {

            _mediaPlayer.stop();
            _mediaPlayer.reset();
            _mediaPlayer.setDataSource(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PlaybackParams params = new PlaybackParams();
                if(_isPrepared) {
                    params.setPitch(1f);
                    _mediaPlayer.setPlaybackParams(params);
                    PauseIfPaused();
                }

            }
            _mediaPlayer.prepare();
            _mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void TogglePausePlayback(){
        try{
            if(_mediaPlayer.isPlaying()){
                    _mediaPlayer.pause();
                    _isPaused = true;
                }
            else {
                _mediaPlayer.start();
                _isPaused = false;
            }
        }
        catch (Exception e){
            Log.d("PAUSE FAILED", e.getMessage());
        }
    }
    public boolean IsPlaying(){
        return _mediaPlayer.isPlaying();
    }
    public int GetCurrentTrackDuration(){
        if(_mediaPlayer.isPlaying())
            return _mediaPlayer.getDuration();
        return 0;
    }
    public int GetCurrentPosition(){
        if(_mediaPlayer.isPlaying())
            return _mediaPlayer.getCurrentPosition();
        return 0;
    }
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    private class MyOnErrorListener implements MediaPlayer.OnErrorListener{

        @Override
        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
            Log.e("MP ERROR:", Integer.toString(what) + ", " + Integer.toString(extra));
            mediaPlayer.stop();
            mediaPlayer.reset();
            return true;
        }
    }
}
