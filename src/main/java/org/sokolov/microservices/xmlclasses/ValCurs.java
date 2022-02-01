package org.sokolov.microservices.xmlclasses;

import lombok.Getter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@XmlRootElement(name="ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCurs {
    @XmlElement(name = "Valute")
    private List<Valute> valutes;
}
