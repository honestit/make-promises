package pl.honestit.projects.promises.client.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.honestit.projects.promises.client.command.GiveFirstPromiseCommand;

import javax.validation.Valid;

@Controller
@RequestMapping("/first-promise")
@Slf4j
@RequiredArgsConstructor
public class MakeFirstPromiseController {

    @GetMapping
    public String prepareFirstPromisePage(Model model) {
        model.addAttribute("givenPromise", new GiveFirstPromiseCommand());
        return "promises/first";
    }

    @PostMapping
    public String giveFirstPromise(@Valid @ModelAttribute("givenPromise") GiveFirstPromiseCommand givenPromise, BindingResult bindings) {
        log.debug("First promise from {} to give: {}", givenPromise.getWho(), givenPromise);
        if (bindings.hasErrors()) {
            log.debug("Promise with errors! Stepping back to page");
            return "promises/first";
        }

        return "promises/first-success";
    }
}
