package data

import rx.Observable

interface DataSource<in K, V> {

    fun getAll(): Observable<List<V>>

    fun get(key: K): Observable<V>

    fun put(key: K, value: V): Observable<V>

    fun remove(key: K): Observable<V>

}