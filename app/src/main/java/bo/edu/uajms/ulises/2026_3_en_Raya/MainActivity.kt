package bo.edu.uajms.danilonieves.a2026_tresenraya

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import android.widget.Button
import android.widget.TextView

class MainActivity: AppCompatActivity(){


    //Controls
    private lateinit var BTNTablero : Array<Button>
    private lateinit var TXVPlayer: TextView
    private lateinit var BTNRestart: Button
    private lateinit var Tablero: Array<Array<String>>
    //Variables
    private val rows=3
    private val cols=3
    private var currentPlayer=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize Variables
        BTNTablero=arrayOf(
            findViewById(R.id.BTN00),
            findViewById(R.id.BTN01),
            findViewById(R.id.BTN02),
            findViewById(R.id.BTN10),
            findViewById(R.id.BTN11),
            findViewById(R.id.BTN12),
            findViewById(R.id.BTN20),
            findViewById(R.id.BTN21),
            findViewById(R.id.BTN22)
        )
        TXVPlayer = findViewById(R.id.TxVPlayer)
        BTNRestart=findViewById(R.id.BTNRestart)

        Tablero= Array(rows){ Array(cols){""} }

        //Events
        for (i in BTNTablero.indices){
            val row=i/rows
            val col=i%cols
            BTNTablero[i].setOnClickListener(){
                Log.d("Click","Hiciste click en ($row,$col)")
                click(row,col,BTNTablero[i])
            }
        }
        BTNRestart.setOnClickListener() {
            Log.d("Click","Hiciste click en Reiniciar")
            enableGame()
        }
    }



    //Functions
    private fun click(row: Int, col: Int, button: Button) {
        if (button.text=="")
        {
            if (currentPlayer==0)
            {
                button.setText("0")
                currentPlayer=1
                Tablero[row][col]="0"
                TXVPlayer.setText(getString(R.string.playerX))
            }
            else
            {
                button.setText("X")
                currentPlayer=0
                Tablero[row][col]="X"
                TXVPlayer.setText(getString(R.string.playerO))
            }
            verifyVictory()
            verifyNoWinner()
        }
    }

    private fun verifyVictory() {
        if(verifyCols()||verifyRows()||verifyDiags()){

            Log.d("Click","${Tablero[0][0]} - ${Tablero[0][1]} - ${Tablero[0][2]} - " +
                                        "${Tablero[1][0]} - ${Tablero[1][1]} - ${Tablero[1][2]} - " +
                                        "${Tablero[2][0]} - ${Tablero[2][1]} - ${Tablero[2][2]} - ")
            if(currentPlayer==0)
            {
                TXVPlayer.setText(getString(R.string.playerXwin))
            }
            else
            {
                TXVPlayer.setText(getString(R.string.playerOwin))
            }
            disableGame()
        }
    }

    private fun verifyNoWinner() {
        var ban: Boolean=false
        for (i in 0..<rows)
        {
            for (j in 0..<cols)
            {
                if(Tablero[i][j]=="")
                {
                    ban=true
                    break
                }
            }
        }
        if(!ban)
        {
            TXVPlayer.setText(getString(R.string.noWinner))
            disableGame()
        }
    }


    private fun verifyDiags(): Boolean {

        if((Tablero[0][0]==Tablero[1][1]&&Tablero[0][0]==Tablero[2][2]&&Tablero[0][0]!="")||
            (Tablero[0][2]==Tablero[1][1]&&Tablero[0][2]==Tablero[2][0]&&Tablero[0][2]!=""))
        {
            return true
        }
        return false
    }

    private fun verifyRows(): Boolean {
        for (i in 0..<rows)
        {
            if(Tablero[i][0]==Tablero[i][1]&&Tablero[i][0]==Tablero[i][2]&&Tablero[i][0]!="")
            {
                return true
            }
        }
        return false
    }

    private fun verifyCols(): Boolean {
        for (i in 0..<cols)
        {
            if(Tablero[0][i]==Tablero[1][i]&&Tablero[0][i]==Tablero[2][i]&&Tablero[0][i]!="")
            {
                return true
            }
        }
        return false
    }



    private fun enableGame() {
        Tablero= Array(rows){ Array(cols){""} }
        for (i in BTNTablero.indices) {
            BTNTablero[i].isEnabled = true;
            BTNTablero[i].setText("")
            BTNRestart.visibility=View.INVISIBLE
        }
        for (i in 0..<rows)
        {
            for (j in 0..<cols)
            {
                Tablero[i][j]=""
            }
        }
        currentPlayer=0;
        TXVPlayer.setText(R.string.playerO)
    }
    private fun disableGame() {
        for (i in BTNTablero.indices) {
            BTNTablero[i].isEnabled = false;
            BTNRestart.visibility=View.VISIBLE
        }
    }
}