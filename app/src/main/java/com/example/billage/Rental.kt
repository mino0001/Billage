package com.example.billage

data class Rental(
    val rt_id: String,  //return_id값(기본키)
    val d_id: String, //devcie_id(기기 id)
    val u_id: String, //user id(사용자 id)
    val rt_book: String, //예약일
    val rt_start: String, //대여 시작일(수령일)
    val rt_deadline: String, //반납 예정일
    val rt_return: String, //실제 반납일
    val rt_state: String //예약 상태 : 예약 완료(0),수령 완료(1),예약 취소(2),반납 완료(3),연체(4)
)
