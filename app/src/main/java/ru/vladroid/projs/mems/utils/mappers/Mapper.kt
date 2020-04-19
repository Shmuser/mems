package ru.vladroid.projs.mems.utils.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}
