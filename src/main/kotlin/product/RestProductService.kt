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

package me.devgabi.arcano.product

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RestProductService(private val client: HttpClient) : ProductService {
  override suspend fun findProduct(productId: ProductId): Product? {
    return client.get("https://fakestoreapi.com/products/$productId").body()
  }
}
