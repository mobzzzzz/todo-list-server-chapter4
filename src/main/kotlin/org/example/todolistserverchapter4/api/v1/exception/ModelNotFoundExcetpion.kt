package org.example.todolistserverchapter4.api.v1.exception

class ModelNotFoundException(modelName: String, id: Long) : RuntimeException(
    "Model $modelName with given id $id not found"
)