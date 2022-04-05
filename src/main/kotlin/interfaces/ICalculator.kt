package interfaces

import entities.Bill

interface ICalculator {
    fun CalculateCostAllocation(bill: Bill)
    fun CalculateBalance()
    fun CalculateOverallBalance()
}