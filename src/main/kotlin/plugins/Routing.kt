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

package me.devgabi.arcano.plugins

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import me.devgabi.arcano.cart.RestCartService
import me.devgabi.arcano.cart.routes.cartHistoryRoute
import me.devgabi.arcano.cart.routes.createCartRoute
import me.devgabi.arcano.product.RestProductService
import me.devgabi.arcano.user.RestUserService

fun Application.configureRouting() {
  val client = HttpClient(CIO) {
    install(ContentNegotiation) {
      json(Json { ignoreUnknownKeys = true })
    }
  }

  val userService = RestUserService(client)
  val productService = RestProductService(client)
  val cartService = RestCartService(client, userService, productService)

  routing {
    createCartRoute(cartService)
    cartHistoryRoute(cartService)
  }
}
