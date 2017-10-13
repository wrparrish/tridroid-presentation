package data.event

import data.DataSource
import domain_model.discover.Event

/**
 * Created by billyparrish on 2/27/17 for Shortlist.
 */
interface EventDataSource : DataSource<String, Event>