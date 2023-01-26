package LowerLayers;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.BaseAdapter;

import com.example.mediaplayer.MainActivity;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import DataObjects.MediaStoreAudio;
import MyUtils.SongNameFilter;
import wseemann.media.FFmpegMediaMetadataRetriever;

public enum FileManager {
    INSTANCE;

    private ArrayList<MediaStoreAudio> _audioFiles = new ArrayList<>();

    public void FetchFilesAndUpdateActivity(File file, FilenameFilter filter, MainActivity activity){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Files.walkFileTree(
                        Paths.get(file.getAbsolutePath()),
                        new HashSet<FileVisitOption>(Arrays.asList(FileVisitOption.FOLLOW_LINKS)),
                        Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                                    throws IOException {
                                File f = file.toFile();
                                if(Files.isRegularFile(file) && new SongNameFilter().accept(f, f.getName()))
                                {
                                    activity.runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            activity.AddFile(f);
                                        }
                                    });
                                    Log.d("file added:", f.getName());
                                }
                                return FileVisitResult.CONTINUE;
                            }

                            @Override
                            public FileVisitResult visitFileFailed(Path file, IOException e)
                                    throws IOException {
                                return FileVisitResult.SKIP_SUBTREE;
                            }

                            @Override
                            public FileVisitResult preVisitDirectory(Path dir,
                                                                     BasicFileAttributes attrs)
                                    throws IOException {
                                return FileVisitResult.CONTINUE;
                            }
                        });
            }
        } catch (IOException e) {
            // handle exception
        }
    }

    public void BuildAudioFilesArrayList(Context context) {

        ArrayList<MediaStoreAudio> musicInfos = new ArrayList<MediaStoreAudio>();


        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return;
        }


        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

/*
            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.));*/

            //if (isMusic != 0) {



                String path = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));

                if (!new File(path).exists()) {
                    continue;
                }
                //FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
                //mmr.setDataSource(new File(path).getAbsolutePath());
                //FFmpegMediaMetadataRetriever.Metadata songMetadata = mmr.getMetadata();
                //HashMap MedatadaMap = songMetadata.getAll();



                long id = cursor.getLong(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media._ID));

                String title;

                title = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));


                /*if (MedatadaMap.containsKey(FFmpegMediaMetadataRetriever.METADATA_KEY_TITLE))
                    title = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_TITLE);
                else title = new File(path).getName();*/
                //title = cursor.getString(cursor
                //        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));


                String album = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));


                String artist = cursor.getString(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

                long duration = cursor
                        .getLong(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                short genre = cursor.getShort(cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.GENRE));

                /*MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(Uri.fromFile() path);
                byte[] byteArr = mmr.getEmbeddedPicture();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap albumArt = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length, options);
                mmr.release();*/

                //TODO:
                //Optimize bitmaps so they dont kill your phone xd
/*



                byte[] byteArr = mmr.getEmbeddedPicture();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap albumArt = BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length, options);
                mmr.release();
*/

                MediaStoreAudio audio = new MediaStoreAudio(id, title, album, artist, path, genre, duration);
                _audioFiles.add(audio);
                //musicInfos.add(audio);
            }
        //}

        //return musicInfos;
    }

    public int GetAudioCount(){
        return _audioFiles.size();
    }
    public MediaStoreAudio GetAudioFile(int i){
        return _audioFiles.get(i);
    }
}
