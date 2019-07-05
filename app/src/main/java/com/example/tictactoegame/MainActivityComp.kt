package com.example.tictactoegame

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivityComp : AppCompatActivity() {

    var player1Turn = true
    var roundCount = 0
    var player1Points = 0
    var player2Points = 0

    var winStreak = 0
    var lastWinnerPlayer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button00 = findViewById<Button>(R.id.button_00)
        val button01 = findViewById<Button>(R.id.button_01)
        val button02 = findViewById<Button>(R.id.button_02)
        val button10 = findViewById<Button>(R.id.button_10)
        val button11 = findViewById<Button>(R.id.button_11)
        val button12 = findViewById<Button>(R.id.button_12)
        val button20 = findViewById<Button>(R.id.button_20)
        val button21 = findViewById<Button>(R.id.button_21)
        val button22 = findViewById<Button>(R.id.button_22)

        val buttonList = listOf(
            button00, button01, button02, button10, button11, button12, button20,
            button21, button22
        )

        for (i in 0..8) {
            buttonList[i].setOnClickListener {
                if (buttonList[i]?.text.toString() == "") {
                    buttonList[i]?.text = "X"
                    makeMove()
                    botMove(buttonList)
                }
            }
        }

        val buttonReset = findViewById<Button>(R.id.button_reset)
        buttonReset.setOnClickListener { resetGame() }

    }

    private fun botMove(buttonList: List<Button>) {
        var isOK = false
        do {
            val buttonIndex = Random.nextInt(0, 8)
            if (buttonList[buttonIndex].text == "") {
                buttonList[buttonIndex].text = "O"
                isOK = true
            }
        } while (!isOK)
        makeMove()
    }

    private fun resetGame() {
        player1Points = 0
        player2Points = 0
        winStreak = 0
        updatePointsText()
        resetBoard()
    }

    private fun makeMove() {
        roundCount++
        if (checkForWin(button_00, button_01, button_02) || checkForWin(button_10, button_11, button_12)
            || checkForWin(button_20, button_21, button_22)
            || checkForWin(button_00, button_10, button_20) || checkForWin(button_01, button_11, button_21)
            || checkForWin(button_02, button_12, button_22)
            || checkForWin(button_00, button_11, button_22) || checkForWin(button_20, button_11, button_02)) {
            if (player1Turn) {
                player1Wins()
            } else {
                player2Wins()
            }
            } else if (roundCount == 9) {
                draw()
            } else {
                player1Turn = !player1Turn
        }
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        player1Turn = !player1Turn
        resetBoard()
    }

    private fun player1Wins() {
        player1Turn = false
        player1Points++
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show()
        if (lastWinnerPlayer != 1) {
            lastWinnerPlayer = 1
            winStreak = 1
        } else
            winStreak++
        sendScore(winStreak)
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins() {
        player1Turn = true
        player2Points++
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show()
        if (lastWinnerPlayer != 2) {
            lastWinnerPlayer = 2
            winStreak = 0
        }
        updatePointsText()
        resetBoard()
    }

    private fun sendScore(score: Int) {
        val preferences = getSharedPreferences("prefFile", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("scoreComp", score.toString())
        editor.apply()
    }

    private fun resetBoard() {
        button_00.text = ""
        button_01.text = ""
        button_02.text = ""
        button_10.text = ""
        button_11.text = ""
        button_12.text = ""
        button_20.text = ""
        button_21.text = ""
        button_22.text = ""

        roundCount = 0
    }

    private fun updatePointsText() {
        text_view_player1.text = "Player 1: " + player1Points
        text_view_player2.text = "Player 2: " + player2Points
        winstraek_text_view.text = "Win streak: " + winStreak
    }

    private fun checkForWin(btn1: Button, btn2: Button, btn3: Button): Boolean {
        if (btn1.text == btn2.text && btn1.text == btn3.text && btn1.text != "")
            return true

        return false
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putInt("roundCount", roundCount)
        outState?.putInt("player1Points", player1Points)
        outState?.putInt("player2Points", player2Points)
        outState?.putBoolean("player1Turn", player1Turn)
        outState?.putInt("winStreak", winStreak)
        outState?.putInt("lastWinnerPlayer", lastWinnerPlayer)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        roundCount = savedInstanceState!!.getInt("roundCount")
        player1Points = savedInstanceState.getInt("player1Points")
        player2Points = savedInstanceState.getInt("player2Points")
        player1Turn = savedInstanceState.getBoolean("player1Turn")
        winStreak = savedInstanceState.getInt("winStreak")
        lastWinnerPlayer = savedInstanceState.getInt("lastWinnerPlayer")
    }
}
