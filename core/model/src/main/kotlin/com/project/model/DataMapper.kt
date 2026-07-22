package com.project.model

import com.project.model.entity.ExampleEntity
import com.project.model.model.ExampleModel
import com.project.model.request.ExampleRequest
import com.project.model.response.ExampleResponse

fun ExampleModel.toEntity() = ExampleEntity(id = this.id)

fun ExampleModel.toRequest() = ExampleRequest(id = this.id)

fun ExampleEntity.toModel() = ExampleModel(id = this.id)

fun ExampleResponse.toModel() = ExampleModel(id = this.id)
