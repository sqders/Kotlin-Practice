package com.example.yandexinterview

//Сортирует список в списки по набору букв
fun main(){
    println(groupingByCommonChar(arrayOf("eat","ate","eta","tea","dog","god","odg")))
}

fun groupingByCommonChar(wordArray:Array<String>):List<List<String>>{
    val result: MutableList<List<String>> = mutableListOf()
    val map = mutableMapOf<String, MutableList<String>>()

    for(word in wordArray){
        val sortedWord = word.toCharArray().sorted().joinToString("")

        if(map.containsKey(sortedWord))
            map[sortedWord]?.add(word)
        else
            map[sortedWord]= mutableListOf(word)
    }
    for((key,value) in map){
        result.add(value)
    }
    return result
}