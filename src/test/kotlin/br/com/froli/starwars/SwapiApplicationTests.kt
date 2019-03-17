package br.com.froli.starwars

import br.com.froli.starwars.SwapiApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SwapiApplication::class])
class SwapiApplicationTests {

	@Test
	fun contextLoads() {
	}

}
