package com.github.dungsil.armeria101

import com.linecorp.armeria.server.Server
import org.slf4j.LoggerFactory
import java.lang.ClassLoader.getSystemClassLoader
import java.lang.System.currentTimeMillis
import com.linecorp.armeria.server.file.FileService.of as fileService

fun main() {
  val startMillis = currentTimeMillis()
  val log = LoggerFactory.getLogger("com.github.dungsil.armeria101.Main")

  val server = createServer().apply {
    closeOnJvmShutdown()
    start().join()
  }

  log.debug("Server warmup: ${currentTimeMillis() - startMillis}ms")
  log.info("Server is running at http://localhost:${server.activeLocalPort()}")
}

/**
 * Create a new [Server] instance
 */
private fun createServer(port: Int = 8080): Server {
  return Server.builder()
    .http(port)
    // static resource serving
    .serviceUnder(
      "/",
      fileService(getSystemClassLoader(), "com/github/dungsil/armeria101/public")
    )
    .build()
}

