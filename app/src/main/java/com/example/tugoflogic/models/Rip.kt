package com.example.tugoflogic.models


public class Rip(
    var _id: Int,
    var game_id: Int,
    var user_id: Int,
    var statement: String,
    var status: List<ERipStatus>
) {


}