package spittr.web;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import spittr.data.SpittleRepository;
import spittr.misc.Spittle;
import spittr.misc.SpittleForm;
import spittr.stomp.Notification;
import spittr.stomp.SpittleFeedService;

@Controller
public class SpittleMessageController {

  private SpittleRepository spittleRepo;
  private SpittleFeedService feedService;

  @Autowired
  public SpittleMessageController(SpittleRepository spittleRepo, SpittleFeedService feedService) {
	this.spittleRepo = spittleRepo;
	this.feedService = feedService;
  }
  
  @MessageMapping("/spittle")
  @SendToUser("/queue/notifications")
  //@Secured("hasAnyRole({'ROLE_SPITTER', 'ROLE_ADMIN'})")
  @PreAuthorize("hasRole('ROLE_SPITTER')")
  public Notification handleSpittle(Principal principal, SpittleForm form) {
	  System.out.println("Saving");
	  Spittle spittle = new Spittle(Spittle.getNextId(), form.getText(), new Date());
	  spittleRepo.save(spittle);
	  feedService.broadcastSpittle(spittle);	  
	  return new Notification("Saved Spittle for user: " + principal.getName());
  }
  
}
