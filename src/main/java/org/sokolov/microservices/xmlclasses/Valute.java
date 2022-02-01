package org.sokolov.microservices.xmlclasses;

import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class Valute {
    private String NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private String Value;

    @XmlAttribute(name = "ID")
    private String ID;
}
