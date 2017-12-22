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

package uk.gov.hmrc.ocelotexternal.controllers

import javax.inject.Singleton

import play.api.mvc.Action
import uk.gov.hmrc.mongo.Awaiting
import uk.gov.hmrc.ocelotexternal.ProcessData
import uk.gov.hmrc.ocelotexternal.repositories.{OcelotRepository, ProcessData}
import uk.gov.hmrc.play.bootstrap.controller.BaseController

import scala.concurrent.Future

@Singleton()
class OcelotExternal() extends BaseController with Awaiting {

  val db = new OcelotRepository()

  def fetch(id: String) = Action.async { implicit request =>

    val result: List[ProcessData] = await(db.findAll())

    Future.successful(Ok(result.head.data))
  }
}
