package ru.lagarnikov.hapidrum.model


data class InstrumentSound(
    val instrumentName: String,
    val soundName: String,
    val fileLocalName: String,
    val fileExtension: String
) {
}