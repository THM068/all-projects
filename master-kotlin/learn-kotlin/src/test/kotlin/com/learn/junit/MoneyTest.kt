package com.learn.junit

import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class MoneyTest {

    private val VALIDCURRENCY = "USD"
    @Test
    fun `Constructor should set amount`() {
        val money = Money(100, "USD")
        assertThat(money.amount).isEqualTo(100)
        assertThat(money.currency).isEqualTo("USD").matches("U.*")

        println("acc".map { val x = it + "aaa"; x.toUpperCase() })
    }

    @ParameterizedTest
    @ValueSource(ints = [ 10, 15, 50 ])
    fun `Constructor should set amounts`(amount: Int) {
        val money = Money(amount, "USD")
        assertThat(money.amount).isEqualTo(amount)
    }

    @ParameterizedTest
    @CsvSource(
      "10, USD",
      "15, EUR",
      "50, CHF",
    )
    fun `constructor can set amount and currency`(amount: Int, currency: String){
        val money = Money(amount, currency = currency )
        assertThat(money.amount).isEqualTo(amount)
    }

    @ParameterizedTest
    @ValueSource(ints = [ -1234, -323, -2332])
    fun `constructor should throw illegal argument exception`(invalidAmount: Int) {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                Money(invalidAmount, VALIDCURRENCY)
            }
    }

    @Test
    fun `String utils`(){
    }
}