package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Fragments.PlayerControls;
import LowerLayers.FileManager;
import LowerLayers.MediaManager;
import MyUICallbacks.MySeekBars;
public class MainActivity extends AppCompatActivity {

    ListView _listView;
    SeekBar _seekBar;
    SeekBar _pitchSeekBar;
    String[] _songs;
    ArrayList<File> _songFiles;
    SongListAdapter _songListAdapter = new SongListAdapter();
    private ExecutorService _threadPool;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.bottom_container, PlayerControls.class, null)
                    .commit();
        }
        setContentView(R.layout.activity_main);


        RuntimePermission();
        SetUp();
        StartApp();
    }
    public void RuntimePermission()
    {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        System.exit(1);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public void StartApp() {

        DisplaySongs();
        UpdateSongList();
    }
    private AdapterView.OnItemClickListener SongClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            //PlayFile(_songFiles.get(position).getAbsolutePath());
            String path = FileManager.INSTANCE.GetAudioFile(position).GetPath();
            PlayFile(path);
            TextView textView = findViewById(R.id.searchBar);
            textView.setSelected(true);
            textView.setText(FileManager.INSTANCE.GetAudioFile(position).GetTitle());
        }
    };

    protected void SetUp()
    {
        _threadPool = Executors.newCachedThreadPool();
        _songFiles = new ArrayList<>();
        _listView = findViewById(R.id.listViewSongs);
        _listView.setOnItemClickListener(SongClickedHandler);
    }
    public void PlayFile(String path)
    {
        MediaManager.INSTANCE.PlayFile(path);
    }

    public void DisplaySongs(){
        _listView.setAdapter(_songListAdapter);
    }
    public void AddFile(File songFile){
        _songFiles.add(songFile);
        _songListAdapter.notifyDataSetChanged();
    }
    public void UpdateSongList() {
        //if(firstRun)...
        //_threadPool.execute(new FileFetcher(this, Environment.getExternalStorageDirectory(), new SongNameFilter()));
        FileManager.INSTANCE.BuildAudioFilesArrayList(getApplicationContext());
    }

    public void PauseClicked(View view) {
        MediaManager.INSTANCE.TogglePausePlayback();
    }

    class SongListAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            //return _songs.length;
            //return _songFiles.size();
            return FileManager.INSTANCE.GetAudioCount();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View songView = getLayoutInflater().inflate(R.layout.list_item, null);
            TextView songText = songView.findViewById(R.id.songName);
            songText.setSelected(true);
            //songText.setText(_songs[i]);
            /*songText.setText(_songFiles.get(i).getName());

            FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
            mmr.setDataSource(_songFiles.get(i).getAbsolutePath());
            FFmpegMediaMetadataRetriever.Metadata songMetadata = mmr.getMetadata();
            HashMap MedatadaMap = songMetadata.getAll();

            String title;
            if (MedatadaMap.containsKey(FFmpegMediaMetadataRetriever.METADATA_KEY_TITLE))
                   title = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_TITLE);
            else title = _songFiles.get(i).getName();
*/
            String title = FileManager.INSTANCE.GetAudioFile(i).GetTitle();
            songText.setText(title);
            return songView;
        }
    }
}