package com.example.kitsuanimeapp.data.model.mappers

import com.example.kitsuanimeapp.data.model.dto.categoryResponseDTO.CategoryDataDTO
import com.example.kitsuanimeapp.data.model.local.entities.Attributes
import com.example.kitsuanimeapp.data.model.local.entities.CategoryData

class CategoryDataMapper : Mapper<CategoryDataDTO, CategoryData> {
    override fun invoke(dto: CategoryDataDTO): CategoryData {
        return with(dto) {
            val attributes = attributes?.let {
                Attributes(
                    it.childCount,
                    it.createdAt,
                    it.description,
                    it.nsfw,
                    it.slug,
                    it.title,
                    it.totalMediaCount,
                    it.updatedAt,
                )
            }
            CategoryData(
                attributes = attributes,
                selfLink = links?.self,
                animeLink = relationships?.anime?.links?.self,
                type = type,
            )
        }
    }
}
