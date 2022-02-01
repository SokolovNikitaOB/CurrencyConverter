package org.sokolov.microservices;

import org.sokolov.microservices.xmlclasses.ValCurs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "currency", url = "${cbr.url}")
public interface CurrencyProxy {
    @GetMapping(value = "/scripts/XML_daily.asp", produces = "application/xml")
    ValCurs getValCurs();
}
