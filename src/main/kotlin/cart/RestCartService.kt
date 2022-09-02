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

package me.devgabi.arcano.cart

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import me.devgabi.arcano.product.ProductService
import me.devgabi.arcano.user.CompleteUser
import me.devgabi.arcano.user.Purchase
import me.devgabi.arcano.user.UserId
import me.devgabi.arcano.user.UserService

class RestCartService(
  private val client: HttpClient,
  private val userService: UserService,
  private val productService: ProductService,
) : CartService {
  private val purchases = hashMapOf<UserId, CompleteUser>()

  override suspend fun createCart(userId: UserId, products: List<CartProduct>): Cart {
    val localDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)

    val user = purchases.getOrPut(userId) { createCompleteUser(userId) }
    populateUserPurchases(user, products)

    return client
      .post("https://fakestoreapi.com/carts") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        setBody(RestCreateCartRequest(userId, localDateTime.date, products))
      }
      .body()
  }

  override suspend fun getCartHistory(userId: Int): CompleteUser {
    return purchases.getOrPut(userId) { createCompleteUser(userId) }
  }

  private suspend fun populateUserPurchases(user: CompleteUser, products: List<CartProduct>) {
    products.forEach { (quantity, productId) ->
      val product = productService.findProduct(productId)
        ?: error("Product with id $productId not found")

      user.purchases += Purchase(product, quantity)
    }
  }

  private suspend fun createCompleteUser(userId: UserId): CompleteUser {
    val user = userService.findUser(userId)
      ?: error("User with id $userId not found")

    return CompleteUser(user.id, user.name.toString(), user.email, mutableListOf())
  }
}

@Serializable
data class RestCreateCartRequest(
  val userId: Int,
  val date: LocalDate,
  val products: List<CartProduct>,
)
