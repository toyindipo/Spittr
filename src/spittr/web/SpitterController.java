package spittr.web;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.Errors;

import spittr.misc.Spitter;
import spittr.misc.SpitterForm;
import spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitters")
public class SpitterController {
  private static final String MAX_LONG_AS_STRING = "9223372036854775807";
	  
  private SpitterRepository spitterRepository;

  @Autowired
  public SpitterController(SpitterRepository spitterRepository) {
    this.spitterRepository = spitterRepository;
 }
	
  @RequestMapping(method=RequestMethod.GET)
  public List<Spitter> spitters(
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count) {
    return spitterRepository.findSpitters(max, count);
  }
	
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm(Model model) {
    model.addAttribute(new SpitterForm());
    return "registerForm";
  }
  
  @RequestMapping(value="/register", method=POST)
  @PreAuthorize("isAuthenticated()")
  public String processRegistration(
      @Valid SpitterForm spitterForm, @AuthenticationPrincipal UserDetails principal,
       Errors errors) throws IllegalStateException, IOException {
    if (errors.hasErrors()) {
      return "registerForm";
    }
    System.out.println(principal.getUsername());
    Spitter spitter = spitterForm.toSpitter();
    spitterRepository.save(spitter);
    
    MultipartFile profilePicture = spitterForm.getProfilePicture();
    
    profilePicture.transferTo(new File(spitter.getUsername() + ".jpg"));
    return "redirect:/spitters/" + spitter.getUsername();
  }
  
  @RequestMapping(value="/{username}", method=GET)
  public String showSpitterProfile(@PathVariable String username, Model model) {
    Spitter spitter = spitterRepository.findByUsername(username);
    model.addAttribute(spitter);
    return "profile";
  }

}