package ru.lagarnikov.hapidrum.model

data class InstrumentAboutData(
    val name: String,
    val shopName: String,
    val urlShop: String,
    val aditinalParams: ArrayList<AditionalInstrumentInfo>,
    val withoutLink: Boolean = false,
    val fakeName: String = ""
)