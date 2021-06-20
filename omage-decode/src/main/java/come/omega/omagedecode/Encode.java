package come.omega.omagedecode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.VideoSize;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;




public class Encode {

    public static File encode(InputStream stream, String filename, int type) throws IOException, EncoderException {
        String basename = FilenameUtils.getBaseName(filename);
        String extension = FilenameUtils.getExtension(filename);
        File src = File.createTempFile(basename,"."+extension);
        src.deleteOnExit();
        FileUtils.copyInputStreamToFile(stream,src);

        File tmp = File.createTempFile(basename+"_"+type,"."+extension);
        tmp.deleteOnExit();
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("aac");
        VideoAttributes video = new VideoAttributes();
        video.setCodec("h264");
        if(type==2){
            video.setSize(new VideoSize(1280, 720));
        }else if (type==1){
            video.setSize(new VideoSize(600, 360));
        }else{
            throw new RuntimeException();
        }

        EncodingAttributes tmp2 = new EncodingAttributes();
        tmp2.setOutputFormat("mp4");
        tmp2.setAudioAttributes(audio);
        tmp2.setVideoAttributes(video);
        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(src), tmp, tmp2);
        return tmp;

    }
}
