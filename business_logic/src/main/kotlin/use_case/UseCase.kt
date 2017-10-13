package use_case

import rx.Observable
import use_case.UseCase.Request
import  use_case.UseCase.Response

/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 */


interface UseCase<in I : Request, O : Response> {

    fun execute(request: I): Observable<O>

    interface Request

    interface Response

}