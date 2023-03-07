package com.example.kitsuanimeapp.data.model.mappers

interface Mapper<in DTO, out Entity> {
    operator fun invoke(dto: DTO): Entity
}
