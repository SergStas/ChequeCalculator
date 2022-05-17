package com.sergstas.chequecalculator.validation.newevent

import com.sergstas.chequecalculator.App
import com.sergstas.chequecalculator.R
import com.sergstas.chequecalculator.vm.newevent.NewEventViewModel

interface IPositionValidator {
    fun validate(position: NewEventViewModel.IndexedPosition): Result

    sealed class Result {
        val isValid get() = this is Ok
        val isInvalid get() = this !is Ok

        object Ok: Result()
        data class Invalid(val errors: List<Error>): Result() {
            sealed class Error {
                fun getMessage() =
                    when (this) {
                        DuplicatedUsers  -> App.instance.getString(R.string.ne_nr_pos_invalid_message_duplicated)
                        EmptyName        -> App.instance.getString(R.string.ne_nr_pos_invalid_message_name)
                        PartNotPositive  -> App.instance.getString(R.string.ne_nr_pos_invalid_message_part)
                        PartsSumInvalid  -> App.instance.getString(R.string.ne_nr_pos_invalid_message_sum)
                        PriceNotPositive -> App.instance.getString(R.string.ne_nr_pos_invalid_message_price)
                        NoParts          -> App.instance.getString(R.string.ne_nr_pos_invalid_message_no_parts)
                    }

                object EmptyName: Error()
                object PriceNotPositive: Error()
                object NoParts: Error()
                object DuplicatedUsers: Error()
                object PartsSumInvalid: Error()
                object PartNotPositive: Error()
            }
        }
    }
}