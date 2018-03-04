package ovh.geoffrey_druelle.trustmysciencerssreader.Model

/**
 * Created by Geoffrey on 01/03/2018.
 */

data class RSSObject(val status:String, val feed:Feed, val items:List<Item>) {
    companion object {
        fun getItems(): MutableList<RSSObject>? {
            val items = mutableListOf<RSSObject>()

            return items
        }
    }

}