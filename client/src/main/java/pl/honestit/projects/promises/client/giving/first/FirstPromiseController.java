package pl.honestit.projects.promises.client.giving.first;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/first-promise")
@Slf4j
@RequiredArgsConstructor
public class FirstPromiseController {

    @GetMapping
    public String prepareFirstPromisePage(Model model) {
        model.addAttribute("firstPromise", new FirstPromiseCommand());
        return "promises/first";
    }

    @PostMapping
    public String giveFirstPromise(@Valid @ModelAttribute("firstPromise") FirstPromiseCommand givenPromise, BindingResult bindings) {
        log.debug("First promise from {} to give: {}", givenPromise.getWho(), givenPromise);
        if (bindings.hasErrors()) {
            log.debug("Promise with errors! Stepping back to page");
            return "promises/first";
        }

        return "promises/first-success";
    }
}
