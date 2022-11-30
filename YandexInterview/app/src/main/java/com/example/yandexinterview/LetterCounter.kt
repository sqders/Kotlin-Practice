package com.example.yandexinterview

fun main() {
    println(counterLetter("AAAAAbbbbbKKKKoooooLLLzxc"))
}
fun counterLetter(lettersString: String) : String{
    var currentLetter = lettersString[0]
    var resultString: String=""
    var counter=0
    for(char in lettersString){
        if(char == currentLetter){
            counter++
        }else{
            if(counter==1)
                resultString+=currentLetter
            else
                resultString+=currentLetter.toString()+counter
            currentLetter=char
            counter=1
        }
    }

    if(counter==1)
        resultString+=currentLetter
    else
        resultString+=currentLetter.toString()+counter
    return resultString
}
