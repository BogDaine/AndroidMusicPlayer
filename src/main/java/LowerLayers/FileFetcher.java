package LowerLayers;

import android.os.Environment;

import com.example.mediaplayer.MainActivity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import MyUtils.SongNameFilter;

public class FileFetcher extends Thread {
    MainActivity _activity;
    File _fetchFrom;
    FilenameFilter _filter;

    public FileFetcher(MainActivity activity, File file, FilenameFilter filter) {
        _activity = activity;
        _fetchFrom = file;
        _filter = filter;
    }
    public void run() {
        FileManager.INSTANCE.FetchFilesAndUpdateActivity(_fetchFrom, _filter, _activity);
    }
}