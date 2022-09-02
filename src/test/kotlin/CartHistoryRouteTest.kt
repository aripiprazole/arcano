/*
 *   Arcano project
 *   Copyright (C) 2022  Gabrielle Guimarães
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.devgabi.arcano

import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.serialization.json.Json
import me.devgabi.arcano.cart.CartService
import me.devgabi.arcano.cart.routes.cartHistoryRoute
import me.devgabi.arcano.plugins.configureSerialization
import me.devgabi.arcano.product.Product
import me.devgabi.arcano.user.CompleteUser
import me.devgabi.arcano.user.Purchase

class CartHistoryRouteTest {
  @Test
  fun `test create cart route`(): Unit = testApplication {
    val completeUser = CompleteUser(
      id = 1,
      name = "Gabrielle",
      email = "hello@devgabi.me",
      purchases = mutableListOf(
        Purchase(
          quantity = 1,
          product = Product(
            id = 1,
            title = "Test",
            price = 10.0,
            description = "Test",
            image = "Test",
            category = "Test",
          ),
        ),
      ),
    )

    val cartService = mockk<CartService> {
      coEvery { getCartHistory(1) } returns completeUser
    }

    val client = createClient {
      install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
      }
    }

    application {
      configureSerialization()
      routing { cartHistoryRoute(cartService) }
    }

    client.get("/cart-history/1").apply {
      assertEquals(HttpStatusCode.OK, status)
      assertEquals(completeUser, body())
      coVerify(exactly = 1) { cartService.getCartHistory(1) }
    }
  }
}
