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

package me.devgabi.arcano.cart.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import me.devgabi.arcano.cart.CartProduct
import me.devgabi.arcano.cart.CartService

fun Route.createCartRoute(cartService: CartService) {
  post("/cart") {
    val body = call.receive<CreateCartRequest>()

    body.carts.forEach { (userId, products) ->
      cartService.createCart(userId, products)
    }

    call.respond(HttpStatusCode.NoContent)
  }
}

@Serializable
data class CreateCartRequest(val carts: List<ClientCart>) {
  @Serializable
  data class ClientCart(val userId: Int, val products: List<CartProduct>)
}
