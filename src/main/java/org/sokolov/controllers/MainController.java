package org.sokolov.controllers;

import org.sokolov.domains.ConversionHistory;
import org.sokolov.domains.Currency;
import org.sokolov.services.ConversionHistoryService;
import org.sokolov.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.NoSuchElementException;

@Controller
public class MainController {
    @Autowired
    CurrencyService currencyService;

    @Autowired
    ConversionHistoryService historyService;

    @GetMapping
    public String getMainPage(Model model){
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        return "index";
    }

    @PostMapping
    public String convertCurrency(@RequestParam(name = "input_currency") Double inputNumber,
                                  @RequestParam(name = "select_input_currency") String inputId,
                                  @RequestParam(name = "select_output_currency") String outputId
    ){
        Currency actualInputCurrency = currencyService.getActualCurrency(inputId);
        Currency actualOutputCurrency = currencyService.getActualCurrency(outputId);
        Long historyId = currencyService.convertCurrency(inputNumber,actualInputCurrency,actualOutputCurrency);
        return "redirect:/result?id=" + historyId;
    }

    @GetMapping("/result")
    public String getConversionResult(@RequestParam("id") Long id, Model model){
        try{
            ConversionHistory lastRecord = historyService.getHistoryById(id);
            Currency inputCurrency = lastRecord.getInputCurrency();
            Currency outputCurrency = lastRecord.getOutputCurrency();

            model.addAttribute("input", lastRecord.getInputValue());
            model.addAttribute("result", lastRecord.getOutputValue());
            model.addAttribute("inputCurrency", inputCurrency);
            model.addAttribute("outputCurrency",outputCurrency);

            model.addAttribute("averageCourse", historyService.getAverageCourse(inputCurrency,outputCurrency));
            model.addAttribute("inputHistory", historyService.getCurrencyHistory(inputCurrency));
            model.addAttribute("outputHistory", historyService.getCurrencyHistory(outputCurrency));

        }catch (NoSuchElementException exception){
            return "redirect:/";
        }
        return "result";
    }
}
