package com.idekhail.an1_sqlite1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object{
        // variable for database name
        private val DATABASE_NAME = "USERS6"
        // variable for database version
        private val DATABASE_VERSION = 1
        // variable for table name
        val TABLE_NAME = "users_table"
        //variable for id column
        val ID_COL = "id"
        // variable for name column
        val NAME_COl = "name"
        // variable for age column
        val AGE_COL = "age"
    }
    //method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                AGE_COL + " TEXT" + ")")
        // method for executing our query
        db.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase, v1: Int, v2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun loginName(name : String, age : String ): Cursor {
        val db = this.readableDatabase
        val cols = arrayOf(ID_COL, NAME_COl, AGE_COL)  // 1 الحقول في الجدول
        val selections = "$NAME_COl=? AND $AGE_COL=?"  // 2  الشرط
        val args = arrayOf(name, age)          // 3  بيانات المستخدم

        val cursor = db.query(TABLE_NAME, cols, selections, args, null, null, null)
        return  cursor
    }

    fun updateName(id: Int, name: String?, age: String?): Int {
        var db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COl, name)
        values.put(AGE_COL, age)
        return db.update(TABLE_NAME, values, ID_COL + " = " + id,null)
    }
    fun deleteName(id: Int){
        var db = this.writableDatabase
        db.delete(TABLE_NAME, ID_COL + "=" + id, null);
    }
    // This method is for adding data in our database
    fun addName(name : String, age : String ){
        // creating content values variable
        val values = ContentValues()
        // we are inserting our values
        values.put(NAME_COl, name)
        values.put(AGE_COL, age)
        // creating writable variable of our database
        val db = this.writableDatabase
        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)
        // closing our database
        db.close()
    }
    // get all data from our database
    fun getName(): Cursor? {
        // creating a readable variable of our database
        val db = this.readableDatabase
        // returns a cursor to read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }
}
