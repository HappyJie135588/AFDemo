package com.example.huangjie.afdemo.uis.activities.tab_b_media_record;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.huangjie.afdemo.R;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.afbaselibrary.utils.AFLog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MediaRecorderActivity extends BaseSwipeBackActivity {


    public static final int REQUEST_CUSTOMER_VIDEO_CAPTURE = 2;
    @BindView(R.id.rl_options)
    public RelativeLayout rl_options;
    @BindView(R.id.iv_flash)
    public ImageView iv_flash;
    @BindView(R.id.iv_switch)
    public ImageView iv_switch;
    @BindView(R.id.rl_time)
    public RelativeLayout rl_time;
    @BindView(R.id.iv_point)
    public ImageView iv_point;
    @BindView(R.id.chronometer)
    public Chronometer chronometer;
    @BindView(R.id.surfaceview)
    public SurfaceView mSurfaceView;
    @BindView(R.id.iv_play)
    public ImageView iv_play;
    @BindView(R.id.iv_capture)
    public ImageView iv_capture;
    @BindView(R.id.iv_local)
    public ImageView iv_local;
    @BindView(R.id.iv_fail)
    public ImageView iv_fail;
    @BindView(R.id.iv_pass)
    public ImageView iv_pass;

    private AnimationDrawable anim;//视频录制动画
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mediaPlayer;//播放视频的类
    private boolean isRecording = false;
    private int time = 0;

    private int cameraPosition = 1;//0代表后置摄像头，1代表前置摄像头
    private File mFilePath;

    @Override
    public int getContentView() {
        return R.layout.activity_media_recorder;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setContentView(R.layout.activity_media_recorder);
//        ButterKnife.bind(this);
//        mPresenter = new MediaRecorderPresenter(this, mSurfaceView);
//        mPresenter.start();
//    }


    @OnClick(R.id.iv_flash)
    public void iv_flash() {
        Camera.Parameters parameters = mCamera.getParameters();
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
            // 开启闪光灯
            if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            }
        } else if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // 关闭闪光灯
            if (supportedFlashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            }
        }
        mCamera.setParameters(parameters);
        iv_flash.setSelected(!iv_flash.isSelected());
    }

    @OnClick(R.id.iv_switch)
    public void iv_switch() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    changeCamera(i);
                    cameraPosition = 0;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    changeCamera(i);
                    cameraPosition = 1;
                    break;
                }
            }
        }
    }

    private void changeCamera(int cameraId) {
        mCamera.stopPreview();//停掉原来摄像头的预览
        mCamera.release();//释放资源
        mCamera = getCameraInstance(cameraId);
        initCamera();
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    private Camera getCameraInstance(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId); // attempt to get a Camera instance
        } catch (Exception e) {
            AFLog.d(e.getMessage());
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private void initCamera() {
        mCamera.setDisplayOrientation(90);
        Camera.Parameters parameters = mCamera.getParameters();
        /*add by HJ 实现Camera自动对焦 begin*/
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes != null) {
            for (String mode : focusModes) {
                mode.contains("continuous-video");
                parameters.setFocusMode("continuous-video");
            }
        }
        /*add by HJ end*/
        /*add by HJ 拉伸   begin*/
        Camera.Size size = parameters.getPreferredPreviewSizeForVideo();
        int videoWidth = size.width;
        int videoHeight = size.height;
        parameters.setPreviewSize(videoWidth, videoHeight);
        /*add by HJ end*/
        mCamera.setParameters(parameters);
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
            AFLog.d("Error starting camera preview: " + e.getMessage());
        }
    }

    @OnClick(R.id.iv_capture)
    public void iv_capture() {
        if (isRecording) {
            // stop recording and release camera
            mMediaRecorder.stop();  // stop the recording
            releaseMediaRecorder(); // release the MediaRecorder object
            releaseCamera();
            // inform the user that recording has stopped
            //mTimer.cancel();
            capturedUI();
            isRecording = false;
        } else {
            // initialize video camera
            if (prepareVideoRecorder()) {
                // Camera is available and unlocked, MediaRecorder is prepared,
                // now you can start recording
                mMediaRecorder.start();
                capturingUI();
                // inform the user that recording has started
                isRecording = true;
            } else {
                // prepare didn't work, release the camera
                releaseMediaRecorder();
                // inform user
            }
        }
    }

    private boolean prepareVideoRecorder() {

        //mCamera = getCameraInstance();
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        mMediaRecorder.setVideoSize(1280, 720);
        // 每秒 4帧
        mMediaRecorder.setVideoFrameRate(15);
        mMediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);

        // Step 4: Set output file
        mFilePath = getOutputMediaFile();
        mMediaRecorder.setOutputFile(mFilePath.toString());

        //解决录制视频, 播放器横向问题
        if (cameraPosition == 1) {//后置
            mMediaRecorder.setOrientationHint(90);
        } else {//前置
            mMediaRecorder.setOrientationHint(270);
        }

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (Exception e) {
            AFLog.d("Exception preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AFDemo");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                AFLog.d("failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();      // release the mediaPlayer for other applications
            mediaPlayer = null;
        }
    }

    public void capturingUI() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        anim = (AnimationDrawable) iv_point.getBackground();
        anim.start();//开启动画
        rl_options.setVisibility(View.GONE);
        iv_local.setVisibility(View.GONE);
        rl_time.setVisibility(View.VISIBLE);
        iv_capture.setImageResource(R.drawable.paisheing);
    }

    public void capturedUI() {
        chronometer.stop();
        anim.stop();
        iv_point.setVisibility(View.INVISIBLE);
        iv_fail.setVisibility(View.VISIBLE);
        iv_pass.setVisibility(View.VISIBLE);
        iv_play.setVisibility(View.VISIBLE);
        iv_capture.setVisibility(View.INVISIBLE);
    }

    public void playingUI() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        iv_play.setVisibility(View.GONE);
    }

    public void playedUI() {
        chronometer.stop();
        iv_play.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_local)
    public void iv_local() {
        startActivity(LocalVideoListActivity.class);
        finish();
    }

    @OnClick(R.id.iv_fail)
    public void iv_fail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to give up ths video?");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mFilePath.exists()) {
                    //不保存直接删掉
                    mFilePath.delete();
                }
                showToast("放弃录制的视频");
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

    @OnClick(R.id.iv_pass)
    public void iv_pass() {
        showToast("跳转到编辑页");
        //EditVideoActivity.actionStart(this, mFilePath);
    }

    @OnClick(R.id.iv_play)
    public void iv_play() {
        playingUI();
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.reset();
        Uri uri = Uri.parse(mFilePath.toString());
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playedUI();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
        releaseMediaPlayer();
    }

    @OnClick(R.id.iv_back)
    public void iv_back() {
        finish();
    }

}
