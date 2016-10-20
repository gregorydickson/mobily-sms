package ws.mobily
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.mockito.MockitoAnnotations.initMocks


class MobilyTest {

	private Mobily mobily

	

	@Before void beforeTest() {
		initMocks(this)
		mobily = new Mobily()

	}

	@Test void shouldDoSomethingGroovy() {
		
		
		def user=  "Javatest"
		def pass = "an2080"
		def message = "السلام عليكم Test"
		def senderName = "Greg"
		def numbers = "014052799528"
		//mobily.sendMessage(user,pass,senderName,message,numbers);
		def converted = mobily.convertUnicode(message)
		println "Converting message"
		println(converted)
		
	
	
	}

}
