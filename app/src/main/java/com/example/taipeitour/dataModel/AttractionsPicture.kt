package com.example.taipeitour

/**
 * Author: FlyWei
 * E-mail: tony91097@gmail.com
 * Date: 2023/2/9
 */
data class AttractionsPicture(
    val XML_Head: Head?
)

data class Head(
    val Infos: Infos?,
)

data class Infos(
    val Info: List<Data>
)

data class Data(
    val Name: String?,
    val Picture1: String?,
)