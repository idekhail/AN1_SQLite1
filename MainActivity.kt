package com.idekhail.an1_sqlite1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addName = findViewById<Button>(R.id.addName)
        val printName = findViewById<Button>(R.id.printName)
        val login = findViewById<Button>(R.id.login)
        val update = findViewById<Button>(R.id.update)
        val delete = findViewById<Button>(R.id.delete)
        val enterId = findViewById<EditText>(R.id.enterId)
        val enterName = findViewById<EditText>(R.id.enterName)
        val enterAge = findViewById<EditText>(R.id.enterAge)
        val Name = findViewById<TextView>(R.id.Name)
        val Age = findViewById<TextView>(R.id.Age)
        val Id = findViewById<TextView>(R.id.Id)

        login.setOnClickListener {
            Name.setText("Name\n---------\n")
            Age.setText("Age\n---------\n")
            if (enterName.text.isNullOrEmpty())
                Toast.makeText(this, " Name is Empty", Toast.LENGTH_LONG).show()
            else {
                val db = DBHelper(this, null)
                val name = enterName.text.toString()
                val age = enterAge.text.toString()

                val cursor = db.loginName(name, age)

                if (cursor.count > 0) {
                    cursor!!.moveToFirst()
                    Name.append(cursor.getString(1) + "\n")
                    Age.append(cursor.getString(2) + "\n")
                }else
                    Toast.makeText(this, " Name is Error", Toast.LENGTH_LONG).show()

                // clearing edit texts
                enterName.text.clear()
                enterAge.text.clear()

                cursor!!.close()
            }
        }

        update.setOnClickListener {
            if (enterName.text.isNullOrEmpty())
                Toast.makeText(this, " Name is Empty", Toast.LENGTH_LONG).show()
            else {
                val db = DBHelper(this, null)
                val id =  enterId.text.toString()
                val name = enterName.text.toString()
                val age = enterAge.text.toString()

                val f = db.updateName(id.toInt(), name, age)

                if (f > 0)
                    Toast.makeText(this, " $f", Toast.LENGTH_LONG).show()

                // clearing edit texts
                enterId.text.clear()
                enterName.text.clear()
                enterAge.text.clear()
            }
        }

        delete.setOnClickListener {
            if (enterId.text.isNullOrEmpty())
                Toast.makeText(this, " Name is Empty", Toast.LENGTH_LONG).show()
            else {
                val db = DBHelper(this, null)
                val id =  enterId.text.toString()

                val f = db.deleteName(id.toInt())

                enterId.text.clear()
            }
        }

        addName.setOnClickListener{
            // creating an object of DBHelper class
            val db = DBHelper(this, null)

            if (enterName.text.isNullOrEmpty())
            // Toast to message on the screen
                Toast.makeText(this, " Name is Empty", Toast.LENGTH_LONG).show()
            else {
                // get entries
                val name = enterName.text.toString()
                val age = enterAge.text.toString()

                // calling method to add name to our database
                db.addName(name, age)

                // Toast to message on the screen
                Toast.makeText(
                    this, name +" added to database", Toast.LENGTH_LONG).show()

                // clearing edit texts
                enterName.text.clear()
                enterAge.text.clear()
            }
        }

        printName.setOnClickListener{
            Id.setText("Id\n---------\n")
            Name.setText("Name\n---------\n")
            Age.setText("Age\n---------\n")
            // creating an object of DBHelper class
            val db = DBHelper(this, null)

            // Calling method to get all names from our database
            val cursor = db.getName()
            //Toast.makeText(this, " $cursor", Toast.LENGTH_LONG).show()
            if (cursor!!.moveToFirst()) {
                Id.append(cursor.getString(0) + "\n")
                Name.append(cursor.getString(1) + "\n")
                Age.append(cursor.getString(2) + "\n")

                // moving our cursor to next position
                while (cursor.moveToNext()) {
                    Id.append(cursor.getString(0) + "\n")
                    Name.append(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)) + "\n")
                    Age.append(cursor.getString(cursor.getColumnIndex(DBHelper.AGE_COL)) + "\n")
                }
            }
           // close our cursor
           cursor!!.close()
        }
    }
}
