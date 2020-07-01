package com.ap8.appcriptomoedas.api

class MoedaAPI (
    var high: Double = 0.0, //Maior preço unitário de negociação das últimas 24 horas.
    var low: Double = 0.0, //Menor preço unitário de negociação das últimas 24 horas.
    var vol: Double = 0.0, //Quantidade negociada nas últimas 24 horas.
    var price: Double = 0.0, //Preço unitário da última negociação.
    var buy: Double = 0.0, //Maior preço de oferta de compra das últimas 24 horas.
    var sell: Double = 0.0, //Menor preço de oferta de venda das últimas 24 horas.
    var date: Double = 0.0 //Data e hora da informação em Era Unix
)