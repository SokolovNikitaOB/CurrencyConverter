package org.sokolov.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ConversionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "input_id")
    private Currency inputCurrency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "output_id")
    private Currency outputCurrency;

    @Column(name = "input_value")
    private Double inputValue;

    @Column(name = "output_value")
    private Double outputValue;

    @Column(name = "conversion_date")
    private LocalDateTime conversionDate;
}

