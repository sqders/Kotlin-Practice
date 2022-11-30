package com.example.yandexinterview
/*
* Author Andrei Popov
 */
fun main(){
    val array1 = listOf(1,2,2,3,4,1)
    var array2 = listOf(2,1,0,4,0,3,0,1)
    var result = mutableListOf<Int>()
    for ( element in array1){
        var j=0
        while(j<array2.size ){
            if(element==array2[j]){
                result.add(element)
                array2 =array2.filterIndexed{index, i -> index!=j }
                break
            }
            j++
        }
    }
    println(result)
}