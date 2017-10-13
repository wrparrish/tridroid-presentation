package use_case.event

import data.event.EventDiskDataSource
import data.event.EventMemoryDataSource
import domain_model.discover.Event
import rx.Observable
import use_case.Strategy
import use_case.UseCase
import use_case.event.SaveEvents.Request
import use_case.event.SaveEvents.Response
import javax.inject.Inject


class SaveEvents @Inject constructor(
        private val disk: EventDiskDataSource,
        private val memory: EventMemoryDataSource) : UseCase<Request, Response> {


    override fun execute(request: Request): Observable<Response> {
        val strategy = Strategy(request.flags)
        val observable = Observable.empty<Event>()

        if (strategy.useDisk) observable.mergeWith(disk.put(request.event.id, request.event))
        if (strategy.useMemory) observable.mergeWith(memory.put(request.event.id, request.event))

        return observable.map { Response() }
    }


    class Request(val event: Event, flags: Int = Strategy.WARM) : Strategy.Request(flags)

    class Response : UseCase.Response

}

