package com.micro.server.dao.core

import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.event.AfterSaveCallback

abstract class AbstractPersistable<K, T> : Persistable<K>, AfterSaveCallback<T> {

    @org.springframework.data.annotation.Transient
    @Transient
    private var isNew = true

    override fun isNew(): Boolean {
        return isNew
    }

    fun forInsert(): T {
        isNew = true
        return this as T
    }

    fun forUpdate(): T {
        isNew = false
        return this as T
    }

    override fun onAfterSave(aggregate: T): T {
        if (isNew) isNew = false
        return aggregate
    }
}
