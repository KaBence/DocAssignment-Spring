package via.doc1.doc1backenddemo.controller;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import via.doc1.doc1backenddemo.model.ExploreMessage;

@RestController
public class ExploreController {
    private static final String template = "Exploring DevOps CI/CD pipeline: " + LocalDateTime.now() + ". Running on %s!";
    private final AtomicLong counter = new AtomicLong();

    // inject via application.properties
    @Value("${application.env}")
    private String env;
/*
    @GetMapping("/")
    public ResponseEntity<ExploreMessage> explore() {
        ExploreMessage msg = new ExploreMessage(counter.incrementAndGet(), String.format(template, env));
        return ResponseEntity.ok().body(msg);
    }
 */
    @GetMapping("/")
    public ModelAndView explore(
        @RequestParam(name = "group", required = false, defaultValue = "All")
        String group, ModelMap map) {
        ExploreMessage msg = new ExploreMessage(counter.incrementAndGet(), String.format(template, env));
        map.addAttribute("group", group);
        map.addAttribute("arch", msg);
        return new ModelAndView("home", map);
    }

    @GetMapping("/groups")
    public ModelAndView extractEnvirons(
         @RequestParam(name = "group", required = false, defaultValue = "All")
        String group, ModelMap map) {
        String env2 = "[OS name ==> " + System.getProperty("os.name")
         + ", OS Architecture ==> " + System.getProperty("os.arch")+"]";
        String arch = counter.incrementAndGet() + env2;
        map.addAttribute("group", group);
        map.addAttribute("arch", arch);
        return new ModelAndView("home", map);
    }

    @GetMapping("/environment")
    public ModelAndView getProcessorArchitecture(
        @RequestParam(name = "group", required = false, defaultValue = "All")
        String group, ModelMap map) {
        String env3 = "[OS name ==> " + System.getProperty("os.name")+ ", OS version ==> " + System.getProperty("os.version") + ", OS Architecture ==> " + System.getProperty("os.arch")+"]";
        map.addAttribute("group", group);
        map.addAttribute("arch", new ExploreMessage(counter.incrementAndGet(), env3));
        return new ModelAndView("home", map);
    }
}
