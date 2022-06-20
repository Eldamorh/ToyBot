package com.eldamorh.toybot.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bots")
public class Bot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bot_id")
    private Long id;

    @Column(name = "currency")
    @NotEmpty(message = "*Please provide a crypto currency to buy")
    private String currency;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "api_key")
    @NotEmpty(message = "*Please provide your api key")
    private String api_key;

    @Column(name = "secret_key")
    @NotEmpty(message = "*Please provide your api key")
    private String secret_key;

    @Column(name = "upward_trend_threshold")
    private Double upward_trend_threshold;

    @Column(name = "dip_threshold")
    private Double dip_threshold;

    @Column(name = "profit_threshold")
    private Double profit_threshold;

    @Column(name = "stop_loss_threshold")
    private Double stop_loss_threshold;

    @Column(name = "state")
    private Boolean state;
}
