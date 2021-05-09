package cz.czechitas.java2webapps.ukol5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;

/**
 * Kontroler obsluhující registraci účastníků dětského tábora.
 */
@Controller
public class RegistraceController {

  @GetMapping("/")
  public ModelAndView formular() {
    ModelAndView formular = new ModelAndView("formular");
    formular.addObject("formular", new RegistraceForm());
    return formular;
  }

  @PostMapping("/")
  public Object form(@ModelAttribute("formular") @Valid RegistraceForm formular, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "formular";
    }
    Period period = formular.getDatumNarozeni().until(LocalDate.now());
    int vek = period.getYears();
    if (vek < 9 || vek > 15) {
       bindingResult.rejectValue("datumNarozeni", "", "Věk účastníka musí být mezi 9 až 15 rokem.");
      return "formular";
    }
    if (formular.getOblibena() == null || formular.getOblibena().size() < 2) {
      bindingResult.rejectValue("oblibena", "", "Je nutno zadat dva a více sportů.");
      return "formular";
    }
    ModelAndView rekap = new ModelAndView("rekapitulace");
    rekap.addObject("formular", formular);
    return rekap;
  }

}
