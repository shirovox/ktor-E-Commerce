package com.example.entities.product

import com.example.entities.base.BaseIntEntity
import com.example.entities.base.BaseIntEntityClass
import com.example.entities.base.BaseIntIdTable
import com.example.entities.product.defaultproductcategory.ProductCategoryTable
import org.jetbrains.exposed.dao.id.EntityID

object ProductVariantOptionTable : BaseIntIdTable("product_variant_option") {
    val productVariantId = ProductCategoryTable.reference("product_variant_id", ProductVariantTable.id)
    val name = text("name")
}

class ProductVariantOptionEntity(id: EntityID<String>) : BaseIntEntity(id, ProductVariantOptionTable) {
    companion object : BaseIntEntityClass<ProductVariantOptionEntity>(ProductVariantOptionTable)

    var productVariantId by ProductVariantOptionTable.productVariantId
    var name by ProductVariantTable.name
}