/*
 * Copyright 2011-2019 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package default

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class ProSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:9000") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
  
  val scn = scenario("User").exec(Search.search)

  //setUp(scn.inject(atOnceUsers(1000)).protocols(httpProtocol))

  setUp(scn.inject(rampUsers(1000) during(5 second)).protocols(httpProtocol))

  object Search {
    val user = csv("user.csv").random 
    val ticket = csv("ticket.csv").random
    val search = 
      feed(user)
      .feed(ticket)
    //   .exec(
    //   http("request_1")
    //     .get("/")
    //   )
    //   .pause(5) // Note that Gatling has recorded real time pauses
    //   .exec(
    //     http("request_2")
    //       .get("/userLogin")
    //   )
    //   .pause(5)
    //   .exec(
    //     http("request_3") 
    //       .post("/userPage")
    //       .formParam("name", "${userName}")
    //       .formParam("password", "123456")
    //   )
    //   .pause(5)
    //   .exec(
    //     http("request_4")
    //       .get("/userPurchase?name=${userName}")
    //   )
    // .pause(5)
    .exec(
      http("1000UserTest")
        .get("/purchaseResult?userName=${userName}&ticketInfo=${ticket}")
    )
  }


}
