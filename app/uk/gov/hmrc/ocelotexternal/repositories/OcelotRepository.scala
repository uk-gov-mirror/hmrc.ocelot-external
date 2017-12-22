/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ocelotexternal.repositories

import play.api.libs.json.{Format, JsObject, Json}
import play.modules.reactivemongo.MongoDbConnection
import reactivemongo.api.DB
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.indexes.{Index, IndexType}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import uk.gov.hmrc.mongo.json.ReactiveMongoFormats
import uk.gov.hmrc.mongo.{ReactiveRepository, Repository}

import scala.concurrent.Future

trait OcelotRepository extends Repository[ProcessData, BSONObjectID] {

  def insertProcess(process: ProcessData): Future[WriteResult]

  def fetchProcess(processId: String): Future[ProcessData]
}

object OcelotRepository extends MongoDbConnection {
  private lazy val ocelotRepository = new OcelotMongoRepository

  def apply(): OcelotRepository = ocelotRepository
}

class OcelotMongoRepository(implicit mongo: () => DB)
  extends ReactiveRepository[ProcessData, BSONObjectID]("ocelot-external", mongo, ProcessData.formats, ReactiveMongoFormats.objectIdFormats)
    with OcelotRepository {

  override def indexes: Seq[Index] = Seq(Index(Seq("processId" -> IndexType.Ascending), name = Some("procIdIndex"), unique = true, sparse = true))

  override def insertProcess(process: ProcessData): Future[WriteResult] = {
    collection.insert[ProcessData](process)
  }

  override def fetchProcess(processId: String): Future[ProcessData] = {
    val query = BSONDocument(
      "processId" -> processId
    )

    collection.find(query).one[ProcessData]
  }
}


case class ProcessData(processId: String, data: JsObject, id: BSONObjectID = BSONObjectID.generate)

object ProcessData {

  import uk.gov.hmrc.mongo.json.ReactiveMongoFormats.mongoEntity

  implicit val formats: Format[ProcessData] = mongoEntity {
    Json.format[ProcessData]
  }
}

