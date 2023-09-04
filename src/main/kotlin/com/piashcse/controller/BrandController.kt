package com.piashcse.controller

import com.piashcse.entities.product.BrandEntity
import com.piashcse.entities.product.BrandTable
import com.piashcse.models.PagingData
import com.piashcse.models.bands.AddBrand
import com.piashcse.models.bands.DeleteBrand
import com.piashcse.models.bands.UpdateBrand
import com.piashcse.utils.extension.alreadyExistException
import com.piashcse.utils.extension.isNotExistException
import org.jetbrains.exposed.sql.transactions.transaction

class BrandController {
    fun createBrand(addBand: AddBrand) = transaction {
        val brandExist = BrandEntity.find { BrandTable.brandName eq addBand.brandName }.toList().singleOrNull()
        return@transaction if (brandExist == null) {
            BrandEntity.new {
                brandName = addBand.brandName
            }.brandResponse()
        } else {
            addBand.brandName.alreadyExistException()
        }
    }

    fun getBrand(paging: PagingData) = transaction {
        val brands = BrandEntity.all().limit(paging.limit, paging.offset)
        return@transaction brands.map {
            it.brandResponse()
        }
    }

    fun updateBrand(updateBrand: UpdateBrand) = transaction {
        val isBrandExist = BrandEntity.find { BrandTable.id eq updateBrand.brandId }.toList().singleOrNull()
        isBrandExist?.let {
            it.brandName = updateBrand.brandName
            // return category response
            it.brandResponse()
        } ?: updateBrand.brandId.isNotExistException()

    }

    fun deleteBrand(deleteBrand: DeleteBrand) = transaction {
        val isBrandExist = BrandEntity.find { BrandTable.id eq deleteBrand.brandId }.toList().singleOrNull()
        isBrandExist?.let {
            it.delete()
            deleteBrand.brandId
        } ?: deleteBrand.brandId.isNotExistException()
    }
}