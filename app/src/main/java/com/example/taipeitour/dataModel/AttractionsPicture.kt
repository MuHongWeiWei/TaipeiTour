package com.example.taipeitour.dataModel

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
data class AttractionsRelease(
    val XML_Head: Head?
)

data class Head(
    val Infos: Infos?,
)

data class Infos(
    val Info: List<AttractionsData>
)

data class AttractionsData(
    val Name: String?,
    val Picture1: String?,
)