package com.zz.baseweb.tools;

import java.io.File;
import java.io.IOException;

import lombok.Data;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

import static org.bytedeco.ffmpeg.global.avcodec.AV_CODEC_ID_H264;

@Data
public class RecordVideoThread extends Thread {

    private DownloadTaskManager manager;

    private String streamURL;// 流地址 网上有自行百度
    private String filePath;// 文件路径
    private Long recordId;// 案件id
    private Integer width;
    private Integer height;

    public RecordVideoThread(DownloadTaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        System.out.println(streamURL);
        // 获取视频源
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(streamURL);
        FFmpegFrameRecorder recorder = null;
        try {
            int frameCount = 0;
            grabber.start();
            Frame frame = grabber.grabFrame();
            if (frame != null) {
                File outFile = new File(filePath);
                if (!outFile.isFile()) {
                    try {
                        outFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
                recorder = new FFmpegFrameRecorder(filePath,
                        width == null ? grabber.getImageWidth() : width,
                        height == null ? grabber.getImageHeight() : height,
                        1);
                System.out.printf("rate: %f, width: %d, height:%d", grabber.getFrameRate(), recorder.getImageWidth(), recorder.getImageHeight());
                recorder.setVideoCodec(AV_CODEC_ID_H264);// 直播流格式
                recorder.setFormat("flv");// 录制的视频格式
                recorder.setFrameRate(grabber.getFrameRate());// 帧数
                //百度翻译的比特率，默认400000，但是我400000贼模糊，调成800000比较合适
                recorder.setVideoBitrate(grabber.getVideoBitrate());
                recorder.start();
                while ((frame != null)) {
                    recorder.record(frame);// 录制
                    frame = grabber.grabFrame();// 获取下一帧
                    // 不能用flush，用了会关闭流
//                    if(frameCount++ % 1800 == 0) {
//                        recorder.flush();
//                    }
                }
                // 停止录制
                recorder.flush();
                recorder.stop();
                grabber.stop();
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } finally {
            if (null != grabber) {
                try {
                    grabber.stop();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
            if (recorder != null) {
                try {
                    recorder.stop();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                }
            }
            if (manager != null) {
                manager.completeTask(recordId);
            }
        }
    }

    public static void main(String[] args) {
        RecordVideoThread thread = new RecordVideoThread(null);
        thread.setFilePath("/Users/zuoji/Desktop/福利/LiveApp/record/testOne2.flv");
//        String streamURL = "http://www2.gui84.com:7899/zhubo/201912/m3u8/a1-CLfv59mCPykMVgllAEso/a1-CLfv59mCPykMVgllAEso.m3u8";
        String streamURL = "http://www1.86daiwei.com:9888/katong/201903/d54addad937e6432447c01b1c5739d7f/d54addad937e6432447c01b1c5739d7f.m3u8";
//        String streamURL = "http://www1.86daiwei.com:9888/katong/201903/77edd44e7d46f160ac7fe5599b30e11f/77edd44e7d46f160ac7fe5599b30e11f.m3u8";
        thread.setStreamURL(streamURL);
        thread.run();
    }
}
