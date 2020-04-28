package ru.vladroid.projs.mems.utils.mappers

import ru.vladroid.projs.mems.domain.Mem
import ru.vladroid.projs.mems.network.response.MemResponse

class MemMapper: Mapper<MemResponse, Mem> {
    override fun map(input: MemResponse): Mem {
       return Mem(
           input.id,
           input.title,
           input.description ?: "",
           input.isFavorite,
           input.createdDate,
           input.photoUrl
       )
    }
}


class ListMemMapper(private val mapper: Mapper<MemResponse, Mem>): ListMapper<MemResponse, Mem> {
    override fun map(input: List<MemResponse>): List<Mem> {
        return input.map { mapper.map(it) }
    }
}