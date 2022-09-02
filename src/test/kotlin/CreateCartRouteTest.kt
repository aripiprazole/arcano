package me.devgabi.arcano

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
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
import me.devgabi.arcano.cart.CartProduct
import me.devgabi.arcano.cart.CartService
import me.devgabi.arcano.cart.routes.CreateCartRequest
import me.devgabi.arcano.cart.routes.createCartRoute
import me.devgabi.arcano.plugins.configureSerialization

class CreateCartRouteTest {
  @Test
  fun testCreateCartRoute(): Unit = testApplication {
    val cartService = mockk<CartService> {
      coEvery { createCart(any(), any()) } returns mockk()
    }

    val client = createClient {
      install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
      }
    }

    application {
      configureSerialization()
      routing { createCartRoute(cartService) }
    }

    val cart = CreateCartRequest.ClientCart(
      userId = 1,
      products = listOf(CartProduct(productId = 1, quantity = 1)),
    )

    client
      .post("/cart") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        setBody(CreateCartRequest(listOf(cart)))
      }
      .apply {
        assertEquals(HttpStatusCode.NoContent, status)
        coVerify(exactly = 1) { cartService.createCart(1, cart.products) }
      }
  }
}
