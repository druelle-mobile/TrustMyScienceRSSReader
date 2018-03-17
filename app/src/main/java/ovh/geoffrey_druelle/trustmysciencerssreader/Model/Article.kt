package ovh.geoffrey_druelle.trustmysciencerssreader.Model

/**
 * Created by Geoffrey on 13/03/2018.
 */
data class Article(var id: Long, var title:String, var pubDate:String, var link:String,
                   var author:String, var thumbnail:String, var content:String){

    constructor(title: String, pubDate: String, link: String, author: String, thumbnail: String,
                content: String) : this(-1, title, pubDate, link, author, thumbnail, content)

}