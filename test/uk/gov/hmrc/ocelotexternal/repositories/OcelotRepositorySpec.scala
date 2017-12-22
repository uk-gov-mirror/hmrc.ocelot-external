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
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalatest.BeforeAndAfterEach
import play.api.libs.json.{JsObject, Json}
import reactivemongo.api.DB
import uk.gov.hmrc.mongo.MongoSpecSupport
import uk.gov.hmrc.play.test.UnitSpec

class OcelotRepositorySpec extends UnitSpec with MongoSpecSupport with BeforeAndAfterEach {

  "OcelotRepositry" should {

    "add a process" in {
      val result = await(db.insertProcess(basicProcess))
      assert(result.ok)
    }

  }


  def db(implicit mongo: () => DB) = new OcelotMongoRepository()

  var basicProcess = ProcessData("oct90001", Json.parse("""{}""").as[JsObject])


  override def beforeEach() {
    await(db.removeAll())
  }
}
