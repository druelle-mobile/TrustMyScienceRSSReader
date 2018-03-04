package ovh.geoffrey_druelle.trustmysciencerssreader.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Geoffrey on 03/03/2018.
 */

private const val DATA_NAME = "tms_library.db"

private const val TABLE_NAME = "Articles"
private const val ID = "id"
private const val TITLE = "title"
private const val AUTHOR = "author"
private const val DATE = "pubDate"
private const val IMAGE = "image"
private const val DESCRIPTION = "description"
private const val CONTENT = "content"
private const val LINK = "link"

private const val CREATE_TABLE = """
CREATE TABLE IF NOT EXISTS $TABLE_NAME (
    $ID INTEGER PRIMARY KEY,
    $TITLE VARCHAR,
    $AUTHOR VARCHAR,
    $DATE VARCHAR,
    $IMAGE VARCHAR,
    $DESCRIPTION VARCHAR,
    $CONTENT VARCHAR,
    $LINK VARCHAR)
"""

private const val IF_EXISTS = """
   DROP TABLE IF EXISTS
"""

class Database(context: Context) : SQLiteOpenHelper(context, DATA_NAME, null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(IF_EXISTS+ CREATE_TABLE)
        onCreate(db)
    }
}