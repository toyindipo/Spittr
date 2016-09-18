package spittr.test;

import org.junit.Test;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import spittr.data.SpitterRepository;
import spittr.misc.Spitter;
import spittr.web.SpitterController;

public class SpitterControllerTest {
	@Test
	public void shouldShowRegistration() throws Exception {
	  SpitterRepository mockRepository =
		         mock(SpitterRepository.class);
	  SpitterController controller = new SpitterController(mockRepository);
	  MockMvc mockMvc = standaloneSetup(controller).build();
	  mockMvc.perform(get("/spitters/register"))
	         .andExpect(view().name("registerForm"));
	}
	
	@Test
	public void shouldProcessRegistration() throws Exception {
	SpitterRepository mockRepository =
	         mock(SpitterRepository.class);
	
	    Spitter unsaved =
	          new Spitter("jbauer", "24hours", "Jack", "Bauer", "jackbauer@mail.com");
	  Spitter saved =
	          new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer",  "jackbauer@mail.com");
	  when(mockRepository.save(unsaved)).thenReturn(saved);
	  SpitterController controller =
	          new SpitterController(mockRepository);
	  MockMvc mockMvc = standaloneSetup(controller).build();
	  mockMvc.perform(post("/spitters/register")
	         .param("firstName", "Jack")
	         .param("lastName", "Bauer")
	         .param("username", "jbauer")
	         .param("password", "24hours")
	         .param("email", "jackbauer@mail.com"))
	         .andExpect(redirectedUrl("/spitters/jbauer"));
	  verify(mockRepository, atLeastOnce()).save(unsaved);
	}
}
