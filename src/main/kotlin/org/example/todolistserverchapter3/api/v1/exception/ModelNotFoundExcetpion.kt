package org.example.todolistserverchapter3.api.v1.exception

class ModelNotFoundException(modelName: String, id: Long) : RuntimeException(
    "Model $modelName with given id $id not found"
)