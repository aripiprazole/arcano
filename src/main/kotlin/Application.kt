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

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.System.getenv
import me.devgabi.arcano.plugins.configureMonitoring
import me.devgabi.arcano.plugins.configureRouting
import me.devgabi.arcano.plugins.configureSerialization

fun main() {
  val port = getenv("PORT")?.toInt() ?: 8080
  val host = getenv("HOST") ?: "0.0.0.0"

  embeddedServer(Netty, port = port, host = host) {
    configureMonitoring()
    configureSerialization()
    configureRouting()
  }.start(wait = true)
}
