package io.jffiorillo.venezuelanews.base

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface ExecutionSchedulers {
    fun io(): Scheduler = Schedulers.io()
    fun computation(): Scheduler = Schedulers.computation()
    fun ui(): Scheduler = AndroidSchedulers.mainThread()
}