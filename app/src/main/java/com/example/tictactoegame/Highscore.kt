package com.example.tictactoegame

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Highscore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        val vsPlayer = findViewById<TextView>(R.id.vsPlayer_textView)
        val vsComp = findViewById<TextView>(R.id.vsComp_textView)
        val resetButton = findViewById<Button>(R.id.reset_highscore_button)

        val preferences = getSharedPreferences("prefFile", Context.MODE_PRIVATE)
        val editor = preferences.edit()

        val score = preferences.getString("score", "0")
        val scoreComp = preferences.getString("scoreComp", "0")
        val highScore = preferences.getString("highScore", "0")
        val highScoreComp = preferences.getString("highScoreComp", "0")

        if (score != null && highScore != null) {
            if (highScore.toInt() < score.toInt()) {
                editor.putString("highScore", score)
                editor.apply()
                vsPlayer.text = score
            } else {
                vsPlayer.text = highScore
            }
        }

        if (scoreComp != null && highScoreComp != null) {
            if (highScoreComp.toInt() < scoreComp.toInt()) {
                editor.putString("highScoreComp", scoreComp)
                editor.apply()
                vsComp.text = scoreComp
            } else {
                vsComp.text = highScoreComp
            }
        }

        resetButton.setOnClickListener {
            editor.putString("highScore", "0")
            editor.putString("score", "0")
            editor.putString("highScoreComp", "0")
            editor.putString("scoreComp", "0")
            editor.apply()

            vsPlayer.text = "0"
            vsComp.text = "0"
        }
    }
}
