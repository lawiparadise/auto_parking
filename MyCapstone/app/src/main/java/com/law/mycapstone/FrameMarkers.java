package com.law.mycapstone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.qualcomm.vuforia.CameraDevice;
import com.qualcomm.vuforia.Marker;
import com.qualcomm.vuforia.MarkerTracker;
import com.qualcomm.vuforia.Matrix44F;
import com.qualcomm.vuforia.State;
import com.qualcomm.vuforia.Tool;
import com.qualcomm.vuforia.TrackableResult;
import com.qualcomm.vuforia.Tracker;
import com.qualcomm.vuforia.TrackerManager;
import com.qualcomm.vuforia.Vec2F;
import com.qualcomm.vuforia.Vuforia;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import SampleApplication.SampleApplicationControl;
import SampleApplication.SampleApplicationException;
import SampleApplication.SampleApplicationSession;
import SampleApplication.utils.LoadingDialogHandler;
import SampleApplication.utils.SampleApplicationGLView;
import SampleApplication.utils.Texture;
//import gpio.GPIOPin;
//import gpio.OdroPin;
//import gpio.PinMode;
//import gpio.PinState;
import ui.SampleAppMenu.SampleAppMenu;
import ui.SampleAppMenu.SampleAppMenuGroup;
import ui.SampleAppMenu.SampleAppMenuInterface;

public class FrameMarkers extends Activity implements SampleApplicationControl, SampleAppMenuInterface {

    //
//    GPIOPin led;
//    GPIOPin inPin;

    //
    Button btnUp;
    Button btnDown;
    Button btnSpd;
    Button btnSee;
    Button btnPlace;

    //
    private int SPEED = 40;
    private int STOP = 30;
    private int place = 0;
    private int seeCnt = 0;
    private int BACK = 100;
    private int DELAY = 100;

    private int leftAng = 40;
    private int rightAng = 140;
    //
    private boolean OFF_SW = false;
    //
    private int value;
    private int[] pt = new int[8];
    int cntVlaue = 0;
    int speed = 40;
    ServerSocket serverSocket;
    Socket client;

    private static final String LOGTAG = "FrameMarkers";

    SampleApplicationSession vuforiaAppSession;

    // Our OpenGL view:
    private SampleApplicationGLView mGlView;

    // Our renderer:
    private FrameMarkerRenderer mRenderer;

    // The textures we will use for rendering:
    private Vector<Texture> mTextures;

    private RelativeLayout mUILayout;

    private Marker dataSet[];

    private GestureDetector mGestureDetector;

    private SampleAppMenu mSampleAppMenu;

    private LoadingDialogHandler loadingDialogHandler = new LoadingDialogHandler(
            this);

    // Alert Dialog used to display SDK errors
    private AlertDialog mErrorDialog;

    boolean mIsDroidDevice = false;


    // Called when the activity first starts or the user navigates back to an
    // activity.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);

        vuforiaAppSession = new SampleApplicationSession(this);

        startLoadingAnimation();

//        vuforiaAppSession.initAR(this, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        vuforiaAppSession.initAR(this, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mGestureDetector = new GestureDetector(this, new GestureListener());

        // Load any sample specific textures:
        mTextures = new Vector<Texture>();
        loadTextures();

        mIsDroidDevice = android.os.Build.MODEL.toLowerCase().startsWith(
                "droid");

        //
//        led = new GPIOPin(OdroPin.GPIO_24, PinMode.OUT, PinState.LOW);
//        inPin = new GPIOPin(OdroPin.GPIO_23, PinMode.IN);

//        Thread sThread = new Thread(new TCPServer());
//        sThread.start();
//        TCPServer2 tcp = new TCPServer2();
//        tcp.start();
        MyTcpServer tcp = new MyTcpServer();
        tcp.start();

        btnUp = (Button) findViewById(R.id.btn_up);
        btnDown = (Button) findViewById(R.id.btn_down);
        btnSpd = (Button) findViewById(R.id.btn_spd);
        btnPlace = (Button) findViewById(R.id.btn_place);
        btnSee = (Button) findViewById(R.id.btn_see);

        btnSpd.setText(Integer.toString(SPEED));
    }

    public void onButtonUpClicked(View v) {
        SPEED++;
        btnSpd.setText(Integer.toString(SPEED));
    }

    public void onButtonDownClicked(View v) {
        SPEED--;
        btnSpd.setText(Integer.toString(SPEED));
    }

    public void onButtonPlaceClicked(View v) {
        place++;
        if (place == 3) {
            place = 0;
        }
        btnPlace.setText(Integer.toString(place));
    }

    public void onButtonSeeClicked(View v) {
        seeCnt++;
        if (seeCnt == 3) {
            seeCnt = 0;
        }
        btnSee.setText(Integer.toString(seeCnt));
    }


    // Process Single Tap event to trigger autofocus
    private class GestureListener extends
            GestureDetector.SimpleOnGestureListener {
        // Used to set autofocus one second after a manual focus is triggered
        private final Handler autofocusHandler = new Handler();


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }


        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // Generates a Handler to trigger autofocus
            // after 1 second
            autofocusHandler.postDelayed(new Runnable() {
                public void run() {
                    boolean result = CameraDevice.getInstance().setFocusMode(
                            CameraDevice.FOCUS_MODE.FOCUS_MODE_TRIGGERAUTO);

                    if (!result)
                        Log.e("SingleTapUp", "Unable to trigger focus");
                }
            }, 1000L);

            return true;
        }
    }


    // We want to load specific textures from the APK, which we will later use
    // for rendering.
    private void loadTextures() {
        mTextures.add(Texture.loadTextureFromApk("FrameMarkers/letter_Q.png",
                getAssets()));
        mTextures.add(Texture.loadTextureFromApk("FrameMarkers/letter_C.png",
                getAssets()));
        mTextures.add(Texture.loadTextureFromApk("FrameMarkers/letter_A.png",
                getAssets()));
        mTextures.add(Texture.loadTextureFromApk("FrameMarkers/letter_R.png",
                getAssets()));
    }


    // Called when the activity will start interacting with the user.
    @Override
    protected void onResume() {
        Log.d(LOGTAG, "onResume");
        super.onResume();

        // This is needed for some Droid devices to force portrait
        if (mIsDroidDevice) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        try {
            vuforiaAppSession.resumeAR();
        } catch (SampleApplicationException e) {
            Log.e(LOGTAG, e.getString());
        }

        // Resume the GL view:
        if (mGlView != null) {
            mGlView.setVisibility(View.VISIBLE);
            mGlView.onResume();
        }

    }


    @Override
    public void onConfigurationChanged(Configuration config) {
        Log.d(LOGTAG, "onConfigurationChanged");
        super.onConfigurationChanged(config);

        vuforiaAppSession.onConfigurationChanged();
    }


    // Called when the system is about to start resuming a previous activity.
    @Override
    protected void onPause() {
        Log.d(LOGTAG, "onPause");
        super.onPause();

        if (mGlView != null) {
            mGlView.setVisibility(View.INVISIBLE);
            mGlView.onPause();
        }

        try {
            vuforiaAppSession.pauseAR();
        } catch (SampleApplicationException e) {
            Log.e(LOGTAG, e.getString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        OFF_SW = true;

    }

    // The final call you receive before your activity is destroyed.
    @Override
    protected void onDestroy() {
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();

        try {
/*x    NetworkOnMainThreadException
        if (!String.valueOf(serverSocket).equals(null)) {
                if (serverSocket.isBound()) {
                    serverSocket.close();
                }
            }
            if (!String.valueOf(client).equals(null)) {
                if (client.isBound()) {
                    client.close();
                }
            }*/


            vuforiaAppSession.stopAR();
        } catch (SampleApplicationException e) {
            Log.e(LOGTAG, e.getString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Unload texture:
        mTextures.clear();
        mTextures = null;

        System.gc();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Process the Gestures
        if (mSampleAppMenu != null && mSampleAppMenu.processEvent(event))
            return true;

        return mGestureDetector.onTouchEvent(event);
    }


    private void startLoadingAnimation() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mUILayout = (RelativeLayout) inflater.inflate(R.layout.camera_overlay,
                null, false);

        mUILayout.setVisibility(View.VISIBLE);
        mUILayout.setBackgroundColor(Color.BLACK);

        // Gets a reference to the loading dialog
        loadingDialogHandler.mLoadingDialogContainer = mUILayout
                .findViewById(R.id.loading_indicator);

        // Shows the loading indicator at start
        loadingDialogHandler
                .sendEmptyMessage(LoadingDialogHandler.SHOW_LOADING_DIALOG);

        // Adds the inflated layout to the view
        addContentView(mUILayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }


    // Initializes AR application components.
    private void initApplicationAR() {
        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = Vuforia.requiresAlpha();

        mGlView = new SampleApplicationGLView(this);
        mGlView.init(translucent, depthSize, stencilSize);

        mRenderer = new FrameMarkerRenderer(this, vuforiaAppSession);
        mRenderer.setTextures(mTextures);
        mGlView.setRenderer(mRenderer);

    }


    @Override
    public boolean doInitTrackers() {
        // Indicate if the trackers were initialized correctly
        boolean result = true;

        // Initialize the marker tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        Tracker trackerBase = trackerManager.initTracker(MarkerTracker
                .getClassType());
        MarkerTracker markerTracker = (MarkerTracker) (trackerBase);

        if (markerTracker == null) {

            Log.e(
                    LOGTAG,
                    "Tracker not initialized. Tracker already initialized or the camera is already started");
            result = false;
        } else {
            Log.i(LOGTAG, "Tracker successfully initialized");
        }

        return result;

    }


    @Override
    public boolean doLoadTrackersData() {
        TrackerManager tManager = TrackerManager.getInstance();
        MarkerTracker markerTracker = (MarkerTracker) tManager
                .getTracker(MarkerTracker.getClassType());
        if (markerTracker == null)
            return false;

        dataSet = new Marker[4];

        dataSet[0] = markerTracker.createFrameMarker(0, "MarkerQ", new Vec2F(
                50, 50));
        if (dataSet[0] == null) {
            Log.e(LOGTAG, "Failed to create frame marker Q.");
            return false;
        }

        dataSet[1] = markerTracker.createFrameMarker(1, "MarkerC", new Vec2F(
                50, 50));
        if (dataSet[1] == null) {
            Log.e(LOGTAG, "Failed to create frame marker C.");
            return false;
        }

        dataSet[2] = markerTracker.createFrameMarker(2, "MarkerA", new Vec2F(
                50, 50));
        if (dataSet[2] == null) {
            Log.e(LOGTAG, "Failed to create frame marker A.");
            return false;
        }

        dataSet[3] = markerTracker.createFrameMarker(3, "MarkerR", new Vec2F(
                50, 50));
        if (dataSet[3] == null) {
            Log.e(LOGTAG, "Failed to create frame marker R.");
            return false;
        }

        Log.i(LOGTAG, "Successfully initialized MarkerTracker.");

        return true;
    }


    @Override
    public boolean doStartTrackers() {
        // Indicate if the trackers were started correctly
        boolean result = true;

        TrackerManager tManager = TrackerManager.getInstance();
        MarkerTracker markerTracker = (MarkerTracker) tManager
                .getTracker(MarkerTracker.getClassType());
        if (markerTracker != null)
            markerTracker.start();


        return result;
    }


    @Override
    public boolean doStopTrackers() {
        // Indicate if the trackers were stopped correctly
        boolean result = true;

        TrackerManager tManager = TrackerManager.getInstance();
        MarkerTracker markerTracker = (MarkerTracker) tManager
                .getTracker(MarkerTracker.getClassType());
        if (markerTracker != null)
            markerTracker.stop();

        return result;
    }


    @Override
    public boolean doUnloadTrackersData() {
        // Indicate if the trackers were unloaded correctly
        boolean result = true;

        return result;
    }


    @Override
    public boolean doDeinitTrackers() {
        // Indicate if the trackers were deinitialized correctly
        boolean result = true;

        TrackerManager tManager = TrackerManager.getInstance();
        tManager.deinitTracker(MarkerTracker.getClassType());

        return result;
    }


    @Override
    public void onInitARDone(SampleApplicationException exception) {

        if (exception == null) {
            initApplicationAR();

            mRenderer.mIsActive = true;

            // Now add the GL surface view. It is important
            // that the OpenGL ES surface view gets added
            // BEFORE the camera is started and video
            // background is configured.
            addContentView(mGlView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            // Sets the UILayout to be drawn in front of the camera
            mUILayout.bringToFront();

            // Hides the Loading Dialog
            loadingDialogHandler
                    .sendEmptyMessage(LoadingDialogHandler.HIDE_LOADING_DIALOG);

            // Sets the layout background to transparent
            mUILayout.setBackgroundColor(Color.TRANSPARENT);

            try {
                vuforiaAppSession.startAR(CameraDevice.CAMERA.CAMERA_DEFAULT);
            } catch (SampleApplicationException e) {
                Log.e(LOGTAG, e.getString());
            }

            boolean result = CameraDevice.getInstance().setFocusMode(
                    CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO);

            if (!result)
                Log.e(LOGTAG, "Unable to enable continuous autofocus");

            mSampleAppMenu = new SampleAppMenu(this, this, "Frame Markers",
                    mGlView, mUILayout, null);
            setSampleAppMenuSettings();

        } else {
            Log.e(LOGTAG, exception.getString());
            showInitializationErrorMessage(exception.getString());
        }
    }


    // Shows initialization error messages as System dialogs
    public void showInitializationErrorMessage(String message) {
        final String errorMessage = message;
        runOnUiThread(new Runnable() {
            public void run() {
                if (mErrorDialog != null) {
                    mErrorDialog.dismiss();
                }

                // Generates an Alert Dialog to show the error message
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        FrameMarkers.this);
                builder
                        .setMessage(errorMessage)
                        .setTitle(getString(R.string.INIT_ERROR))
                        .setCancelable(false)
                        .setIcon(0)
                        .setPositiveButton(getString(R.string.button_OK),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });

                mErrorDialog = builder.create();
                mErrorDialog.show();
            }
        });
    }


    @Override
    public void onQCARUpdate(State state) {
/*        Image imageRGB565 = null;
        Frame frame = state.getFrame();
        Log.e("my onQCARUpdate frame.getNumimage", Integer.toString((int)frame.getNumImages())); //always 2?

        for (int i = 0; i < frame.getNumImages(); ++i) {
            Image image = frame.getImage(i);
            if (image.getFormat() == PIXEL_FORMAT.RGB565) {
                imageRGB565 = image;
                break;
            }
        }

        if (imageRGB565 != null) {
            ByteBuffer pixels = imageRGB565.getPixels();
            byte[] pixelArray = new byte[pixels.remaining()];
            pixels.get(pixelArray, 0, pixelArray.length);
            int imageWidth = imageRGB565.getWidth();
            int imageHeight = imageRGB565.getHeight();
            int stride = imageRGB565.getStride();

*//*            DebugLog.LOGD("Image", "Image width: " + imageWidth);
            DebugLog.LOGD("Image", "Image height: " + imageHeight);
            DebugLog.LOGD("Image", "Image stride: " + stride);
            DebugLog.LOGD("Image", "First pixel byte: " + pixelArray[0]);*//*

            Log.e("Image", "Image width: " + imageWidth);
            Log.e("Image", "Image height: " + imageHeight);
            Log.e("Image", "Image stride: " + stride);
            Log.e("Image", "First pixel byte: " + pixelArray[0]);


        }*/

        int cnt = state.getNumTrackableResults();
        for (int i = 0; i < cnt; i++) {
            cntVlaue = 1;
            switch (state.getTrackableResult(i).getTrackable().getId()) {
                case 0:
                    TrackableResult result = state.getTrackableResult(i);
                    Matrix44F matrix44F = Tool.convertPose2GLMatrix(result.getPose());
                    float[] floats = matrix44F.getData();
                    pt[0] = (int) floats[12];
                    pt[1] = (int) floats[13];
                    break;
                case 1:
                    result = state.getTrackableResult(i);
                    matrix44F = Tool.convertPose2GLMatrix(result.getPose());
                    floats = matrix44F.getData();
                    pt[2] = (int) floats[12];
                    pt[3] = (int) floats[13];
                    break;
                case 2:
                    result = state.getTrackableResult(i);
                    matrix44F = Tool.convertPose2GLMatrix(result.getPose());
                    floats = matrix44F.getData();
                    pt[4] = (int) floats[12];
                    pt[5] = (int) floats[13];
                    break;
                case 3:
                    result = state.getTrackableResult(i);
                    matrix44F = Tool.convertPose2GLMatrix(result.getPose());
                    floats = matrix44F.getData();
                    pt[6] = (int) floats[12];
                    pt[7] = (int) floats[13];
                    break;
            }
        }
        if (cnt == 2) {
            cntVlaue = 1;
        } else if(cnt == 3){
            cntVlaue = 1;
        }else if(cnt == 4){
            cntVlaue = 1;
        } else{
            cntVlaue = 0;
        }

 /*       if (cnt == 0) {
            cntVlaue = 0;
        } else {
            cntVlaue = 1;
        }
*/

//        Log.e("my status", "hi");
        value = 3;
    }

    public class TCPServer implements Runnable {

        public static final String SERVERIP = "127.0.0.1";
        public static final int SERVERPORT = 11001;


        public void run() {
            try {
                Log.d("TCP", "S: Connecting...");

                serverSocket = new ServerSocket(SERVERPORT);
                //x      serverSocket.setReuseAddress(true);
                client = serverSocket.accept();

                while (true) {
                    if (value != 0) {
                        if (cntVlaue == 1) {
                            speed = 45;
                        } else {
                            speed = 35;
                        }

                        String message = "ptx3," + Integer.toString(pt[0]) + ",pty3," + Integer.toString(pt[1]) + ",ptx4," + Integer.toString(pt[2]) + ",pty4," + Integer.toString(pt[3]) + ",ptx5," + Integer.toString(pt[4]) + ",pty5," + Integer.toString(pt[5]) + ",ptx6," + Integer.toString(pt[6]) + ",pty6," + Integer.toString(pt[7]) + ",speed," + Integer.toString(speed);
//                        String message = "ptx3,"+Integer.toString(pt[0])+",pty3,"+Integer.toString(pt[1])+",ptx4,1,pty4,2,ptx5,3,pty5,4,ptx6,5,pty6,6";
                        // Log.e("my message", message);
                        try {
//                            Log.d("TCP", "C: Sending: '" + message + "'");
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                            out.println(message);
//                            Log.d("TCP", "C: Sent.");
//                            Log.d("TCP", "C: Done.");

                        } catch (Exception e) {
                            Log.e("TCP", "S: Error", e);
                        }
                        value = 0;
                    }
                    if (OFF_SW) {
                        break;
                    }
                }
            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            } finally {
                try {
                    if (serverSocket.isBound()) {
                        serverSocket.close();
                    }
                    if (client.isBound()) {
                        client.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class TCPServer2 extends Thread {

        public TCPServer2() {
            try {
                serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(11001));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            try {
/*X                Log.e("my", "run start");
                while(true){
                    led.toggle();
                    Log.d("my", inPin.read().toString());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }*/
                while (true) {
                    Log.e("my tcp2", "wait for client..");
                    client = serverSocket.accept();
                    Log.e("my tcp2", "got it");
                    TransThread receiver = new TransThread(client);
                    receiver.start();

                    //있어봐자 무용지물
                    if (OFF_SW) {
                        break;
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                    client.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class TransThread extends Thread {

        private Socket socket;

        public TransThread(Socket socket) {
            this.socket = socket;
            for (int i = 0; i < 8; i++) {
                pt[i] = 0;
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (value != 0) {
                        if (cntVlaue == 1) {
                            speed = SPEED;
                        } else {
                            speed = STOP;
                        }

                        String message = "ptx3," + Integer.toString(pt[0]) + ",pty3," + Integer.toString(pt[1]) + ",ptx4," + Integer.toString(pt[2]) + ",pty4," + Integer.toString(pt[3]) + ",ptx5," + Integer.toString(pt[4]) + ",pty5," + Integer.toString(pt[5]) + ",ptx6," + Integer.toString(pt[6]) + ",pty6," + Integer.toString(pt[7]) + ",speed," + Integer.toString(speed);
//                        String message = "ptx3,"+Integer.toString(pt[0])+",pty3,"+Integer.toString(pt[1])+",ptx4,1,pty4,2,ptx5,3,pty5,4,ptx6,5,pty6,6";
                        Log.e("my message", message);
                        try {
//                            Log.d("TCP", "C: Sending: '" + message + "'");
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                            out.println(message);
//                            Log.d("TCP", "C: Sent.");
//                            Log.d("TCP", "C: Done.");

                        } catch (Exception e) {
                            Log.e("TCP", "S: Error", e);
                        }
                        value = 0;
                    }

                    //사실상 여기서 서버랑 클라이언트 둘 다 종료됨.
                    if (OFF_SW) {
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class MyTcpServer extends Thread {

        public MyTcpServer() {
            try {
                serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(11001));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {

                while (true) {
                    Log.e("my tcp2", "wait for client..");
                    client = serverSocket.accept();
                    Log.e("my tcp2", "got it");
                    ConnectThread receiver = new ConnectThread(client);
                    receiver.start();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class ConnectThread extends Thread {
        private Socket clientSocket;

        public ConnectThread(Socket socket) {
            clientSocket = socket;
            for (int i = 0; i < 8; i++) {
                pt[i] = 0;
            }
        }

        @Override
        public void run() {
            if (place == 0) {
                place0(clientSocket);
            } else if (place == 1) {
                place1(clientSocket);
            } else if (place == 2) {

            }


        }
    }

    private void place0(Socket clientSocket) {
        try {
            int status = 0;
            int b = 0;
            int cnt = 0;
            int plaX0 = -85;
            int plaX0_1 = -75 ;
            pt[0] = -200;
            pt[4] = -200;

            while (true) {
                if (value != 0) {
                    if (cntVlaue == 1) {
                        speed = SPEED;
                    } else {
                        speed = STOP;
                    }

                    //주차구역까지 전진
                    if (status == 0) {
                        if (pt[0] > plaX0 && pt[0] < plaX0_1) {
                            status = 1;
                            Log.e("status0", "stop");
                            send(clientSocket, STOP, 140, 4, 5);
                        } else if(pt[0] > plaX0_1){
                            send(clientSocket, BACK+speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                        }else {
                            Log.e("status0", "speed");
                            send(clientSocket, speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                            if (b == 0) {
                                b = 1;
                            }

                        }

                    }

                    //좌측 뒷바퀴가 선에 닿을때까지 전진 후 핸들 30으로
                    if (status == 1) {
                        if (cnt == 0) {
                            Log.e("status1", "stop");
                            send(clientSocket, STOP, rightAng, 4, 5);
                            cnt = 1;
                        }

                        if (pt[4] > plaX0 && pt[4] < plaX0_1 ) {
                            Log.e("status1", "stop");
                            send(clientSocket, STOP, leftAng, 4, 5);
                            status = 2;

                        }else if(pt[4] > plaX0_1){
                            send(clientSocket, BACK+speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                        }else {
                            Log.e("status1", "speed");
                            send(clientSocket, speed, rightAng, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, rightAng, 4, 5);
                            if (b == 1) {
                                b = 0;
                            }

                        }
                    }

                    //핸들을 좌로 꺾고 수평이 될때까지 후진
                    if (status == 2) {
                        if (cnt == 1) {
                            Log.e("status2", "stop");
                            send(clientSocket, STOP, leftAng, 4, 5);
                            cnt = 2;
                        }

                        if (pt[0] <= pt[4] || pt[1] >= pt[3]) {
                            Log.e("status2", "stop");
                            send(clientSocket, STOP, 90, 4, 5);
                            status = 3;
                        } else {

                            Log.e("status2", "backspeed");
                            send(clientSocket, BACK + speed, 30, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, leftAng, 4, 5);
                            if (b == 0) {
                                b = 1;
                            }

                        }
                    }

                    //바퀴를 일자로 맞추고 후진
                    if (status == 3) {
                        if (cnt == 2) {
                            Log.e("status3", "stop");
                            send(clientSocket, STOP, 90, 4, 5);
                            cnt = 3;
                        }

                        if (pt[1] <= -190) {
                            Log.e("status3", "stop");
                            send(clientSocket, STOP, 90, 4, 5);
                            status = 4;


                        } else {
                            Log.e("status3", "backspeed");
                            send(clientSocket, BACK + speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                            if (b == 1) {

                            }


                        }

                    }
                    if (status == 4) {
                        Log.e("status4", "end");
                        while (true) ;
                    }

                    value = 0;
                }

                //사실상 여기서 서버랑 클라이언트 둘 다 종료됨.
                if (OFF_SW) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void place1(Socket clientSocket) {
        try {
            int status = 0;
            int plaX0 = 115;
            int plaX0_1 = 125 ;
            int b = 0;
            int cnt = 0;


            while (true) {
                if (value != 0) {
                    if (cntVlaue == 1) {
                        speed = SPEED;
                    } else {
                        speed = STOP;
                    }

                    //주차구역까지 전진
                    if (status == 0) {
                        if (pt[0] > plaX0 && pt[0] < plaX0_1) {
                            status = 1;
                            Log.e("status0", "stop");
                            send(clientSocket, STOP, rightAng, 4, 5);
                        } else if(pt[0] > plaX0_1){
                            send(clientSocket, BACK+speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                        }else {
                            Log.e("status0", "speed");
                            send(clientSocket, speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                            if (b == 0) {
                                b = 1;
                            }

                        }

                    }

                    //좌측 뒷바퀴가 선에 닿을때까지 전진 후 핸들 30으로
                    if (status == 1) {
                        if (cnt == 0) {
                            Log.e("status1", "stop");
                            send(clientSocket, STOP, rightAng, 4, 5);
                            cnt = 1;
                        }

                        if (pt[4] > plaX0 && pt[4] < plaX0_1 ) {
                            Log.e("status1", "stop");
                            send(clientSocket, STOP, leftAng, 4, 5);
                            status = 2;

                        }else if(pt[4] > plaX0_1){
                            send(clientSocket, BACK+speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                        }else {
                            Log.e("status1", "speed");
                            send(clientSocket, speed, rightAng, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, rightAng, 4, 5);
                            if (b == 1) {
                                b = 0;
                            }

                        }
                    }

                    //핸들을 좌로 꺾고 수평이 될때까지 후진
                    if (status == 2) {
                        if (cnt == 1) {
                            Log.e("status2", "stop");
                            send(clientSocket, STOP, leftAng, 4, 5);
                            cnt = 2;
                        }

                        if (pt[0] <= pt[4] || pt[1] >= pt[3]) {
                            Log.e("status2", "stop");
                            send(clientSocket, STOP, 90, 4, 5);
                            status = 3;
                        } else {

                            Log.e("status2", "backspeed");
                            send(clientSocket, BACK + speed, leftAng, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, leftAng, 4, 5);
                            if (b == 0) {
                                b = 1;
                            }

                        }
                    }

                    //바퀴를 일자로 맞추고 후진
                    if (status == 3) {
                        if (cnt == 2) {
                            Log.e("status3", "stop");
                            send(clientSocket, STOP, 90, 4, 5);
                            cnt = 3;
                        }

                        if (pt[1] <= -190) {
                            Log.e("status3", "stop");
                            send(clientSocket, STOP, 90, 4, 5);
                            status = 4;


                        } else {
                            Log.e("status3", "backspeed");
                            send(clientSocket, BACK + speed, 90, 4, 5);
                            Thread.sleep(DELAY);
                            send(clientSocket, STOP, 90, 4, 5);
                            if (b == 1) {

                            }


                        }

                    }
                    if (status == 4) {
                        Log.e("status4", "end");
                        while (true) ;
                    }

                    value = 0;
                }

                //사실상 여기서 서버랑 클라이언트 둘 다 종료됨.
                if (OFF_SW) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Socket clientSocket, int spd, int ang, int x, int y) {
        try {

            String message = "spd," + Integer.toString(spd) + ",ang," + Integer.toString(ang) + ",x," + "4" + ",y," + "8";
            Log.e("my message", message);
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
                out.println(message);
            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final public static int CMD_BACK = -1;

    // This method sets the menu's settings
    private void setSampleAppMenuSettings() {
        SampleAppMenuGroup group;

        group = mSampleAppMenu.addGroup("", false);
        group.addTextItem(getString(R.string.menu_back), -1);

        mSampleAppMenu.attachMenu();
    }


    @Override
    public boolean menuProcess(int command) {
        boolean result = true;

        switch (command) {
            case CMD_BACK:
                finish();
                break;

        }

        return result;
    }

}
