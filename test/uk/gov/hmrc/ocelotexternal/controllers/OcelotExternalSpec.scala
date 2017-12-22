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

import play.api.http.Status
import play.api.test.FakeRequest
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}

class OcelotExternalSpec extends UnitSpec with WithFakeApplication {

  val fakeRequest = FakeRequest("GET", "/")

  "GET /oct90001" should {
    "get the right process" in {
      val controller = new OcelotExternal()
      val result = controller.fetch("oct90001")(fakeRequest)
      status(result) shouldBe Status.OK
    }

    "get the wrong process" in {
      val controller = new OcelotExternal()
      val result = controller.fetch("bogus")(fakeRequest)
      status(result) shouldBe Status.NOT_FOUND
    }


  }
}
