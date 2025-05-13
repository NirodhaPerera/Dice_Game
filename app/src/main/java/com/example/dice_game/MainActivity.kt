 package com.example.dice_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random




 // Video Demonstration Video Link::
 // https://drive.google.com/drive/folders/12dIEpktq67bGKMlHYSmOiEwdA7b4mvP0?usp=sharing

 class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            App()

        }
    }
}





@Composable
fun App(){
    var showGameScreen by remember { mutableStateOf(false) }
    var showSetTargetScreen by remember { mutableStateOf(false) }
    var targetScore by remember { mutableStateOf(101) }
    var humanWin by remember { mutableStateOf(0) }
    var computerWin by remember { mutableStateOf(0) }

    if (showGameScreen){
        NewGame(
            humanWin = humanWin,
            computerWin = computerWin,
            updateWinCount = { newHumanWin, newComputerWin->
                humanWin+=newHumanWin
                computerWin +=newComputerWin

            },

            onBack = {
                showGameScreen = false
                showSetTargetScreen = false



                     },
            targetScore = targetScore)
    }else if (showSetTargetScreen) {
        SetTargetScreen(
            onStartGame = { newTargetScore ->
                targetScore = newTargetScore
                showGameScreen = true
            },
            onBackHome = { showSetTargetScreen = false }
        )
    }

    else{
        HomeScreen(onNewGame = {showSetTargetScreen = true})
    }
}



 @Composable
 fun HomeScreen(onNewGame : ()-> Unit){

     var showDialog by remember { mutableStateOf(false) }

     Image(
         painter = painterResource(id = R.drawable.bg3),
         contentDescription = null,
         contentScale = ContentScale.FillBounds,
         modifier = Modifier.fillMaxSize()
     )

     Column(
         Modifier.fillMaxSize().padding(20.dp),
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center
     ) {

         Image(
             painter = painterResource(id = R.drawable.logodice),
             contentDescription = null,
             contentScale = ContentScale.FillHeight,
             modifier = Modifier.size(530.dp)

         )

         Button(onClick = onNewGame, colors =ButtonDefaults.buttonColors(
             Color.Green
         )) {
             Text(text = "New Game", fontSize = 35.sp, color = Color.Black )

             Image(
                 painter = painterResource(id = R.drawable.play2),
                 contentDescription = null,
                 contentScale = ContentScale.FillWidth,
                 modifier = Modifier.size(60.dp).padding(start = 10.dp)

             )


         }
         Spacer(Modifier.padding(20.dp))

         Button(onClick = {showDialog = true}, colors = ButtonDefaults.buttonColors(
             Color.Red
         )) {
             Text(text = "About", fontSize = 24.sp)
         }



         if (showDialog) {
             AlertDialog(
                 onDismissRequest = { showDialog = false },

                 title = {
                     Column  (
                         modifier = Modifier.padding(8.dp),
                         verticalArrangement = Arrangement.Center


                     ){
                         Text(text = "Author : w1953237",
                             fontSize = 22.sp,
                             fontWeight = FontWeight.Bold)
                         Text(text = "Nirodha Adithya Perera",
                             fontSize = 20.sp,
                             fontWeight = FontWeight.Bold
                             )
                     }


                 },
                 text = {

                     Column(
                         Modifier.padding(10.dp),
                         horizontalAlignment = Alignment.CenterHorizontally,
                         verticalArrangement = Arrangement.Center
                     ) {


                         Text("I confirm that I understand what plagiarism is and have read " +
                                 "and understood the section on Assessment Offences in the Essential Information for Students. " +
                                 "The work that I have submitted is entirely my own. Any work from other authors is duly referenced and acknowledged",

                             fontSize = 12.sp,
                             fontWeight = FontWeight.W800

                         )
                     }

                     },
                 confirmButton = {

                 },
                 dismissButton = {
                     Button(onClick = { showDialog = false }) {
                         Text("Cancel")
                     }
                 }

             )
         }
     }
 }




 @Composable
 fun SetTargetScreen(onStartGame: (Int) -> Unit, onBackHome: () -> Unit) {
     var targetScore by remember { mutableStateOf(101) }
     var showError by remember { mutableStateOf(false) }

     Image(
         painter = painterResource(id = R.drawable.canva_2),
         contentDescription = null,
         contentScale = ContentScale.FillBounds,
         modifier = Modifier.fillMaxSize()
     )

     Column(
         modifier = Modifier
             .fillMaxSize()
             .padding(20.dp),
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center
     ) {



         Text(
             text = "Set Target Score",
             fontSize = 30.sp,
             fontWeight = FontWeight.Bold,
             color = Color.White,
             modifier = Modifier.padding(bottom = 20.dp)
         )


         TextField(
             value = targetScore.toString(),
             onValueChange = { input ->
                 val newValue = input.toIntOrNull() ?: 101
                 targetScore = newValue

             },
             label = { Text("Target Score") },
             keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(bottom = 10.dp)

         )



         Button(
             onClick = { onStartGame(targetScore) },
             colors = ButtonDefaults.buttonColors(Color.Green),
             modifier = Modifier.padding(bottom = 10.dp)
         ) {
             Text(text = "Start Game", fontSize = 24.sp, color = Color.Black)
         }

         // Back button
         Button(
             onClick = onBackHome,
             colors = ButtonDefaults.buttonColors(Color.Red)
         ) {
             Text(text = "Back", fontSize = 24.sp)
         }
     }
 }


 @Composable
 fun NewGame(
     humanWin: Int,
     computerWin: Int,
     onBack: () -> Unit,
     targetScore: Int,
     updateWinCount: (Int, Int)->Unit
 ) {

     var humanDice by remember { mutableStateOf(List(5) { 1 }) }
     var computerDice by remember { mutableStateOf(List(5) { 1 }) }
     var throwCount by remember { mutableStateOf(0) }
     var humanScore by remember { mutableStateOf(0) }
     var computerScore by remember { mutableStateOf(0) }
     var reRollCount by remember { mutableStateOf(2) }
     var isReRollEnable by remember { mutableStateOf(false) }
     var selectedDice by remember { mutableStateOf(List(5){false}) }
     var isThrowEnable by remember { mutableStateOf(true) }
     var computerReRollCount by remember { mutableStateOf(2) }
     var showGameOverDialog by remember { mutableStateOf(false) }
     var isHumanWin by remember { mutableStateOf(false) }
     var isScoreEnable by remember { mutableStateOf(true) }
     var showTieBreakingDialog by remember { mutableStateOf(false) }
     var humanWinCount by remember { mutableStateOf(0) }
     var computerWinCount by remember { mutableStateOf(0) }


     val gradientBrush = Brush.linearGradient(
         colors = listOf(Color.LightGray, Color.DarkGray, Color.White),
         start = androidx.compose.ui.geometry.Offset(0f, 0f),
         end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
     )



     Image(
         painter = painterResource(id = R.drawable.canva_2),
         contentDescription = null,
         contentScale = ContentScale.FillBounds,
         modifier = Modifier.fillMaxSize()
     )


     fun checkGameOver(){


         if (humanScore>=targetScore && computerScore>= 101){
             showTieBreakingDialog = true
             isThrowEnable = false
             isScoreEnable =false
             isReRollEnable = false

         }else if (humanScore>=101){
            isHumanWin = true
            showGameOverDialog = true
             isScoreEnable = false
             isThrowEnable = false
             isReRollEnable = false
             humanWinCount++


         }else if (computerScore >= 101){
             isHumanWin = false
             showGameOverDialog = true
             isScoreEnable = false
             isThrowEnable = false
             isReRollEnable = false
             computerWinCount++

         }
     }


     fun calculateScore(dice: List<Int>): Int{
         return dice.sum()
     }

     fun updateScore(){

         humanScore += calculateScore(humanDice)
         computerScore += calculateScore(computerDice)
         checkGameOver()
         isThrowEnable = true
         isReRollEnable =false
     }

     fun reRollDice(){
         if (reRollCount>0){
             humanDice= humanDice.mapIndexed{index, value ->
                 if (selectedDice[index]) value else (1..6).random()
             }
             computerDice = List(5){(1..6).random()}
            reRollCount--
         }else{
             updateScore()
             reRollCount =0
             isReRollEnable = false
         }
     }

     fun throwDice(){
            isThrowEnable = false
            isReRollEnable = true

             if (throwCount<10){
                 humanDice= humanDice.mapIndexed{index, value ->
                     if (selectedDice[index]) value else (1..6).random()
                 }
                 computerDice = List(5){(1..6).random()}

             }
     }

     fun computerTurn(currentHumanScore: Int, currentComputerScore: Int) {
         val scoreDifference = currentHumanScore - currentComputerScore
         val isBehind = scoreDifference > 0
         val remainingReRolls = computerReRollCount


         val reRollThreshold = when {
             isBehind && remainingReRolls > 1 -> 4
             isBehind -> 3
             else -> 2
         }

         val shouldReRoll = when {
             remainingReRolls == 0 -> false
             isBehind -> Random.nextFloat() < 0.5f
             else -> Random.nextFloat() < 0.3f
         }

         if (shouldReRoll) {
             val keepSelection = computerDice.map { dieValue ->
                 when {
                     dieValue >= 5 -> true
                     dieValue <= 2 -> false
                     else -> dieValue >= reRollThreshold
                 }
             }


             computerDice = computerDice.mapIndexed { index, value ->
                 if (keepSelection[index]) value else (1..6).random()
             }
             computerReRollCount--
         }
         updateScore()
     }






     if (showGameOverDialog){
         AlertDialog(
             containerColor = Color.White,
             modifier = Modifier.border(
                 width = 3.dp,
                 color = if (isHumanWin)Color.Green else Color.Red,
                 shape = RoundedCornerShape(20.dp)
             ),

             onDismissRequest = { showGameOverDialog = false },

             title = {

                     Box(
                         modifier = Modifier.fillMaxWidth(),
                         contentAlignment = Alignment.Center
                     ){
                         Text(
                             text = if (isHumanWin)"You Win!" else "You Lose",
                             color = if(isHumanWin)Color.Green else Color.Red,
                             fontSize = 25.sp,
                             fontWeight = FontWeight.Bold)
                     }

             },
             text = {

                 Column(
                     Modifier.padding(10.dp),
                     horizontalAlignment = Alignment.CenterHorizontally,
                     verticalArrangement = Arrangement.Center
                 ) {


                     Text(
                         text = "Game Over! Press the Back button to return to the Home Screen.",

                         fontSize = 18.sp,
                         fontWeight = FontWeight.W800

                     )
                 }

             },

              confirmButton = {
                 BackHandler {
                     onBack()
                     updateWinCount(humanWinCount,computerWinCount)
                                 }
             }

         )
     }


     fun suddenDeathRound() {
         humanDice= humanDice.mapIndexed{index, value ->
             if (selectedDice[index]) value else (1..6).random()
         }
         computerDice = List(5){(1..6).random()}

         isHumanWin = humanScore > computerScore

         if (isHumanWin){
             humanWinCount++
             showGameOverDialog = true
         }else{
             computerWinCount++
             showGameOverDialog = true
         }

     }

     if (showTieBreakingDialog){
         AlertDialog(
             onDismissRequest = {showTieBreakingDialog = false},
             modifier = Modifier.border(
                 width = 3.dp,
                 color = Color.Black,
                 shape = RoundedCornerShape(20.dp)
             ),
             title = {

                 Box(
                     modifier = Modifier.fillMaxWidth(),
                     contentAlignment = Alignment.Center
                 ){
                     Text(
                         text = "It's Tie",
                         color = Color.Black,
                         fontSize = 25.sp,
                         fontWeight = FontWeight.Bold)
                 }

             },
             text = {
                 Column(
                     Modifier.padding(10.dp),
                     horizontalAlignment = Alignment.CenterHorizontally,
                     verticalArrangement = Arrangement.Center
                 ) {


                     Text(
                         text = "Both players are Tie..! Click Throw to get Chance to Win.",

                         fontSize = 18.sp,
                         fontWeight = FontWeight.W800

                     )
                 }
             },
             confirmButton = {
                 Button(onClick = {showTieBreakingDialog=false
                 suddenDeathRound()}) {
                     Text("Throw")
                 }
             }
         )
     }






     Row(
         Modifier.fillMaxSize(),
         horizontalArrangement = Arrangement.SpaceBetween,


     ) {


         Button(onClick = onBack, colors =ButtonDefaults.buttonColors(
             Color.Transparent
         ), modifier = Modifier.padding(start = 10.dp, top = 40.dp)) {
             Image(
                 painter = painterResource(id = R.drawable.home2),
                 contentDescription = null,
                 contentScale = ContentScale.FillBounds,
                 modifier = Modifier.size(50.dp)
             )


         }





         Column (
            modifier = Modifier.padding(40.dp)
        ){

             Row (
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier.padding(start = 10.dp)
             ){
                 Text(text = "H : $humanWin / C : $computerWin", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White)
             }

            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier.padding(20.dp)
            ){
                Image(
                    painter = painterResource(id = R.drawable.human),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.padding(10.dp))

                Text(text = "$humanScore", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(20.dp)

            ){
                Image(
                    painter = painterResource(id = R.drawable.computer),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.padding(10.dp))

                Text(text = "$computerScore", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }



        }



     }




     Column (

         Modifier.fillMaxSize().padding(20.dp).border(
             width = 5.dp,
             brush = gradientBrush,

             shape = RoundedCornerShape(22.dp)

         ),
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center


     ){

         Spacer(Modifier.padding(40.dp))


         Text(text = "YOU", fontSize = 20.sp, fontWeight = FontWeight.Bold,modifier = Modifier.padding(10.dp), color = Color.White)

         Row (
             verticalAlignment = Alignment.CenterVertically,

         ){

             humanDice.forEachIndexed(){index,value ->

                 Box(
                     contentAlignment = Alignment.Center,
                     modifier = Modifier.size(50.dp).
                     background(Color.White, shape = RoundedCornerShape(20.dp)).
                     border(
                         width = if (selectedDice[index])3.dp else 0.dp,
                         color =if (selectedDice[index])Color.Green else Color.Black,
                         shape = RoundedCornerShape(15.dp)
                     )
                         .clickable {
                             selectedDice = selectedDice.toMutableList().also {
                                 it[index] = !it[index]
                             }
                         }
                 ){
                     Image(
                         painter = painterResource(id = getDiceImageResource(value)),
                         contentDescription = null,

                         modifier = Modifier.size(50.dp),


                     )
                 }

                 Spacer(Modifier.padding(5.dp))
             }
         }

         Spacer(Modifier.padding(20.dp))

         Text(text = "COMPUTER", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp), color = Color.White)

         Row {



             computerDice.forEach{value->

                 Box(
                     contentAlignment = Alignment.Center,
                     modifier = Modifier.size(50.dp).
                     background(Color.White, shape = RoundedCornerShape(22.dp)).
                     border(
                         width = 2.dp,
                         color = Color.Black,
                         shape = RoundedCornerShape(14.dp)
                     )
                 ){
                     Image(
                         painter = painterResource(id = getDiceImageResource(value)),
                         contentDescription = null,
                         modifier = Modifier.size(55.dp)
                     )
                 }


                 Spacer(Modifier.padding(5.dp))
             }
         }
         Spacer(Modifier.padding(20.dp))

         Row (
             horizontalArrangement = Arrangement.SpaceBetween,
             modifier = Modifier.padding(20.dp)
         ) {
             Button(onClick = { throwDice() }, enabled = isThrowEnable, colors = ButtonDefaults.buttonColors(Color.Green)) {
                 Text(text = "Throw", fontSize = 24.sp)
             }


             Spacer(Modifier.padding(10.dp))

             Button(onClick = {
                 computerTurn(currentHumanScore = humanScore, currentComputerScore = computerScore)
                 updateScore()
             }, enabled = isScoreEnable,
                 colors = ButtonDefaults.buttonColors(Color.Magenta)) {
                 Text(text = "Score", fontSize = 24.sp)
             }

         }
         Spacer(Modifier.padding(10.dp))

         Button(onClick = {reRollDice()}, enabled = isReRollEnable, colors = ButtonDefaults.buttonColors(Color.White),
             modifier = Modifier.border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(35.dp))

         ) {
             Image(
                 painter = painterResource(id = R.drawable.refresh),
                 contentDescription = null,
                 contentScale = ContentScale.FillBounds,
                 modifier = Modifier.size(40.dp).padding(end = 10.dp)
             )

             Text(text = "$reRollCount", fontSize = 24.sp, color = Color.Red)
         }

         Spacer(Modifier.padding(20.dp))





     }







 }



 fun getDiceImageResource(value: Int): Int {
     return when (value) {
         1 -> R.drawable.dice_1
         2 -> R.drawable.dice_2
         3 -> R.drawable.dice_3
         4 -> R.drawable.dice_4
         5 -> R.drawable.dice_5
         6 -> R.drawable.dice_6
         else -> R.drawable.dice_1
     }
 }




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}