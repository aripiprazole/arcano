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

class RestCartService(private val client: HttpClient) : CartService {
  override suspend fun createCart(userId: Int, products: List<Product>): Cart {
    val localDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)

    return client
      .post("https://fakestoreapi.com/carts") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        setBody(RestCreateCartRequest(userId, localDateTime.date, products))
      }
      .body()
  }
}

@Serializable
data class RestCreateCartRequest(val userId: Int, val date: LocalDate, val products: List<Product>)
