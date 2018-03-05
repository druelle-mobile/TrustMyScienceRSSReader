package ovh.geoffrey_druelle.trustmysciencerssreader.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ovh.geoffrey_druelle.trustmysciencerssreader.Interface.ItemClickListener
import ovh.geoffrey_druelle.trustmysciencerssreader.Model.RSSObject
import ovh.geoffrey_druelle.trustmysciencerssreader.R
import ovh.geoffrey_druelle.trustmysciencerssreader.Activity.SelectedItemActivity

/**
 * Created by Geoffrey on 02/03/2018.
 */

class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener
{
    var _thumbnail:ImageView
    var _title:TextView
    var _date:TextView

    private var itemClickListener:ItemClickListener?=null

    init{
        _thumbnail = itemView.findViewById<ImageView>(R.id.image_view_thumbnail)
        _title = itemView.findViewById<TextView>(R.id.text_view_title)
        _date = itemView.findViewById<TextView>(R.id.text_view_date)

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }
}

class FeedAdapter(private val rssObject: RSSObject, private val mContext: Context):RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater:LayoutInflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        if (rssObject.items[position].thumbnail == "") {
            Glide.with(mContext).load(R.drawable.tms_logo).into(holder._thumbnail)
            holder._thumbnail.setBackgroundColor(Color.WHITE)
            holder._thumbnail.setPadding(10,0,10,0)
        }
        else
            Glide.with(mContext).load(rssObject.items[position].thumbnail).into(holder._thumbnail)

        holder._title.text = rssObject.items[position].title
        holder._date.text = rssObject.items[position].pubDate

        holder.setItemClickListener(ItemClickListener {view, position, isLongClick ->
            if (!isLongClick){
                val selectedItemIntent = Intent (mContext, SelectedItemActivity::class.java)
                val noHtmlContent:String = Html.fromHtml(rssObject.items[position].content).toString()
                selectedItemIntent.putExtra("title",rssObject.items[position].title)
                selectedItemIntent.putExtra("author",rssObject.items[position].author)
                selectedItemIntent.putExtra("date",rssObject.items[position].pubDate)
                selectedItemIntent.putExtra("thumbnail",rssObject.items[position].thumbnail)
                selectedItemIntent.putExtra("content",noHtmlContent)
                selectedItemIntent.putExtra("link",rssObject.items[position].link)
                view.context.startActivity(selectedItemIntent)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.item_layout, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

}