package ovh.geoffrey_druelle.trustmysciencerssreader.Activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import ovh.geoffrey_druelle.trustmysciencerssreader.Adapter.FeedAdapter
import ovh.geoffrey_druelle.trustmysciencerssreader.Common.HTTPDataHandler
import ovh.geoffrey_druelle.trustmysciencerssreader.Database.Database
import ovh.geoffrey_druelle.trustmysciencerssreader.Model.RSSObject
import ovh.geoffrey_druelle.trustmysciencerssreader.R

class MainActivity : AppCompatActivity() {

    private val RSS_link = "https://trustmyscience.com/feed/"
    private val RSS_to_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Trust My Science"
        setSupportActionBar(toolbar)

        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerview.layoutManager = linearLayoutManager


        loadRSS()


        // Using DB or savedInstanceState for an Offline mode
//        if(isNetworkAvailable()){
//            loadRSS()
//        }
//        else if (!isNetworkAvailable()) {
//
//        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager){
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo.isConnected
        }
        else false
    }

    private fun loadRSS() {
        val loadRSSAsync = @SuppressLint("StaticFieldLeak")
        object:AsyncTask<String,String,String>(){
            internal var mDialog = ProgressDialog(this@MainActivity)

            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                val rssObject:RSSObject = Gson().fromJson<RSSObject>(result, RSSObject::class.java!!)
                val adapter = FeedAdapter(rssObject, baseContext)
                recyclerview.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onPreExecute() {
                mDialog.setMessage("Please wait...")
                mDialog.show()
            }

            override fun doInBackground(vararg params: String): String {
                val result:String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(params[0])
                return result
            }
        }

        val url_get_data = StringBuilder(RSS_to_JSON_API)
        url_get_data.append(RSS_link)
        loadRSSAsync.execute(url_get_data.toString())

        val db = Database(this)
        saveArticle
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_refresh)
            loadRSS()
        if(item.itemId == R.id.action_more)
            moreOptions()
        return true
    }

    private fun moreOptions() {
        Toast.makeText(this,"Plus d'options prochainement",Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}
