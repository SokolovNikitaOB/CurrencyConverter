package org.sokolov.events;

import org.sokolov.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RunAfterStartup {

    @Autowired
    private CurrencyService currencyService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup(){
        currencyService.addToDatabaseActualData();
    }
}


