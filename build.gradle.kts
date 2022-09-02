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

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val datetimeVersion: String by project
val mockkVersion: String by project

plugins {
  application
  kotlin("jvm") version "1.7.10"
  kotlin("plugin.serialization") version "1.7.10"
  id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
  id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

group = "me.devgabi"
version = "0.0.1"

application {
  mainClass.set("me.devgabi.arcano.ApplicationKt")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
  implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
  implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
  implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
  testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
  testImplementation("io.mockk:mockk:$mockkVersion")
}
