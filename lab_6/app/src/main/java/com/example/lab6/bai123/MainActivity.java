package com.example.lab6.bai123;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab6.R;

public class MainActivity extends AppCompatActivity {
    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml,
            btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml,
            btnZoomOutCode, btnRotateXml,
            btnRotateCode, btnMoveXml, btnMoveCode, btnSlideUpXml, btnSlideUpCode,
            btnBounceXml,
            btnBounceCode, btnCombineXml, btnCombineCode;
    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    private void findViewsByIds() {
        ivUitLogo = (ImageView) findViewById(R.id.iv_uit_logo);
        btnFadeInXml = (Button) findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode = (Button) findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml = (Button) findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = (Button) findViewById(R.id.btn_fade_out_code);
        btnBlinkXml = (Button) findViewById(R.id.btn_blink_xml);
        btnBlinkCode = (Button) findViewById(R.id.btn_blink_code);
        btnZoomInXml = (Button) findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode = (Button) findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml = (Button) findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = (Button) findViewById(R.id.btn_zoom_out_code);
        btnRotateXml = (Button) findViewById(R.id.btn_rotate_xml);
        btnRotateCode = (Button) findViewById(R.id.btn_rotate_code);
        btnMoveXml = (Button) findViewById(R.id.btn_move_xml);
        btnMoveCode = (Button) findViewById(R.id.btn_move_code);
        btnSlideUpXml = (Button) findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = (Button) findViewById(R.id.btn_slide_up_code);
        btnBounceXml = (Button) findViewById(R.id.btn_bounce_xml);
        btnBounceCode = (Button) findViewById(R.id.btn_bounce_code);
        btnCombineXml = (Button) findViewById(R.id.btn_combine_xml);
        btnCombineCode = (Button) findViewById(R.id.btn_combine_code);
    }
    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Stopped",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        findViewsByIds();
        initVariables();
        // Handle click event for animation initiated from xml
        handleClickAnimationXml(btnFadeInXml, R.anim.anim_fade_in);
        handleClickAnimationXml(btnFadeOutXml, R.anim.anim_fade_out);
        handleClickAnimationXml(btnBlinkXml, R.anim.anim_blink);
        handleClickAnimationXml(btnZoomInXml, R.anim.anim_zoom_in);
        handleClickAnimationXml(btnZoomOutXml, R.anim.anim_zoom_out);
        handleClickAnimationXml(btnRotateXml, R.anim.anim_rotate);
        handleClickAnimationXml(btnMoveXml, R.anim.anim_move);
        handleClickAnimationXml(btnSlideUpXml, R.anim.anim_slide_up);
        handleClickAnimationXml(btnBounceXml, R.anim.anim_bounce);
        handleClickAnimationXml(btnCombineXml, R.anim.anim_combine);

        // Handle click event for animation initiated from code
        handleClickAnimationCode(btnFadeInCode, initFadeInAnimation());
        handleClickAnimationCode(btnFadeOutCode, initFadeOutAnimation());
        handleClickAnimationCode(btnBlinkCode, initBlinkAnimation());
        handleClickAnimationCode(btnZoomInCode, initZoomInAnimation());
        handleClickAnimationCode(btnZoomOutCode, initZoomOutAnimation());
        handleClickAnimationCode(btnRotateCode, initRotateAnimation());
        handleClickAnimationCode(btnMoveCode, initMoveAnimation());
        handleClickAnimationCode(btnSlideUpCode, initSlideUpAnimation());
        handleClickAnimationCode(btnBounceCode, initBounceAnimation());
        handleClickAnimationCode(btnCombineCode, initCombinedAnimation());

        ivUitLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNewActivity = new Intent(MainActivity.this, NewActivity.class);
                startActivity(iNewActivity);
                //overridePendingTransition(R.anim.anim_fade_in,R.anim.anim_fade_out);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
    private void handleClickAnimationXml(Button btn, int aminId){
        final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, aminId);

        animation.setAnimationListener(animationListener);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
    private void handleClickAnimationCode(Button btn, final Animation animation) {
        // Handle onclickListenner to start animation
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
            }
        });
    }
    private Animation initFadeInAnimation(){
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setAnimationListener(
                animationListener
        );
        return animation;
    }
    private Animation initFadeOutAnimation(){
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setAnimationListener(
                animationListener
        );
        return animation;
    }
    private Animation initBlinkAnimation() {
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(500); // Fade in duration
        fadeIn.setFillAfter(true);

        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setDuration(500); // Fade out duration
        fadeOut.setFillAfter(true);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);

        return animation;
    }
    private Animation initZoomInAnimation() {
        ScaleAnimation zoomInAnimation = new ScaleAnimation(
                0f, 3f, // Start and end scale X
                0f, 3f, // Start and end scale Y
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point X
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point Y
        );
        zoomInAnimation.setDuration(1000); // Duration of animation in milliseconds
        zoomInAnimation.setFillAfter(true); // Keeps the state of the animation after it ends
        return zoomInAnimation;
    }

    private Animation initZoomOutAnimation() {
        ScaleAnimation zoomOutAnimation = new ScaleAnimation(
                1f,
                0f, // Start and end scale X
                1f, 0f, // Start and end scale Y
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point X
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point Y
        );
        zoomOutAnimation.setDuration(1000); // Duration of animation in milliseconds
        zoomOutAnimation.setFillAfter(true); // Keeps the state of the animation after it ends
        return zoomOutAnimation;
    }
    private Animation initRotateAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360, // Start and end rotation degrees
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point X
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point Y
        );
        rotateAnimation.setDuration(1000); // Duration of animation in milliseconds
        rotateAnimation.setFillAfter(true); // Keeps the state of the animation after it ends
        return rotateAnimation;
    }

    private Animation initMoveAnimation() {
        TranslateAnimation moveAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, // Start X position
                Animation.RELATIVE_TO_SELF, 0.5f, // End X position
                Animation.RELATIVE_TO_SELF, 0f, // Start Y position
                Animation.RELATIVE_TO_SELF, 0.5f // End Y position
        );
        moveAnimation.setDuration(1000); // Duration of animation in milliseconds
        moveAnimation.setFillAfter(true); // Keeps the state of the animation after it ends
        return moveAnimation;
    }
    private Animation initSlideUpAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, // fromXScale
                1.0f, // toXScale
                1.0f, // fromYScale
                0.0f, // toYScale
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotXType và pivotXValue (điểm neo X)
                Animation.RELATIVE_TO_SELF, 0.5f // pivotYType và pivotYValue (điểm neo Y)
        );

        // Đặt thời gian của animation
        scaleAnimation.setDuration(500);

        // Đặt thuộc tính fillAfter để giữ lại trạng thái cuối cùng của animation
        scaleAnimation.setFillAfter(true);

        return scaleAnimation;
    }

    private Animation initBounceAnimation() {
        ScaleAnimation bounceAnimation = new ScaleAnimation(
                0.8f, 1.2f, // Start and end scale X
                0.8f, 1.2f, // Start and end scale Y
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point X
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot point Y
        );
        bounceAnimation.setDuration(500); // Duration of animation in milliseconds
        bounceAnimation.setInterpolator(new BounceInterpolator()); // Apply bounce effect
        return bounceAnimation;
    }
    private Animation initCombinedAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        // Slide-up animation
        TranslateAnimation slideUpAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f
        );
        slideUpAnimation.setDuration(1000);

        // Bounce animation
        ScaleAnimation bounceAnimation = new ScaleAnimation(
                0.8f, 1.2f,
                0.8f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        bounceAnimation.setDuration(500);
        bounceAnimation.setInterpolator(new BounceInterpolator());

        // Add both animations to the animation set
        animationSet.addAnimation(slideUpAnimation);
        animationSet.addAnimation(bounceAnimation);

        return animationSet;
    }


}