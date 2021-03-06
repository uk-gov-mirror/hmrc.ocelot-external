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
import uk.gov.hmrc.ocelotexternal.repositories.{OcelotRepository, ProcessData}
import uk.gov.hmrc.play.bootstrap.controller.BaseController

import scala.concurrent.Future

@Singleton()
class OcelotExternal() extends BaseController with Awaiting {

  val db = OcelotRepository()

  def fetch(id: String) = Action.async { implicit request =>

    if (id.matches("^[a-z]{3}\\d{5}$")) {

      val result: List[ProcessData] = await(db.findAll())

      if (result.nonEmpty) {
        Future.successful(Ok(result.head.data))
      } else {

        Future.successful(NotFound("Process not found"))
      }
    } else {
      Future.successful(NotFound("Process not found"))
    }
  }


}