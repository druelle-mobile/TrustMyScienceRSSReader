package ovh.geoffrey_druelle.trustmysciencerssreader.Model

/**
 * Created by Geoffrey on 01/03/2018.
 */

data class Item(val title:String, val pubDate:String, val link:String, val guid:String,
                val author:String, val thumbnail:String, val description:String,
                val content:String, val enclosure:Object, val categories:List<String>)