package MyUtils;

import java.io.File;
import java.io.FilenameFilter;

public class SongNameFilter implements FilenameFilter {
    @Override
    public boolean accept(File file, String fileName) {
        fileName.regionMatches(true, fileName.length() - "mp3".length(), "mp3", 0,"mp3".length());
        return StringUtils.HasSuffix(fileName, ".mp3", true) ||
                StringUtils.HasSuffix(fileName, ".wav", true) ||
                StringUtils.HasSuffix(fileName, ".flac", true);
    }
}
