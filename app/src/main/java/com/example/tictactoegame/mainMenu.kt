package com.example.tictactoegame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class mainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val newPlayerGameButton = findViewById<Button>(R.id.new_game_player_button)
        val newComputerGameButton = findViewById<Button>(R.id.new_game_comp_button)
        val scoreButton = findViewById<Button>(R.id.score_button)
        val exitButton = findViewById<Button>(R.id.exit_button)

        newPlayerGameButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        newComputerGameButton.setOnClickListener {
            val intent = Intent(this, MainActivityComp::class.java)
            startActivity(intent)
        }

        scoreButton.setOnClickListener {
            val intent = Intent(this, Highscore::class.java)
            startActivity(intent)
        }

        exitButton.setOnClickListener {
            finish()
        }
    }
}
