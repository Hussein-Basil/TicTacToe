package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val currentPlayer: String = "X"
        var result: String? = null
        var availableMoves = 9
        val board = listOf(
            listOf(
                binding.button1x1,
                binding.button1x2,
                binding.button1x3
            ),
            listOf(
                binding.button2x1,
                binding.button2x2,
                binding.button2x3
            ),
            listOf(
                binding.button3x1,
                binding.button3x2,
                binding.button3x3
            )
        )

        fun equalsThree(a: Button, b: Button, c: Button) =
            (a.text == b.text && b.text == c.text && a.text.toString() != "")

        fun checkWinner(board: List<List<Button>>): String? {
            // Check diagonally
            if (equalsThree(board[0][0], board[1][1], board[2][2])) {
                return board[0][0].text.toString()
            } else if (equalsThree(board[0][2], board[1][1], board[2][0])) {
                return board[0][0].text.toString()
            }

            for (i in 0..2) {
                // Check vertically
                if (equalsThree(board[0][i], board[1][i], board[2][i])) {
                    return board[0][i].text.toString()
                }
                // Check horizontally
                if (equalsThree(board[i][0], board[i][1], board[i][2])) {
                    return board[i][0].text.toString()
                }
            }

            if (availableMoves == 0) {
                return "tie"
            }

            return null
        }


        fun playMove(button: Button, currentPlayer: String) {
            button.text = currentPlayer.toString()
            availableMoves -= 1
        }

        fun playComputerMove(board: List<List<Button>>, player: String = "O") {
            var row: Int
            var col: Int
            do {
                row = (0..2).random()
                col = (0..2).random()
            } while (board[row][col].text != "")

            playMove(board[row][col], player)
        }

        fun showToast(result: String?) {
            Toast.makeText(
                this,
                if (result == "tie") "The result is tie!" else "$result is the winner!",
                Toast.LENGTH_LONG
            ).show()
        }

        binding.resetButton.setOnClickListener {
            result = null
            availableMoves = 9
            board.forEach { buttonsRow ->
                buttonsRow.forEach { button -> button.text = "" }
            }
        }

        board.forEach { buttonsRow ->
            buttonsRow.forEach { button ->
                button.setOnClickListener {
                    // find 2D coordinates from 1D index
                    if (button.text == "" && result == null) {
                        playMove(button, currentPlayer)

                        result = checkWinner(board)
                        if (result == null) {
                            playComputerMove(board)

                            result = checkWinner(board)
                            if (result != null) {
                                showToast(result)
                            }
                        } else {
                            showToast(result)
                        }
                    }
                }
            }
        }
    }
}