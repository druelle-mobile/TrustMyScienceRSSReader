package ovh.geoffrey_druelle.trustmysciencerssreader.Activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_selected_item.*
import ovh.geoffrey_druelle.trustmysciencerssreader.R


class SelectedItemActivity : AppCompatActivity() {

    var _link = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_item)

        val bundle:Bundle = intent.extras
        val _title:TextView = findViewById(R.id.text_view_title)
        val _author:TextView = findViewById(R.id.text_view_author)
        val _date:TextView = findViewById(R.id.text_view_date)
        val _thumbnail:ImageView = findViewById(R.id.image_view_thumbnail)
        val _content:TextView = findViewById(R.id.text_view_content)

        if(bundle!=null)
        {
            _title.text = bundle.getString("title")
            _author.text = bundle.getString("author")
            _date.text = bundle.getString("date")
            _content.text = bundle.getString("content")
            _link = bundle.getString("link")

            if (bundle.getString("thumbnail") != "")
                Glide.with(this).load(bundle.getString("thumbnail")).into(_thumbnail)
            else {
                Glide.with(this).load(R.drawable.tms_logo).into(_thumbnail)
                _thumbnail.setBackgroundColor(Color.WHITE)
            }
        }

        setSupportActionBar(toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_selected_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_share)
            shareNews()
        if(item.itemId == R.id.action_bookmark)
            bookmarkNews()
        if(item.itemId == R.id.action_home)
            onBackPressed()
        return true
    }

    private fun bookmarkNews() {
        Toast.makeText(this,"Option prochainement implémentée",Toast.LENGTH_SHORT).show()
    }

    fun shareNews() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, va voir cet article sur :" + _link )
        startActivity(Intent.createChooser(shareIntent,"Partagez sur..."))
    }

    fun OnClickButtonWebBrowser(v: View) {
        val webBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(_link))
        startActivity(Intent.createChooser(webBrowserIntent,"Ouvrez ce lien avec..."))
    }
}
