/*
 * Copyright 2020-2021 Borislav Borisov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package akka.persistence.postgresql.query.javadsl

import akka.NotUsed
import akka.persistence.postgresql.query.scaladsl.{PostgreSqlReadJournal => ScalaPostgresqlReadJournal}
import akka.persistence.query.javadsl._
import akka.persistence.query.{EventEnvelope, Offset}
import akka.stream.javadsl.Source

object PostgreSqlReadJournal {

  /**
   * The default identifier for [[PostgreSqlReadJournal]] to be used with
   * `akka.persistence.query.PersistenceQuery#getReadJournalFor`.
   *
   * The value is `"postgresql-read-journal"` and corresponds
   * to the absolute path to the read journal configuration entry.
   */
  final val Identifier = ScalaPostgresqlReadJournal.Identifier

}
/**
 * Java API: `akka.persistence.query.javadsl.ReadJournal` implementation for PostgreSQL
 * with R2DBC a driver.
 *
 * It is retrieved with:
 * {{{
 * PostgresqlReadJournal queries =
 *   PersistenceQuery.get(system).getReadJournalFor(PostgresqlReadJournal.class, PostgresqlReadJournal.Identifier());
 * }}}
 *
 * Corresponding Scala API is in [[akka.persistence.postgresql.query.scaladsl.PostgreSqlReadJournal]].
 *
 * Configuration settings can be defined in the configuration section with the
 * absolute path corresponding to the identifier, which is `"postgresql-read-journal"`
 * for the default [[PostgreSqlReadJournal#Identifier]]. See `reference.conf`.
 *
 */
final class PostgreSqlReadJournal(readJournal: ScalaPostgresqlReadJournal)
    extends ReadJournal
        with CurrentPersistenceIdsQuery
        with PersistenceIdsQuery
        with CurrentEventsByPersistenceIdQuery
        with EventsByPersistenceIdQuery
        with CurrentEventsByTagQuery
        with EventsByTagQuery {

  override def currentPersistenceIds(): Source[String, NotUsed] =
    readJournal.currentPersistenceIds().asJava

  override def persistenceIds(): Source[String, NotUsed] =
    readJournal.persistenceIds().asJava

  override def currentEventsByPersistenceId(
      persistenceId: String,
      fromSequenceNr: Long,
      toSequenceNr: Long
  ): Source[EventEnvelope, NotUsed] =
    readJournal.currentEventsByPersistenceId(persistenceId, fromSequenceNr, toSequenceNr).asJava

  override def eventsByPersistenceId(
      persistenceId: String,
      fromSequenceNr: Long,
      toSequenceNr: Long
  ): Source[EventEnvelope, NotUsed] =
    readJournal.eventsByPersistenceId(persistenceId, fromSequenceNr, toSequenceNr).asJava

  override def currentEventsByTag(tag: String, offset: Offset): Source[EventEnvelope, NotUsed] =
    readJournal.currentEventsByTag(tag, offset).asJava

  override def eventsByTag(tag: String, offset: Offset): Source[EventEnvelope, NotUsed] =
    readJournal.eventsByTag(tag, offset).asJava

}
