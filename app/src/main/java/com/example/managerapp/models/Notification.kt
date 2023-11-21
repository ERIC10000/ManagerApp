package com.example.managerapp.models

data class Notification(
    val date: String,
    val notification: String,
    val notification_ID: Int,
    val sender: String?,
    val title: String?
)