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

package me.devgabi.arcano.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
  val id: UserId,
  val email: String,
  val username: String,
  val password: String,
  val name: Name,
  val phone: String,
  val address: Address,
)

typealias UserId = Int

@Serializable
data class Address(
  val geolocation: GeoLocation,
  val city: String,
  val street: String,
  val number: Int,
  val zipcode: String,
)

@Serializable
data class GeoLocation(val lat: String, val long: String)

@Serializable
data class Name(
  @SerialName("firstname") val firstName: String,
  @SerialName("lastname") val lastName: String,
) {
  override fun toString(): String = "$firstName $lastName"
}
