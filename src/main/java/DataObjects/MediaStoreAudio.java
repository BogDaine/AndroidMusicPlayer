package DataObjects;

import android.graphics.Bitmap;

public class MediaStoreAudio {

    private long _id;
    private String _title;
    private String _album;
    private String _artist;
    private String _path;
    private short _genre;
    private long _duration;

    private Bitmap _albumArt;

    public MediaStoreAudio(long id,
                           String title,
                           String album,
                           String artist,
                           String path,
                           short genre,
                           long duration
                           /*, Bitmap albumArt*/){
        _id = id;
        _title = title;
        _album = album;
        _artist = artist;
        _path = path;
        _genre = genre;
        _duration = duration;
        //_albumArt = albumArt;
    }

    public String toString() {
        return String.format("songId: %d, Title: %s, Artist: %s, Path: %s, Genere: %d, Duration %s",
                _id, _title, _artist, _path, _genre, _duration);
    }
    public String GetTitle(){
        return _title;
    }
    public String GetAlbum() {
        return _album;
    }
    public String GetArtist(){
        return _artist;
    }
    public String GetPath(){
        return _path;
    }
    public short GetGenre(){
        //TODO:
        //stringify this
        return _genre;
    }
    public long GetDuration(){
        return _duration;
    }


}