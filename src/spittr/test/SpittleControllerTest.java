package spittr.test;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.data.SpittleRepository;
import spittr.data.SpittleRepositoryImpl;
import spittr.misc.Spittle;
import spittr.web.SpittleController;

public class SpittleControllerTest {
  
  @Test
  public void shouldShowRecentSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList();
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
        .thenReturn(expectedSpittles);

    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();

    mockMvc.perform(get("/spittles"))
       .andExpect(view().name("spittles"))
       .andExpect(model().attributeExists("spittleList"))
       .andExpect(model().attribute("spittleList", 
                  hasItems(expectedSpittles.toArray())));
  }
  
  @Test
  public void shouldShowPagedSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList();
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findSpittles(238900, 50))
        .thenReturn(expectedSpittles);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();

    mockMvc.perform(get("/spittles?max=238900&count=50"))
      .andExpect(view().name("spittles"))
      .andExpect(model().attributeExists("spittleList"))
      .andExpect(model().attribute("spittleList", 
                 hasItems(expectedSpittles.toArray())));
  }
  
  @Test
  public void testSpittle() throws Exception {
    Spittle expectedSpittle = new Spittle(Long.valueOf(21 + ""), "Hello", new Date());
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(get("/spittles/12345"))
      .andExpect(view().name("spittle"))
      .andExpect(model().attributeExists("spittle"))
      .andExpect(model().attribute("spittle", expectedSpittle));
  }
	
  private List<Spittle> createSpittleList() {
    return SpittleRepositoryImpl.getSpittles();
  }
	
}
