package minnie.flashlight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private android.hardware.Camera camera;
    private boolean isFlashLightOn=false;
    private Camera.Parameters parameters;
    private ImageButton imageButton;
    boolean isCameraFlash;
    boolean startingActivity=false;


    class CountDown extends CountDownTimer {

        private boolean isFinished;

        public CountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            isFinished = false;

        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            //   android.hardware.Camera.
            isFinished = true;
            setFlashLightOff();
        }

        public boolean isTimerFinished() {
            return isFinished;
        }
    }
    CountDown countDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        init();
        switchClick();

    }


    public void init()
    {
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        isCameraFlash=getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

       if(!isCameraFlash)
        {
            showError();

        }

        else {
           camera = android.hardware.Camera.open();
            parameters= camera.getParameters();

        }
    }

    private void switchClick() {
     imageButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (!isFlashLightOn) {

                 setFlashLightOn();
                 countDown = new CountDown(300000, 1000);
                 countDown.start();


             } else {
                 setFlashLightOff();
             }
         }
     });
    }

    public void setFlashLightOn()
    {
        parameters = camera.getParameters();
        imageButton.setImageResource(R.drawable.fanush1);

        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);

        camera.startPreview();
        isFlashLightOn=true;




    }
    public void setFlashLightOff()
    {
        if(!countDown.isTimerFinished()) {
            countDown.cancel();
        }

        parameters=camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);

        camera.stopPreview();
        isFlashLightOn=false;
        imageButton.setImageResource(R.drawable.off);
    }
   public void showError() {

       new AlertDialog.Builder(this).setTitle("Error on Camera").setMessage("No flush in this android device")
               .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       finish();
                   }
               }).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(camera!=null)
        {
            imageButton.setImageResource(R.drawable.off);
            camera.release();
            camera=null;
            init();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(camera!=null)
        {
            imageButton.setImageResource(R.drawable.off);
            camera.release();
            camera=null;
            //init();
        }
    }

    @Override
    protected void onPause() {
        if(camera!=null)
        {
            imageButton.setImageResource(R.drawable.off);
            camera.release();
            camera=null;
            init();
        }
        super.onPause();
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(camera!=null)
        {
            imageButton.setImageResource(R.drawable.off);
            camera.release();
            camera=null;
            init();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if(camera!=null)
        {
            imageButton.setImageResource(R.drawable.off);
            camera.release();
            camera=null;
            init();
        }
        return super.onKeyUp(keyCode, event);
    }*/

    @Override
    protected void onUserLeaveHint() {

        startingActivity=false;
        if(camera!=null)
        {
            imageButton.setImageResource(R.drawable.off);
            camera.release();
            camera=null;
            init();
        }
        super.onUserLeaveHint();

    }



  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }


    @Override
    public void startActivity(Intent intent)
    {
        startingActivity = true;
        init();
        super.startActivity(intent);

    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode)
    {
        startingActivity = true;
        super.startActivityForResult(intent, requestCode);
    }*/















}


