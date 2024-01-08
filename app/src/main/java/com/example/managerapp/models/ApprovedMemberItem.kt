package com.example.managerapp.models

data class ApprovedMemberItem(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: Long,
    val regDate: String,
    val regNo: Int,
    val status: String
)