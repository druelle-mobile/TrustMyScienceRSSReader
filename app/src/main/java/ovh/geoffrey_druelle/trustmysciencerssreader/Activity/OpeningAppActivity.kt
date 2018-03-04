package ovh.geoffrey_druelle.trustmysciencerssreader.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import ovh.geoffrey_druelle.trustmysciencerssreader.R

class OpeningAppActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening_app)

        val mImageViewIcon: ImageView = findViewById(R.id.image_view_icon)

        val fadeInFadeOut: Animation = AnimationUtils.loadAnimation(this,R.anim.fade_in_fade_out)

        val listener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                val intent = Intent(this@OpeningAppActivity, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        }
        fadeInFadeOut.setAnimationListener(listener)
        mImageViewIcon.startAnimation(fadeInFadeOut)
    }

}
