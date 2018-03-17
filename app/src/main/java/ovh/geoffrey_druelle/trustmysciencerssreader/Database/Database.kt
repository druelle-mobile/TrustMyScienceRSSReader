package ovh.geoffrey_druelle.trustmysciencerssreader.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import ovh.geoffrey_druelle.trustmysciencerssreader.Model.Article

/**
 * Created by Geoffrey on 03/03/2018.
 */

private const val DATA_NAME = "tms_library.db"

private const val TABLE_NAME = "Articles"
private const val ID = "id"
private const val TITLE = "title"
private const val AUTHOR = "author"
private const val DATE = "pubDate"
private const val THUMBNAIL = "thumbnail"
private const val CONTENT = "content"
private const val LINK = "link"

private const val CREATE_TABLE = """
CREATE TABLE IF NOT EXISTS $TABLE_NAME (
    $ID INTEGER PRIMARY KEY,
    $TITLE VARCHAR,
    $AUTHOR VARCHAR,
    $DATE VARCHAR,
    $THUMBNAIL VARCHAR,
    $CONTENT VARCHAR,
    $LINK VARCHAR)
"""

private const val IF_EXISTS = """
   DROP TABLE IF EXISTS
"""

private const val ARTICLE_QUERY_SELECT_ALL = "SELECT * FROM $TABLE_NAME"


class Database(context: Context) : SQLiteOpenHelper(context, DATA_NAME, null, 1){

    val TAG = Database::class.java.simpleName

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(IF_EXISTS+ CREATE_TABLE)
        onCreate(db)
    }

    fun saveArticle(article: Article) : Boolean {
        val values = ContentValues()
        values.put(TITLE, article.title)
        values.put(AUTHOR, article.author)
        values.put(DATE, article.pubDate)
        values.put(THUMBNAIL, article.thumbnail)
        values.put(CONTENT, article.content)
        values.put(LINK, article.link)

        return when {
            article.id < 0 -> {
                article.id = createArticle(values)
                article.id > 0
            }
            else -> updateArticle(article.id, values)
        }
    }

    fun getAllArticles() : MutableList<Article> {
        val articles = mutableListOf<Article>()

        readableDatabase.rawQuery(ARTICLE_QUERY_SELECT_ALL, null).use { cursor ->
            while (cursor.moveToNext()) {
                val article = Article(
                        cursor.getLong(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(TITLE)),
                        cursor.getString(cursor.getColumnIndex(AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(DATE)),
                        cursor.getString(cursor.getColumnIndex(THUMBNAIL)),
                        cursor.getString(cursor.getColumnIndex(CONTENT)),
                        cursor.getString(cursor.getColumnIndex(LINK))
                )
                articles.add(article)
            }
        }

        return articles
    }

    fun deleteArticle(article: Article) : Boolean {
        val deleteCount = writableDatabase.delete(TABLE_NAME,
                "$ID = ?",
                arrayOf("${article.id}"))
        return deleteCount == 1
    }

    private fun createArticle(values: ContentValues) : Long {
        Log.d(TAG, "Creating article: $values")
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    private fun updateArticle(id: Long, values: ContentValues) : Boolean {
        Log.d(TAG, "Updating article: $values")
        val updateCount = writableDatabase.update(TABLE_NAME,
                values,
                "$ID = ?",
                arrayOf("$id"))
        return updateCount > 0
    }
}