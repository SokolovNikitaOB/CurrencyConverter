package org.sokolov.controllers;

import org.sokolov.domains.Currency;
import org.sokolov.services.ConversionHistoryService;
import org.sokolov.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    CurrencyService currencyService;

    @Autowired
    ConversionHistoryService historyService;

    @GetMapping
    public String main(Model model){
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        return "index";
    }

    @PostMapping
    public String convertCurrency(Model model,
                                  @RequestParam(name = "input_currency") Double inputNumber,
                                  @RequestParam(name = "select_input_currency") String inputId,
                                  @RequestParam(name = "select_output_currency") String outputId
    ){
        Currency actualInputCurrency = currencyService.getActualCurrency(inputId);
        Currency actualOutputCurrency = currencyService.getActualCurrency(outputId);

        Double outputNumber =  currencyService.convertCurrency(inputNumber,actualInputCurrency,actualOutputCurrency);

        model.addAttribute("input", inputNumber);
        model.addAttribute("result", outputNumber);
        model.addAttribute("inputCurrency", actualInputCurrency);
        model.addAttribute("outputCurrency",actualOutputCurrency);

        model.addAttribute("averageCourse", historyService.getAverageCourse(actualInputCurrency,actualOutputCurrency));
        model.addAttribute("inputHistory", historyService.getCurrencyHistory(actualInputCurrency));
        model.addAttribute("outputHistory", historyService.getCurrencyHistory(actualOutputCurrency));

        return "result";
    }

}
